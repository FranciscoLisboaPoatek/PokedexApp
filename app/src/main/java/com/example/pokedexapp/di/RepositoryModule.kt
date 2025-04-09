package com.example.pokedexapp.di

import com.example.pokedexapp.data.repository.DailyPokemonWidgetRepositoryImpl
import com.example.pokedexapp.data.repository.PokemonRepositoryImpl
import com.example.pokedexapp.domain.repository.DailyPokemonWidgetRepository
import com.example.pokedexapp.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPokemonRepository(pokemonRepositoryImpl: PokemonRepositoryImpl): PokemonRepository

    @Binds
    @Singleton
    abstract fun bindDailyPokemonWidgetRepository(
        dailyPokemonWidgetRepositoryImpl: DailyPokemonWidgetRepositoryImpl,
    ): DailyPokemonWidgetRepository
}
