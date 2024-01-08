package com.example.pokedexapp.ui.pokemon_list_screen

import com.example.pokedexapp.domain.models.PokemonModel

data class PokemonListScreenUiState(
    val isLoading: Boolean = false,
    val isSearchMode: Boolean = false,
    val pokemonList: List<PokemonModel> = listOf()

)

