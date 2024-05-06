package com.example.pokedexapp

import com.example.pokedexapp.data.local_database.PokemonDatabase
import com.example.pokedexapp.di.PokemonModule
import com.example.pokedexapp.domain.repository.PokemonRepository
import com.example.pokedexapp.domain.use_cases.PokemonDetailUseCase
import com.example.pokedexapp.domain.use_cases.PokemonEvolutionChainUseCase
import com.example.pokedexapp.domain.use_cases.PokemonListUseCase
import com.example.pokedexapp.domain.use_cases.RandomPokemonUseCase
import com.example.pokedexapp.domain.use_cases.SharePokemonUseCase
import com.example.pokedexapp.ui.navigation.Navigator
import com.example.pokedexapp.ui.analytics.AnalyticsLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [PokemonModule::class],
)
object TestPokemonModule {
    @Provides
    @Singleton
    fun providePokemonDao(pokemonDatabase: PokemonDatabase) = pokemonDatabase.pokemonDao()

    @Provides
    @Singleton
    fun providePokemonDetailUseCase(repository: PokemonRepository): PokemonDetailUseCase {
        return PokemonDetailUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePokemonListUseCase(repository: PokemonRepository): PokemonListUseCase {
        return PokemonListUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePokemonEvolutionChainUseCase(repository: PokemonRepository): PokemonEvolutionChainUseCase {
        return PokemonEvolutionChainUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRandomPokemonUseCase(repository: PokemonRepository): RandomPokemonUseCase {
        return RandomPokemonUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSharePokemonUseCase(repository: PokemonRepository): SharePokemonUseCase {
        return SharePokemonUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAnalyticsLogger(): AnalyticsLogger {
        return FakeAnalyticsLogger()
    }

    @Provides
    @Singleton
    fun provideNavigator(): Navigator  {
        return FakeNavigator()
    }
}
