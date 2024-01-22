package com.example.pokedexapp.ui.pokemon_list_screen

import com.example.pokedexapp.domain.models.PokemonModel

data class PokemonListScreenUiState(
    val isLoading: Boolean = false,
    val isLoadingAppend: Boolean = false,
    val isError: Boolean = false,
    val isSearchMode: Boolean = false,
    val isDefaultList: Boolean = true,
    val searchText: String = "",
    val pokemonList: List<PokemonModel> = listOf()

)

