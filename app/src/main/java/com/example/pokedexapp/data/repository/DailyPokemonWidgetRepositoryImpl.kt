package com.example.pokedexapp.data.repository

import android.content.Context
import com.example.pokedexapp.data.data_store.dailyPokemonDataStore
import com.example.pokedexapp.domain.models.DailyPokemonWidgetModel
import com.example.pokedexapp.domain.repository.DailyPokemonWidgetRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DailyPokemonWidgetRepositoryImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : DailyPokemonWidgetRepository {
        override suspend fun saveDailyPokemon(pokemon: DailyPokemonWidgetModel) {
            context.dailyPokemonDataStore.updateData { pokemon }
        }

        override suspend fun getDailyPokemon() = context.dailyPokemonDataStore.data.first()
    }
