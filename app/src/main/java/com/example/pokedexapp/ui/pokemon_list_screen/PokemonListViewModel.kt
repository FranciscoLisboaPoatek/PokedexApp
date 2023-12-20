package com.example.pokedexapp.ui.pokemon_list_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.models.PokemonTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(

): ViewModel() {

    private var _state = MutableStateFlow(PokemonListScreenUiState())
    val state get() = _state

    private var pokemonList = listOf<PokemonModel>()
    init {
        pokemonList = sampleDataProvider()
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
            state.value = state.value.copy(pokemonList = sampleSearchDataProvider())
        }
    }

    private fun sampleSearchDataProvider(): List<PokemonModel> =  listOf(
        PokemonModel(
            1,
            "???",
            PokemonTypes.NORMAL,
            null,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/365.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/365.png"
        ),PokemonModel(
            1,
            "???",
            PokemonTypes.NORMAL,
            null,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/578.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/578.png"
        ),
    )
    private fun sampleDataProvider(): List<PokemonModel> =  listOf(
        PokemonModel(
            1,
            "Ditto",
            PokemonTypes.NORMAL,
            null,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png"
        ),
        PokemonModel(
            2,
            "Lucario",
            PokemonTypes.FIGHTING,
            PokemonTypes.STEEL,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/448.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/448.png"
        ),
        PokemonModel(
            3,
            "Noivern",
            PokemonTypes.FLYING,
            PokemonTypes.DRAGON,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/715.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/715.png"
        ),
        PokemonModel(
            4,
            "Crobat",
            PokemonTypes.POISON,
            PokemonTypes.FLYING,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/169.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/169.png"
        ),
        PokemonModel(
            5,
            "Krookodile",
            PokemonTypes.GROUND,
            PokemonTypes.DARK,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/553.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/553.png"
        ),
        PokemonModel(
            6,
            "Omanyte",
            PokemonTypes.ROCK,
            PokemonTypes.WATER,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/138.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/138.png"
        ),
        PokemonModel(
            7,
            "Volcarona",
            PokemonTypes.BUG,
            PokemonTypes.FIRE,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/637.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/637.png"
        ),
        PokemonModel(
            8,
            "Gengar",
            PokemonTypes.GHOST,
            PokemonTypes.POISON,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/94.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/94.png"
        ),
        PokemonModel(
            9,
            "Jirachi",
            PokemonTypes.STEEL,
            PokemonTypes.PSYCHIC,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/385.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/385.png"
        ),
        PokemonModel(
            10,
            "Ho-oh",
            PokemonTypes.FIRE,
            PokemonTypes.FLYING,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/250.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/250.png"
        ),
        PokemonModel(
            11,
            "Blastoise",
            PokemonTypes.WATER,
            null,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png"
        ),
        PokemonModel(
            12,
            "Roserade",
            PokemonTypes.GRASS,
            PokemonTypes.POISON,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/407.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/407.png"
        ),
        PokemonModel(
            13,
            "Rotom",
            PokemonTypes.ELECTRIC,
            PokemonTypes.GHOST,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/479.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/479.png"
        ),
        PokemonModel(
            14,
            "Hatterene",
            PokemonTypes.PSYCHIC,
            PokemonTypes.FAIRY,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/858.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/858.png"
        ),
        PokemonModel(
            15,
            "Spheal",
            PokemonTypes.ICE,
            PokemonTypes.WATER,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/363.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/363.png"
        ),
        PokemonModel(
            16,
            "Dragapult",
            PokemonTypes.DRAGON,
            PokemonTypes.GHOST,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/887.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/887.png"
        ),
        PokemonModel(
            17,
            "Darkrai",
            PokemonTypes.DARK,
            null,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/491.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/491.png"
        ),
        PokemonModel(
            18,
            "Floette",
            PokemonTypes.FAIRY,
            null,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/670.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/670.png"
        ),
    )



}