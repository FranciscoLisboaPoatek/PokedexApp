package com.example.pokedexapp.data.repository

import android.content.Context
import com.example.pokedexapp.data.data_store.choosePokemonDataStore
import com.example.pokedexapp.domain.models.ChoosePokemonWidgetModel
import com.example.pokedexapp.domain.repository.ChoosePokemonWidgetRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ChoosePokemonWidgetRepositoryImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : ChoosePokemonWidgetRepository {
        override suspend fun savePokemon(pokemon: ChoosePokemonWidgetModel) {
            context.choosePokemonDataStore.updateData { pokemon }
        }

        override suspend fun getPokemon(): ChoosePokemonWidgetModel = context.choosePokemonDataStore.data.first()
    }
