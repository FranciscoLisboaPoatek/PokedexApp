package com.example.pokedexapp.ui.pokemon_list_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.domain.use_cases.PokemonListUseCase
import com.example.pokedexapp.ui.utils.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonListUseCase: PokemonListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonListScreenUiState())
    val state get() = _state

    private var defaultPokemonList = mutableListOf<PokemonModel>()
    private var searchPokemonList = listOf<PokemonModel>()

    init {
        _state.updateState { copy(isLoading = true) }
        viewModelScope.launch {
            pokemonListUseCase.insertAllPokemon()
            defaultPokemonList.addAll(pokemonListUseCase.getPokemonList(0))
            Log.w("list","${defaultPokemonList.size}")

            _state.updateState { copy(pokemonList = defaultPokemonList , isLoading = false) }
        }
    }

    fun getPokemonList() {
        if(_state.value.isLoadingAppend) return
        _state.updateState { copy(isLoadingAppend = true) }
        viewModelScope.launch {
            try {
                defaultPokemonList.addAll(pokemonListUseCase.getPokemonList(offset = defaultPokemonList.size))
                _state.updateState { copy(isLoadingAppend = false) }
                Log.w("list","${defaultPokemonList.size}")
            } catch (ex: Exception) {
                _state.updateState { copy(isLoadingAppend = false, isError = true) }
            }
        }
    }

    fun changeIsSearchMode() {
        _state.updateState { copy(isSearchMode = !_state.value.isSearchMode) }
    }

    fun searchPokemonByName(pokemonName: String) {
        if (pokemonName.isBlank()) {
            _state.updateState { copy(pokemonList = defaultPokemonList) }
            return
        }
        viewModelScope.launch {
            searchPokemonList = PokemonSampleData.pokemonSearchListSampleData()
            _state.updateState { copy(pokemonList = searchPokemonList) }
        }
    }

}