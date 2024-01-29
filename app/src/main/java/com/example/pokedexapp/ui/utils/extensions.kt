package com.example.pokedexapp.ui.utils

import kotlinx.coroutines.flow.MutableStateFlow

inline fun <T : Any> MutableStateFlow<T>.updateState(transform: T.() -> T) {
    value = transform(value)
}

fun String.treatName(): String {
    return this.replace('-', ' ').split(" ")
        .joinToString(" ") { it.replaceFirstChar { it.uppercase() } }
}

fun String.extractPokemonIdFromUrl(): Int {
    return this.trimEnd('/').split("/").last().toInt()
}