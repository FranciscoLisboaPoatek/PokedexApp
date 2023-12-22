package com.example.pokedexapp.ui.pokemon_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(

): ViewModel() {

    private var _state = MutableStateFlow(PokemonListScreenUiState())
    val state get() = _state

    private var pokemonList = listOf<PokemonModel>()
    init {
        pokemonList = PokemonSampleData.pokemonListSampleData()
        _state.value = _state.value.copy(pokemonList = pokemonList)
    }
    fun changeIsSearchMode(){
        _state.value = _state.value.copy(isSearchMode = !_state.value.isSearchMode)
    }

    fun searchPokemonByName(pokemonName:String){
        if (pokemonName.isBlank()) {
            state.value = state.value.copy(pokemonList = pokemonList)
            return
        }
        viewModelScope.launch {
            state.value = state.value.copy(pokemonList = PokemonSampleData.pokemonSearchListSampleData())
        }
    }

}