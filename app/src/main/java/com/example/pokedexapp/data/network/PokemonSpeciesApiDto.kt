package com.example.pokedexapp.data.network

import com.squareup.moshi.Json

data class PokemonSpeciesApiDto(
    @Json(name = "evolves_from_species")
    val evolvesFromSpecies: BasicApiModelDto?,
    @Json(name = "evolution_chain")
    val evolutionChain: UrlModelDto,
    val varieties: List<PokemonVariety>,
)

data class PokemonVariety(
    @Json(name = "is_default")
    val isDefault: Boolean,
    val pokemon: BasicApiModelDto,
)
