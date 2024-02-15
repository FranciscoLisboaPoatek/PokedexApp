package com.example.pokedexapp.domain.models

data class PokemonEvolutionChainModel(
    val evolvesFromPokemonName:String? = null,
    val evolvesFromPokemonSpriteUrl:String? = null,
    val evolvesToPokemonName:String? = null,
    val evolvesToPokemonSpriteUrl:String? = null,
)