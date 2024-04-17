package com.example.pokedexapp.data.utils

val POKEMON_SPRITE_BASE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"

fun String.treatName(): String {
    return this.replace('-', ' ').split(" ")
        .joinToString(" ") { it.replaceFirstChar { it.uppercase() } }
}

fun String.extractPokemonIdFromUrl(): Int {
    return this.trimEnd('/').split("/").last().toInt()
}
