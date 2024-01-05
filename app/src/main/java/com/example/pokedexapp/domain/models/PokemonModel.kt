package com.example.pokedexapp.domain.models

data class PokemonModel(
    val id: Int,
    val name: String,
    val height: Float,
    val weight: Float,
    val baseStats: List<PokemonBaseStats>,
    val primaryType: PokemonTypes,
    val secondaryType: PokemonTypes?,
    val frontDefaultImageUrl: String,
    val frontShinyImageUrl: String
)