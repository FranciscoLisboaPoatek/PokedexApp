package com.example.pokedexapp.di

import com.example.pokedexapp.BuildConfig
import com.example.pokedexapp.data.network.PokemonApi
import com.example.pokedexapp.domain.repository.PokemonRepository
import com.example.pokedexapp.domain.use_cases.PokemonDetailUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonModule {
    @Provides
    @Singleton
    fun providePokemonApi(): PokemonApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BuildConfig.POKEMON_API_BASE_URL)
            .build()
            .create(PokemonApi::class.java)
    }
    @Provides
    @Singleton
    fun providePokemonDetailUseCase(repository: PokemonRepository): PokemonDetailUseCase{
        return PokemonDetailUseCase(repository)
    }
}