package com.example.pokedexapp.domain.models

data class SharePokemonModel(
    val receiver: String,
    val deeplink: String,
    val pokemonName: String
)
