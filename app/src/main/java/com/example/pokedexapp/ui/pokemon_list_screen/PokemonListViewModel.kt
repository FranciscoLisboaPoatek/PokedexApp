package com.example.pokedexapp.ui.pokemon_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.ui.utils.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(PokemonListScreenUiState())
    val state get() = _state

    private var defaultPokemonList = listOf<PokemonModel>()
    private var searchPokemonList = listOf<PokemonModel>()

    init {
        defaultPokemonList = PokemonSampleData.pokemonListSampleData()
        _state.updateState { copy(pokemonList = defaultPokemonList) }
    }
    fun changeIsSearchMode(){
        _state.updateState { copy(isSearchMode = !_state.value.isSearchMode) }
    }

    fun searchPokemonByName(pokemonName:String){
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