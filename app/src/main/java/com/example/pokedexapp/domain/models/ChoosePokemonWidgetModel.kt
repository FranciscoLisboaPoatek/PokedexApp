package com.example.pokedexapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ChoosePokemonWidgetModel(
    val id: String,
    val imageUrl: String?,
    val color: Int,
)
