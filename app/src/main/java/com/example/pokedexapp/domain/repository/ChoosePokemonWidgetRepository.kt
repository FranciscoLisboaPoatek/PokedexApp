package com.example.pokedexapp.domain.repository

import com.example.pokedexapp.domain.models.ChoosePokemonWidgetModel

interface ChoosePokemonWidgetRepository {
    suspend fun savePokemon(pokemon: ChoosePokemonWidgetModel)

    suspend fun getPokemon(): ChoosePokemonWidgetModel
}
