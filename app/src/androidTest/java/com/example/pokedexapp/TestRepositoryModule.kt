package com.example.pokedexapp

import com.example.pokedexapp.di.RepositoryModule
import com.example.pokedexapp.domain.repository.ChoosePokemonWidgetRepository
import com.example.pokedexapp.domain.repository.DailyPokemonWidgetRepository
import com.example.pokedexapp.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class],
)
abstract class TestRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPokemonRepository(pokemonRepositoryTestImpl: PokemonRepositoryTestImpl): PokemonRepository

    @Binds
    @Singleton
    abstract fun bindDailyPokemonWidgetRepository(
        dailyPokemonWidgetRepositoryTestImpl: DailyPokemonWidgetRepositoryTestImpl,
    ): DailyPokemonWidgetRepository

    @Binds
    @Singleton
    abstract fun bindChoosePokemonWidgetRepository(
        choosePokemonWidgetRepositoryTestImpl: ChoosePokemonWidgetRepositoryTestImpl,
    ): ChoosePokemonWidgetRepository
}
