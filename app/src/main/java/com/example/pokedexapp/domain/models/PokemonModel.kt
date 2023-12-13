package com.example.pokedexapp.domain.models

data class PokemonModel(
    val id: Int,
    val name: String,
    val primaryType: PokemonTypes,
    val secondaryType: PokemonTypes?,
    val frontDefaultImageUrl: String,
    val frontShinyImageUrl: String
)