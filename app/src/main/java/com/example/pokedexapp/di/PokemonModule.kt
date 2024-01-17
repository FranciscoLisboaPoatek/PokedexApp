package com.example.pokedexapp.di

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.pokedexapp.BuildConfig
import com.example.pokedexapp.data.local_database.PokemonDao
import com.example.pokedexapp.data.local_database.PokemonDaoDto
import com.example.pokedexapp.data.local_database.PokemonDatabase
import com.example.pokedexapp.data.network.PokemonApi
import com.example.pokedexapp.domain.repository.PokemonRepository
import com.example.pokedexapp.domain.use_cases.PokemonDetailUseCase
import com.example.pokedexapp.domain.use_cases.PokemonListUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providePokemonDatabase(@ApplicationContext application: Context): PokemonDatabase {
        return Room.databaseBuilder(
            application,
            PokemonDatabase::class.java,
            "PokemonDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun providePokemonDaoPager(pokemonDao: PokemonDao): Pager<Int, PokemonDaoDto>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { pokemonDao.pokemonPagination() }
        )
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
}