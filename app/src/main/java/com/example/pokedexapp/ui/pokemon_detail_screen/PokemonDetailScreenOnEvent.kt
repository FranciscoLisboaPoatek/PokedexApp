package com.example.pokedexapp.ui.pokemon_detail_screen

import com.example.pokedexapp.domain.models.SpriteType

sealed class PokemonDetailScreenOnEvent() {
    data class ChangeShinySprite (val sprite: SpriteType): PokemonDetailScreenOnEvent()
    data class RotateSprite (val sprite: SpriteType): PokemonDetailScreenOnEvent()
    data class NavigateToDetails (val pokemonId: String): PokemonDetailScreenOnEvent()
    object NavigateUp: PokemonDetailScreenOnEvent()
}