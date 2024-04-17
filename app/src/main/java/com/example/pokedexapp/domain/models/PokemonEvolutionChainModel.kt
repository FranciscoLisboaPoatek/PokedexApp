package com.example.pokedexapp.domain.models

data class PokemonEvolutionChainModel(
    val evolvesFromPokemonId: String? = null,
    val evolvesFromPokemonName: String? = null,
    val evolvesFromPokemonSpriteUrl: String? = null,
)
