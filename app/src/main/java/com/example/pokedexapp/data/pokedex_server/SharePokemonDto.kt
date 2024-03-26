package com.example.pokedexapp.data.pokedex_server

data class SharePokemonDto(
    val receiver: String,
    val pokemonId: String,
    val pokemonName: String
)