package com.example.pokedexapp.ui.pokemon_list_screen

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.domain.models.PokemonListItemModel
import com.example.pokedexapp.domain.use_cases.PokemonListUseCase
import com.example.pokedexapp.domain.use_cases.RandomPokemonUseCase
import com.example.pokedexapp.domain.utils.Response
import com.example.pokedexapp.ui.analytics.AnalyticsLogger
import com.example.pokedexapp.ui.navigation.Navigator
import com.example.pokedexapp.ui.navigation.Screen
import com.example.pokedexapp.ui.notifications.DailyPokemonNotification
import com.example.pokedexapp.ui.utils.LIST_ITEMS_PER_PAGE
import com.example.pokedexapp.ui.utils.updateState
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel
    @Inject
    constructor(
        private val pokemonListUseCase: PokemonListUseCase,
        private val randomPokemonUseCase: RandomPokemonUseCase,
        private val analyticsLogger: AnalyticsLogger,
        private val navigator: Navigator,
    ) : ViewModel() {
        private val _state = MutableStateFlow(PokemonListScreenUiState())
        val state get() = _state

        private val searchText = MutableStateFlow("")

        private var defaultPokemonList = SnapshotStateList<PokemonListItemModel>()
        private var searchPokemonList = SnapshotStateList<PokemonListItemModel>()

        private var searchJob: Job? = null

        init {
            loadInitialData()
            observerSearchText()
        }

        fun onEvent(event: PokemonListScreenOnEvent) {
            when (event) {
                is PokemonListScreenOnEvent.OnSearchTextValueChange -> {
                    changeSearchText(event.text)
                }

                PokemonListScreenOnEvent.AppendToList -> {
                    if (state.value.isDefaultList) {
                        getPokemonList()
                    } else {
                        appendSearchList()
                    }
                }

                PokemonListScreenOnEvent.RetryLoadingData -> {
                    loadInitialData()
                }

                is PokemonListScreenOnEvent.OnSendNotificationClick -> {
                    sendNotification(event.context)
                }

                is PokemonListScreenOnEvent.OnPokemonCLick -> {
                    analyticsLogger.logEvent(
                        FirebaseAnalytics.Event.SELECT_ITEM,
                        mapOf(FirebaseAnalytics.Param.ITEM_ID to event.pokemonId),
                    )
                    navigator.navigateTo(Screen.PokemonDetailScreen.navigateToPokemonDetail(event.pokemonId))
                }

                is PokemonListScreenOnEvent.ChangeToDefaultList -> changeToDefaultList()
            }
        }

        fun loadInitialData() {
            _state.updateState { copy(isLoading = true) }
            viewModelScope.launch {
                val insertResponse = pokemonListUseCase.insertAllPokemon()

                if (insertResponse is Response.Error) {
                    _state.updateState { copy(isLoading = false) }
                    return@launch
                }

                val pokemonListResponse =
                    pokemonListUseCase.getPokemonList(
                        offset = 0,
                        limit = LIST_ITEMS_PER_PAGE,
                    )

                when (pokemonListResponse) {
                    is Response.Error -> {
                        _state.updateState { copy(isLoading = false) }
                        return@launch
                    }

                    is Response.Success -> {
                        defaultPokemonList.addAll(pokemonListResponse.data)

                        updateList(
                            _state.value.copy(
                                pokemonList = defaultPokemonList,
                                isLoading = false,
                                couldLoadInitialData = true,
                            ),
                        )
                    }
                }
            }
        }

        @OptIn(FlowPreview::class)
        private fun observerSearchText() {
            viewModelScope.launch {
                searchText
                    .debounce(500)
                    .collect {
                        searchPokemonListByName(it)
                    }
            }
        }

        fun changeSearchText(pokemonName: String) {
            searchText.value = pokemonName
            _state.updateState { copy(searchText = this@PokemonListViewModel.searchText.value) }
        }

        fun getPokemonList() {
            if (_state.value.defaultListEnded || _state.value.isLoadingAppend) return

            _state.updateState { copy(isLoadingAppend = true, errorAppendingDefaultList = false) }

            viewModelScope.launch {
                val appendListResponse =
                    pokemonListUseCase.getPokemonList(
                        offset = defaultPokemonList.size,
                        limit = LIST_ITEMS_PER_PAGE,
                    )

                when (appendListResponse) {
                    is Response.Error -> {
                        _state.updateState {
                            copy(
                                isLoadingAppend = false,
                                errorAppendingDefaultList = true,
                            )
                        }
                    }

                    is Response.Success -> {
                        if (appendListResponse.data.isEmpty()) {
                            _state.updateState {
                                copy(
                                    defaultListEnded = true,
                                    isLoadingAppend = false,
                                )
                            }
                        } else {
                            defaultPokemonList.addAll(appendListResponse.data)
                            updateList(
                                _state.value.copy(
                                    isLoadingAppend = false,
                                    errorAppendingDefaultList = false,
                                    isDefaultList = true,
                                ),
                            )
                        }
                    }
                }
            }
        }

        private fun searchPokemonListByName(pokemonName: String) {
            if (pokemonName.isEmpty()) return

            analyticsLogger.logEvent(
                FirebaseAnalytics.Event.SEARCH,
                mapOf(FirebaseAnalytics.Param.SEARCH_TERM to pokemonName),
            )

            _state.updateState {
                copy(
                    isLoading = true,
                    errorSearching = false,
                    searchListEnded = false,
                    showNoSearchResultsFound = false,
                )
            }
            searchJob?.cancel()
            searchJob =
                viewModelScope.launch {
                    try {
                        val tempListResponse =
                            pokemonListUseCase.getPokemonSearchList(
                                name = pokemonName,
                                offset = 0,
                                limit = LIST_ITEMS_PER_PAGE,
                            )

                        when (tempListResponse) {
                            is Response.Error -> {
                                _state.updateState {
                                    copy(
                                        isLoading = false,
                                        errorSearching = true,
                                        isDefaultList = false,
                                    )
                                }
                            }

                            is Response.Success -> {
                                if (tempListResponse.data.isEmpty()) {
                                    _state.updateState {
                                        copy(
                                            isLoading = false,
                                            showNoSearchResultsFound = true,
                                            isDefaultList = false,
                                        )
                                    }
                                } else {
                                    val newList = mutableStateListOf<PokemonListItemModel>()
                                    newList.addAll(tempListResponse.data)
                                    searchPokemonList = newList

                                    updateList(
                                        _state.value.copy(
                                            isLoading = false,
                                            errorSearching = false,
                                            isDefaultList = false,
                                        ),
                                    )
                                }
                            }
                        }
                    } catch (_: CancellationException) {
                    }
                }
        }

        private fun changeToDefaultList() {
            changeSearchText("")
            updateList(
                _state.value.copy(
                    isDefaultList = true,
                    showNoSearchResultsFound = false,
                    errorSearching = false,
                ),
            )
        }

        fun appendSearchList() {
            if (_state.value.searchListEnded || _state.value.isLoadingAppend) return

            _state.updateState { copy(isLoadingAppend = true, errorAppendingSearchList = false) }

            viewModelScope.launch {
                val appendListResponse =
                    pokemonListUseCase.getPokemonSearchList(
                        name = searchText.value,
                        offset = searchPokemonList.size,
                        limit = LIST_ITEMS_PER_PAGE,
                    )
                when (appendListResponse) {
                    is Response.Error -> {
                        _state.updateState {
                            copy(
                                isLoadingAppend = false,
                                errorAppendingSearchList = true,
                            )
                        }
                    }

                    is Response.Success -> {
                        if (appendListResponse.data.isEmpty()) {
                            _state.updateState { copy(searchListEnded = true, isLoadingAppend = false) }
                        } else {
                            searchPokemonList.addAll(appendListResponse.data)
                            updateList(
                                _state.value.copy(
                                    isLoadingAppend = false,
                                    errorAppendingSearchList = false,
                                    isDefaultList = false,
                                ),
                            )
                        }
                    }
                }
            }
        }

        fun sendNotification(context: Context) {
            viewModelScope.launch {
                val randomPokemonResponse = randomPokemonUseCase.getRandomPokemonMinimalInfo()

                when (randomPokemonResponse) {
                    is Response.Error -> {}

                    is Response.Success -> {
                        DailyPokemonNotification(context = context).showNotification(
                            pokemonId = randomPokemonResponse.data.id,
                            pokemonName = randomPokemonResponse.data.name,
                        )
                    }
                }
            }
        }

        private fun updateList(state: PokemonListScreenUiState) {
            _state.updateState {
                if (state.isDefaultList) {
                    state.copy(pokemonList = defaultPokemonList)
                } else {
                    state.copy(
                        pokemonList = searchPokemonList,
                    )
                }
            }
        }
    }
