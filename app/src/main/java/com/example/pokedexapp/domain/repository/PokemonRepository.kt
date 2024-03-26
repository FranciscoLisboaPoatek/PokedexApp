package com.example.pokedexapp.domain.repository

import com.example.pokedexapp.domain.models.PokemonMinimalInfo
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.models.SharePokemonModel

interface PokemonRepository {
    suspend fun getPokemonById(pokemonId: String): PokemonModel?
    suspend fun getPokemonByName(name: String): PokemonModel?
    suspend fun savePokemonList()
    suspend fun getPokemonList(offset: Int, limit: Int = 20): List<PokemonModel>
    suspend fun getPokemonSearchList(name:String, offset: Int, limit: Int = 20): List<PokemonModel>
    suspend fun getRandomPokemonMinimalInfo(): PokemonMinimalInfo
    suspend fun sharePokemonToReceiver(sharePokemonModel: SharePokemonModel)
}