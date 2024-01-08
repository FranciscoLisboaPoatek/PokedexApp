package com.example.pokedexapp.ui.utils

import kotlinx.coroutines.flow.MutableStateFlow

inline fun <T : Any> MutableStateFlow<T>.updateState(transform: T.() -> T) {
    value = transform(value)
}