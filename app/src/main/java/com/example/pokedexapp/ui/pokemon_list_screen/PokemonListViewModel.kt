package com.example.pokedexapp.ui.pokemon_list_screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.domain.models.PokemonListItemModel
import com.example.pokedexapp.domain.use_cases.PokemonListUseCase
import com.example.pokedexapp.domain.use_cases.RandomPokemonUseCase
import com.example.pokedexapp.ui.navigation.Navigator
import com.example.pokedexapp.ui.navigation.Screen
import com.example.pokedexapp.ui.analytics.AnalyticsLogger
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
                is PokemonListScreenOnEvent.OnSearchClick -> {
                    changeIsSearchMode()
                }

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
                    val route = Screen.PokemonDetailScreen.navigateToPokemonDetail(event.pokemonId)
                    Log.w("navigation", route)

                    navigator.navigateTo(route)
                }
            }
        }

        fun loadInitialData() {
            _state.updateState { copy(isLoading = true) }
            viewModelScope.launch {
                try {
                    pokemonListUseCase.insertAllPokemon()
                    defaultPokemonList.addAll(pokemonListUseCase.getPokemonList(offset = 0, limit = LIST_ITEMS_PER_PAGE))

                    updateList(
                        _state.value.copy(
                            pokemonList = defaultPokemonList,
                            isLoading = false,
                            couldLoadInitialData = true,
                        ),
                    )
                } catch (ex: Exception) {
                    _state.updateState { copy(isLoading = false) }
                }
            }
        }

        fun changeIsSearchMode() {
            if (!_state.value.isSearchMode && _state.value.isLoading) return
            _state.updateState { copy(isSearchMode = !_state.value.isSearchMode) }
        }

        @OptIn(FlowPreview::class)
        private fun observerSearchText() {
            viewModelScope.launch {
                searchText
                    .debounce(1000)
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
                try {
                    val appendList = pokemonListUseCase.getPokemonList(offset = defaultPokemonList.size, limit = LIST_ITEMS_PER_PAGE)

                    if (appendList.isEmpty()) {
                        _state.updateState { copy(defaultListEnded = true, isLoadingAppend = false) }
                    } else {
                        defaultPokemonList.addAll(appendList)
                        updateList(
                            _state.value.copy(
                                isLoadingAppend = false,
                                errorAppendingDefaultList = false,
                                isDefaultList = true,
                            ),
                        )
                    }
                } catch (ex: Exception) {
                    _state.updateState { copy(isLoadingAppend = false, errorAppendingDefaultList = true) }
                }
            }
        }

        private fun searchPokemonListByName(pokemonName: String) {
            if (pokemonName.isBlank()) {
                updateList(_state.value.copy(isDefaultList = true, showNoSearchResultsFound = false, errorSearching = false))
                return
            }
            analyticsLogger.logEvent(
                FirebaseAnalytics.Event.SEARCH,
                mapOf(FirebaseAnalytics.Param.SEARCH_TERM to pokemonName),
            )

            _state.updateState { copy(isLoading = true, errorSearching = false, searchListEnded = false, showNoSearchResultsFound = false) }
            searchJob?.cancel()
            searchJob =
                viewModelScope.launch {
                    try {
                        val tempList =
                            pokemonListUseCase.getPokemonSearchList(name = pokemonName, offset = 0, limit = LIST_ITEMS_PER_PAGE)

                        if (tempList.isEmpty()) {
                            _state.updateState {
                                copy(isLoading = false, showNoSearchResultsFound = true)
                            }
                        } else {
                            val newList = mutableStateListOf<PokemonListItemModel>()
                            newList.addAll(tempList)
                            searchPokemonList = newList

                            updateList(
                                _state.value.copy(
                                    isLoading = false,
                                    errorSearching = false,
                                    isDefaultList = false,
                                ),
                            )
                        }
                    } catch (_: CancellationException) {
                    } catch (ex: Exception) {
                        _state.updateState { copy(isLoading = false, errorSearching = true) }
                    }
                }
        }

        fun appendSearchList() {
            if (_state.value.searchListEnded || _state.value.isLoadingAppend) return

            _state.updateState { copy(isLoadingAppend = true, errorAppendingSearchList = false) }

            viewModelScope.launch {
                try {
                    val appendList =
                        pokemonListUseCase.getPokemonSearchList(
                            name = searchText.value,
                            offset = searchPokemonList.size,
                            limit = LIST_ITEMS_PER_PAGE,
                        )
                    if (appendList.isEmpty()) {
                        _state.updateState { copy(searchListEnded = true, isLoadingAppend = false) }
                    } else {
                        searchPokemonList.addAll(appendList)
                        updateList(
                            _state.value.copy(
                                isLoadingAppend = false,
                                errorAppendingSearchList = false,
                                isDefaultList = false,
                            ),
                        )
                    }
                } catch (ex: Exception) {
                    _state.updateState { copy(isLoadingAppend = false, errorAppendingSearchList = true) }
                }
            }
        }

        fun sendNotification(context: Context) {
            viewModelScope.launch {
                val randomPokemon = randomPokemonUseCase.getRandomPokemonMinimalInfo()
                DailyPokemonNotification(context = context).showNotification(
                    pokemonId = randomPokemon.id,
                    pokemonName = randomPokemon.name,
                )
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
