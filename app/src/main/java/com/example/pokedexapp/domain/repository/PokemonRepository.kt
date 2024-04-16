package com.example.pokedexapp.domain.repository

import com.example.pokedexapp.domain.models.PokemonListItemModel
import com.example.pokedexapp.domain.models.PokemonDetailModel
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.domain.models.PokemonMinimalInfo
import com.example.pokedexapp.domain.models.SharePokemonModel

interface PokemonRepository {
    suspend fun getPokemonDetailById(pokemonId: String): PokemonDetailModel?
    suspend fun getPokemonDetailByName(name: String): PokemonDetailModel?
    suspend fun getPokemonEvolutionChain(speciesId: String): PokemonEvolutionChainModel
    suspend fun savePokemonList()
    suspend fun getPokemonList(offset: Int, limit: Int = 20): List<PokemonListItemModel>
    suspend fun getPokemonSearchList(name:String, offset: Int, limit: Int = 20): List<PokemonListItemModel>
    suspend fun getPokemonListItem(pokemonId: String): PokemonListItemModel?
    suspend fun getRandomPokemonMinimalInfo(): PokemonMinimalInfo
    suspend fun sharePokemonToReceiver(sharePokemonModel: SharePokemonModel)
}