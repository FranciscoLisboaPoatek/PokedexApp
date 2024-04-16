package com.example.pokedexapp.domain.models

data class PokemonListItemModel(
    val id: String,
    val name: String,
    val spriteUrl: String?,
    val primaryType: PokemonTypes,
    val secondaryType: PokemonTypes?,
)