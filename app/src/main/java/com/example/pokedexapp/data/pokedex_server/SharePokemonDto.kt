package com.example.pokedexapp.data.pokedex_server

data class SharePokemonDto(
    val receiver: String,
    val deeplink: String,
    val pokemonName: String
)