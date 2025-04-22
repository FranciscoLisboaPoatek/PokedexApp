package com.example.pokedexapp.domain.use_cases

import com.example.pokedexapp.domain.models.DailyPokemonWidgetModel
import com.example.pokedexapp.domain.repository.DailyPokemonWidgetRepository
import javax.inject.Inject

class DailyPokemonWidgetUseCase
    @Inject
    constructor(
        private val dailyPokemonWidgetRepository: DailyPokemonWidgetRepository,
    ) {
        suspend fun saveDailyPokemon(pokemon: DailyPokemonWidgetModel) = dailyPokemonWidgetRepository.saveDailyPokemon(pokemon)

        suspend fun getDailyPokemon(): DailyPokemonWidgetModel = dailyPokemonWidgetRepository.getDailyPokemon()
    }
