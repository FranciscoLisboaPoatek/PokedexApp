package com.example.pokedexapp.data.network

import com.squareup.moshi.Json

data class PokemonEvolutionChainDto(
    val id: Int,
    val chain: Chain,
    @Json(name = "baby_trigger_item")
    val babyTriggerItem: Boolean
)

data class Chain(
    @Json(name = "evolution_details")
    val evolutionDetails: List<EvolutionDetails>,
    @Json(name = "evolves_to")
    val evolvesTo: List<Chain>,
    @Json(name = "is_baby")
    val isBaby: Boolean,
    val species: Species
)

data class EvolutionDetails(
    val trigger: Trigger
)

data class Trigger(
    val name: String,
    val url: String
)
