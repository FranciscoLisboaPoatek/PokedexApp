package com.example.pokedexapp.domain.models

import androidx.compose.runtime.Immutable

@Immutable
data class PokemonModel(
    val id: String,
    val name: String,
    val height: Float,
    val weight: Float,
    val baseStats: List<PokemonBaseStats>,
    val primaryType: PokemonTypes,
    val secondaryType: PokemonTypes?,
    val frontDefaultSprite: PokemonSprite.FrontDefaultSprite,
    val frontShinySprite: PokemonSprite.FrontShinySprite,
    val backDefaultSprite: PokemonSprite.BackDefaultSprite,
    val backShinySprite: PokemonSprite.BackShinySprite
)