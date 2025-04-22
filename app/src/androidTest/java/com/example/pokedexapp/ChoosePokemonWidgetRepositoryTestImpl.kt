package com.example.pokedexapp

import com.example.pokedexapp.domain.models.ChoosePokemonWidgetModel
import com.example.pokedexapp.domain.repository.ChoosePokemonWidgetRepository
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import javax.inject.Inject

class ChoosePokemonWidgetRepositoryTestImpl @Inject constructor() : ChoosePokemonWidgetRepository {
    override suspend fun savePokemon(pokemon: ChoosePokemonWidgetModel) {

    }

    override suspend fun getPokemon(): ChoosePokemonWidgetModel =
        PokemonSampleData.choosePokemonWidgetDataSample()
}