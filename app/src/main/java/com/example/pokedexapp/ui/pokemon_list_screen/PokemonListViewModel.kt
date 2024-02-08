package com.example.pokedexapp.ui.pokemon_list_screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.use_cases.PokemonListUseCase
import com.example.pokedexapp.ui.utils.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonListUseCase: PokemonListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonListScreenUiState())
    val state get() = _state

    private val _searchText = MutableStateFlow("")

    private var defaultPokemonList = SnapshotStateList<PokemonModel>()
    private var searchPokemonList = SnapshotStateList<PokemonModel>()

    init {
        loadInitialData()
        observerSearchText()
    }

    fun loadInitialData(){
        _state.updateState { copy(isLoading = true, isError = false) }
        viewModelScope.launch {
            delay(100)//Gives time to recomposition know that isError is false, then LaunchedEffect(isError) can be triggered again when catching the exception
            try {
                pokemonListUseCase.insertAllPokemon()
                defaultPokemonList.addAll(pokemonListUseCase.getPokemonList(0))

                updateList(
                    _state.value.copy(
                        pokemonList = defaultPokemonList,
                        isLoading = false,
                        couldLoadInitialData = true
                    )
                )
            }
            catch (ex: Exception) {
                Log.w("ex",ex.toString())
                _state.updateState { copy(isError = true, isLoading = false) }
            }
        }
    }

    fun changeIsSearchMode() {
        if(!_state.value.isSearchMode && _state.value.isLoading) return
        _state.updateState { copy(isSearchMode = !_state.value.isSearchMode) }
    }

    @OptIn(FlowPreview::class)
    private fun observerSearchText() {
        viewModelScope.launch {
            _searchText
                .debounce(1000)
                .collect {
                    searchPokemonListByName(it)
                }
        }
    }

    fun changeSearchText(pokemonName: String) {
        _searchText.value = pokemonName
        _state.updateState { copy(searchText = _searchText.value) }
    }

    fun getPokemonList() {
        if (_state.value.defaultListEnded || _state.value.isLoadingAppend) return

        _state.updateState { copy(isLoadingAppend = true, isError = false) }

        viewModelScope.launch {
            try {
                val appendList = pokemonListUseCase.getPokemonList(offset = defaultPokemonList.size)

                if (appendList.isEmpty()) {
                    _state.updateState { copy(defaultListEnded = true, isLoadingAppend = false) }
                } else {
                    defaultPokemonList.addAll(appendList)
                    updateList(
                        _state.value.copy(
                            isLoadingAppend = false,
                            isError = false,
                            isDefaultList = true
                        )
                    )
                }
            } catch (ex: Exception) {
                _state.updateState { copy(isLoadingAppend = false, isError = true) }
            }
        }
    }

    fun searchPokemonListByName(pokemonName: String) {
        if (pokemonName.isBlank()) {
            updateList(_state.value.copy(isDefaultList = true, showNoSearchResultsFound = false, isError = false))
            return
        }

        _state.updateState { copy(isLoading = true, isError = false, searchListEnded = false, showNoSearchResultsFound = false) }
        viewModelScope.launch {
            delay(100)
            try {
                val tempList =
                    pokemonListUseCase.getPokemonSearchList(name = pokemonName, offset = 0)

                if (tempList.isEmpty()) {
                    _state.updateState {
                        copy(isLoading = false, showNoSearchResultsFound = true)
                    }
                } else {
                    val newList = mutableStateListOf<PokemonModel>()
                    newList.addAll(tempList)
                    searchPokemonList = newList

                    updateList(
                        _state.value.copy(
                            isLoading = false,
                            isError = false,
                            isDefaultList = false,
                        )
                    )
                }
            } catch (ex: Exception) {
                _state.updateState { copy(isLoading = false, isError = true) }
            }
        }
    }

    fun appendSearchList() {

        if (_state.value.searchListEnded || _state.value.isLoadingAppend) return

        _state.updateState { copy(isLoadingAppend = true, isError = false) }

        viewModelScope.launch {
            try {
                val appendList = pokemonListUseCase.getPokemonSearchList(
                    name = _searchText.value,
                    offset = searchPokemonList.size
                )
                if (appendList.isEmpty()) {
                    _state.updateState { copy(searchListEnded = true, isLoadingAppend = false) }
                } else {
                    searchPokemonList.addAll(appendList)
                    updateList(
                        _state.value.copy(
                            isLoadingAppend = false,
                            isError = false,
                            isDefaultList = false,
                        )
                    )
                }
            } catch (ex: Exception) {
                _state.updateState { copy(isLoading = false, isError = true) }
            }
        }
    }

    private fun updateList(state: PokemonListScreenUiState) {
        _state.updateState {
            if (state.isDefaultList) state.copy(pokemonList = defaultPokemonList) else state.copy(
                pokemonList = searchPokemonList
            )
        }
    }
}