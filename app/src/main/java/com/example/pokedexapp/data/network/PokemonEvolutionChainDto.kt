package com.example.pokedexapp.data.network

import com.squareup.moshi.Json

data class PokemonEvolutionChainDto(
    val id: Int,
    val chain: Chain,
)

data class Chain(
    @Json(name = "evolution_details")
    val evolutionDetails: List<EvolutionDetails>,
    @Json(name = "evolves_to")
    val evolvesTo: List<Chain>,
    @Json(name = "is_baby")
    val isBaby: Boolean,
    val species: BasicApiModel,
)

data class EvolutionDetails(
    val trigger: BasicApiModel,
)
