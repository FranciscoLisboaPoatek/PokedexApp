package com.example.pokedexapp.ui.pokemon_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.use_cases.PokemonListUseCase
import com.example.pokedexapp.ui.utils.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
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

    private val defaultPokemonList = mutableListOf<PokemonModel>()
    private var searchPokemonList = mutableListOf<PokemonModel>()

    init {
        _state.updateState { copy(isLoading = true) }
        viewModelScope.launch {
            pokemonListUseCase.insertAllPokemon()
            defaultPokemonList.addAll(pokemonListUseCase.getPokemonList(0))

            _state.updateState {
                copy(
                    pokemonList = defaultPokemonList,
                    isLoading = false,
                    isDefaultList = true
                )
            }
        }

        observerSearchText()
    }

    fun changeIsSearchMode() {
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
            _state.updateState { copy(isLoadingAppend = true) }
        try {

            viewModelScope.launch {
                val appendList = pokemonListUseCase.getPokemonList(offset = defaultPokemonList.size)

                if (appendList.isEmpty()) {
                    _state.updateState { copy(defaultListEnded = true, isLoadingAppend = false) }
                } else {
                    defaultPokemonList.addAll(appendList)
                    _state.updateState {
                        copy(
                            pokemonList = defaultPokemonList,
                            isLoadingAppend = false,
                            isDefaultList = true
                        )
                    }
                }
            }
        } catch (ex: Exception) {
            _state.updateState { copy(isLoadingAppend = false, isError = true) }
        }
    }

    fun searchPokemonListByName(pokemonName: String) {
        try {
            if (pokemonName.isBlank()) {
                _state.updateState { copy(pokemonList = defaultPokemonList, isDefaultList = true) }
                return
            }

            _state.updateState { copy(isLoading = true, searchListEnded = false) }
            viewModelScope.launch {
                searchPokemonList =
                    pokemonListUseCase.getPokemonSearchList(name = pokemonName, offset = 0)
                        .toMutableList()
                _state.updateState {
                    copy(
                        isLoading = false,
                        pokemonList = searchPokemonList,
                        isDefaultList = false
                    )
                }
            }
        } catch (ex: Exception) {
            _state.updateState { copy(isLoading = false, isError = true) }
        }
    }

    fun appendSearchList() {
        try {
            if (_searchText.value.isBlank()) {
                getPokemonList()
                return
            }
            _state.updateState { copy(isLoadingAppend = true) }

            viewModelScope.launch {
                val appendList = pokemonListUseCase.getPokemonSearchList(
                    name = _searchText.value,
                    offset = searchPokemonList.size
                )
                if (appendList.isEmpty()){
                    _state.updateState { copy(searchListEnded = true, isLoadingAppend = false) }
                }else {
                    searchPokemonList.addAll(appendList)
                    _state.updateState {
                        copy(
                            isLoadingAppend = false,
                            isDefaultList = false,
                            pokemonList = searchPokemonList
                        )
                    }
                }
            }
        } catch (ex: Exception) {
            _state.updateState { copy(isLoading = false, isError = true) }
        }
    }
}