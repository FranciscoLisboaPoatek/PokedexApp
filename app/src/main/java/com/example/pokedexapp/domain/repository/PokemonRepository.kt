package com.example.pokedexapp.domain.repository

import com.example.pokedexapp.domain.models.PokemonDetailModel
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.domain.models.PokemonListItemModel
import com.example.pokedexapp.domain.models.PokemonMinimalInfo
import com.example.pokedexapp.domain.models.SharePokemonModel
import com.example.pokedexapp.domain.utils.Response

interface PokemonRepository {
    suspend fun getPokemonDetailById(pokemonId: String): Response<PokemonDetailModel>

    suspend fun getPokemonEvolutionChain(speciesId: String): Response<PokemonEvolutionChainModel>

    suspend fun savePokemonList(): Response<Unit>

    suspend fun getPokemonList(
        offset: Int,
        limit: Int = 20,
    ): Response<List<PokemonListItemModel>>

    suspend fun getPokemonSearchList(
        name: String,
        offset: Int,
        limit: Int = 20,
    ): Response<List<PokemonListItemModel>>

    suspend fun getPokemonListItem(pokemonId: String): PokemonListItemModel?

    suspend fun getRandomPokemonMinimalInfo(): Response<PokemonMinimalInfo>

    suspend fun sharePokemonToReceiver(sharePokemonModel: SharePokemonModel): Response<Unit>
}
