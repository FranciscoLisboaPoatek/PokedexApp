package com.example.pokedexapp

import com.example.pokedexapp.domain.models.DailyPokemonWidgetModel
import com.example.pokedexapp.domain.repository.DailyPokemonWidgetRepository
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import javax.inject.Inject

class DailyPokemonWidgetRepositoryTestImpl
    @Inject
    constructor() : DailyPokemonWidgetRepository {
        override suspend fun saveDailyPokemon(pokemon: DailyPokemonWidgetModel) {
        }

        override suspend fun getDailyPokemon(): DailyPokemonWidgetModel = PokemonSampleData.pokemonWidgetDataSample()
    }
