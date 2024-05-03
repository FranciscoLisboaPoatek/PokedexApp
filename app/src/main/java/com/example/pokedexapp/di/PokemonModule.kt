package com.example.pokedexapp.di

import android.content.Context
import androidx.room.Room
import com.example.pokedexapp.BuildConfig
import com.example.pokedexapp.data.local_database.PokemonDatabase
import com.example.pokedexapp.data.network.PokemonApi
import com.example.pokedexapp.data.pokedex_server.PokedexServerApi
import com.example.pokedexapp.domain.repository.PokemonRepository
import com.example.pokedexapp.domain.use_cases.PokemonDetailUseCase
import com.example.pokedexapp.domain.use_cases.PokemonEvolutionChainUseCase
import com.example.pokedexapp.domain.use_cases.PokemonListUseCase
import com.example.pokedexapp.domain.use_cases.RandomPokemonUseCase
import com.example.pokedexapp.domain.use_cases.SharePokemonUseCase
import com.example.pokedexapp.ui.Navigator
import com.example.pokedexapp.ui.NavigatorImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonModule {
    @Provides
    @Singleton
    fun providePokemonApi(): PokemonApi {
        val moshi =
            Moshi.Builder()
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
    fun providePokedexServerApi(): PokedexServerApi {
        val moshi =
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build())
            .build()
            .create(PokedexServerApi::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonDatabase(
        @ApplicationContext application: Context,
    ): PokemonDatabase {
        return Room.databaseBuilder(
            application,
            PokemonDatabase::class.java,
            "PokemonDatabase",
        ).fallbackToDestructiveMigration()
            .build()
    }

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
    fun provideNavigator(): Navigator {
        return NavigatorImpl()
    }
}
