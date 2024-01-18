package com.example.pokedexapp.domain.models

import com.example.pokedexapp.ui.pokemon_detail_screen.PokemonSprite

data class PokemonModel(
    val id: Int,
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