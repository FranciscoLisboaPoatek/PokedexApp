package com.example.pokedexapp.data.network

data class PokemonListResponse(
    val results: List<PokemonListItemApiDto>
)

data class PokemonListItemApiDto(
    val name:String,
    val url:String
)