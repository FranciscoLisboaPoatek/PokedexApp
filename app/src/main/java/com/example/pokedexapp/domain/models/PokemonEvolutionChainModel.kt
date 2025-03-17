package com.example.pokedexapp.domain.models

data class PokemonEvolutionChainModel(
    val id: String? = null,
    val evolutions: List<ChainModel>? = null
)

data class ChainModel(
    val id: String,
    val name: String,
    val spriteUrl: String?,
    val isBaby: Boolean,
    val evolutions: List<ChainModel>? = null
)
