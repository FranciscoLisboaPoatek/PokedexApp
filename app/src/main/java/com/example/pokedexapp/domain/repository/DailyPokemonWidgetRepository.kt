package com.example.pokedexapp.domain.repository

import com.example.pokedexapp.domain.models.DailyPokemonWidgetModel

interface DailyPokemonWidgetRepository {
    suspend fun saveDailyPokemon(pokemon: DailyPokemonWidgetModel)

    suspend fun getDailyPokemon(): DailyPokemonWidgetModel
}
