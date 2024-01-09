package com.example.pokedexapp.di

import com.example.pokedexapp.domain.repository.PokemonRepository
import com.example.pokedexapp.domain.use_cases.PokemonDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonModule {
    @Provides
    @Singleton
    fun providePokemonDetailUseCase(repository: PokemonRepository): PokemonDetailUseCase{
        return PokemonDetailUseCase(repository)
    }
}