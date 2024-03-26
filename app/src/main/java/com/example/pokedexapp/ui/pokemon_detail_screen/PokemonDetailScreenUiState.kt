package com.example.pokedexapp.ui.pokemon_detail_screen

import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.models.PokemonSprite

data class PokemonDetailScreenUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val receiverToken: String = "",
    val isSharingPokemonToReceiver: Boolean = false,
    val pokemonModel: PokemonModel? = null,
    val pokemonSprite: PokemonSprite? = pokemonModel?.frontDefaultSprite
)