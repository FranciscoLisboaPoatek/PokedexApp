package com.example.pokedexapp.domain.repository

import androidx.paging.PagingData
import com.example.pokedexapp.domain.models.PokemonModel
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemonById(pokemonId: String): PokemonModel
    suspend fun getPokemonByName(name: String): PokemonModel

    suspend fun savePokemonList()

    suspend fun getPokemonList(): Flow<PagingData<PokemonModel>>
}