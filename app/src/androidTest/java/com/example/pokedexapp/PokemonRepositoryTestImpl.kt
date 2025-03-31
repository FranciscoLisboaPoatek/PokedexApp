package com.example.pokedexapp

import com.example.pokedexapp.domain.models.PokemonDetailModel
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.domain.models.PokemonListItemModel
import com.example.pokedexapp.domain.models.PokemonMinimalInfo
import com.example.pokedexapp.domain.models.SharePokemonModel
import com.example.pokedexapp.domain.repository.PokemonRepository
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.domain.utils.Response
import com.example.pokedexapp.domain.utils.response
import javax.inject.Inject

class PokemonRepositoryTestImpl
    @Inject
    constructor() : PokemonRepository {
        override suspend fun getPokemonDetailById(pokemonId: String): Response<PokemonDetailModel> =
            response {
                PokemonSampleData.pokemonDetailListSampleData().find { it.id == pokemonId } ?: throw NoSuchElementException()
            }

        override suspend fun getPokemonEvolutionChain(speciesId: String): Response<PokemonEvolutionChainModel> =
            response {
                PokemonEvolutionChainModel()
            }

        override suspend fun savePokemonList(): Response<Unit> = Response.Success(Unit)

        override suspend fun getPokemonList(
            offset: Int,
            limit: Int,
        ): Response<List<PokemonListItemModel>> =
            response {
                val pokemonList = PokemonSampleData.pokemonListSampleData()
                val maxIndex = offset + limit
                val toIndex = if (maxIndex > pokemonList.size) pokemonList.size else maxIndex
                pokemonList.subList(offset, toIndex)
            }

        override suspend fun getPokemonSearchList(
            name: String,
            offset: Int,
            limit: Int,
        ): Response<List<PokemonListItemModel>> =
            response {
                val searchPokemonList =
                    PokemonSampleData.pokemonListSampleData()
                        .filter { it.name.contains(other = name, ignoreCase = true) }
                val maxIndex = offset + limit
                val toIndex =
                    if (maxIndex > searchPokemonList.size) searchPokemonList.size else maxIndex
                searchPokemonList.subList(offset, toIndex)
            }

        override suspend fun getPokemonListItem(pokemonId: String): PokemonListItemModel? {
            return PokemonSampleData.pokemonListSampleData().find { it.id == pokemonId }
        }

        override suspend fun getRandomPokemonMinimalInfo(): Response<PokemonMinimalInfo> =
            response {
                PokemonMinimalInfo(id = "94", name = "Gengar")
            }

        override suspend fun sharePokemonToReceiver(sharePokemonModel: SharePokemonModel) = Response.Success(Unit)
    }
