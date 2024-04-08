package com.example.pokedexapp.data.network

import com.squareup.moshi.Json

data class PokemonSpeciesApiDto(
    @Json(name = "evolves_from_species")
    val evolvesFromSpecies: PokemonListItemApiDto?
)