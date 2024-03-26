package com.example.pokedexapp.domain.models

data class SharePokemonModel(
    val receiver: String,
    val pokemonId: String,
    val pokemonName: String
)
