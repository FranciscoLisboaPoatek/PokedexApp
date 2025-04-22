package com.example.pokedexapp.domain.use_cases

import com.example.pokedexapp.domain.models.ChoosePokemonWidgetModel
import com.example.pokedexapp.domain.repository.ChoosePokemonWidgetRepository
import javax.inject.Inject

class ChoosePokemonWidgetUseCase
    @Inject
    constructor(
        private val choosePokemonWidgetRepository: ChoosePokemonWidgetRepository,
    ) {
        suspend fun savePokemon(pokemon: ChoosePokemonWidgetModel) = choosePokemonWidgetRepository.savePokemon(pokemon)

        suspend fun getPokemon(): ChoosePokemonWidgetModel = choosePokemonWidgetRepository.getPokemon()
    }
