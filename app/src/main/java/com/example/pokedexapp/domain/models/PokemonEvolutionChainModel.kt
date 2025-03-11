package com.example.pokedexapp.domain.models

data class PokemonEvolutionChainModel(
    val evolvesFromPokemonId: String? = null,
    val evolvesFromPokemonName: String? = null,
    val evolvesFromPokemonSpriteUrl: String? = null,
)

data class PokemonEvolutionModel(
    val id: String,
    val name: String,
    val spriteUrl: String,
    val evolutions: List<PokemonEvolutionModel>? = null
)
