package com.example.pokedexapp.ui.pokemon_detail_screen

import com.example.pokedexapp.domain.models.PokemonModel

data class PokemonDetailScreenUiState(
    val isLoading: Boolean = true,
    val pokemonModel: PokemonModel? = null,
    val pokemonSprite: PokemonSprite? = pokemonModel?.frontDefaultSprite
)