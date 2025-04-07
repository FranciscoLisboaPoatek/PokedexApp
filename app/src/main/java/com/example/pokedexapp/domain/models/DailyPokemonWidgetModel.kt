package com.example.pokedexapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class DailyPokemonWidgetModel(
    val id: String,
    val imageUrl: String?,
    val primaryType: PokemonTypes,
)