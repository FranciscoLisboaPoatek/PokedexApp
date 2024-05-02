package com.example.pokedexapp

import com.example.pokedexapp.domain.models.PokemonDetailModel
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.domain.models.PokemonListItemModel
import com.example.pokedexapp.domain.models.PokemonMinimalInfo
import com.example.pokedexapp.domain.models.SharePokemonModel
import com.example.pokedexapp.domain.repository.PokemonRepository
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import javax.inject.Inject

class PokemonRepositoryTestImpl
    @Inject
    constructor() : PokemonRepository {
        override suspend fun getPokemonDetailById(pokemonId: String): PokemonDetailModel? {
            return PokemonSampleData.pokemonDetailListSampleData().find { it.id == pokemonId }
        }

        override suspend fun getPokemonDetailByName(name: String): PokemonDetailModel? {
            return PokemonSampleData.pokemonDetailListSampleData().find { it.name == name }
        }

        override suspend fun getPokemonEvolutionChain(speciesId: String): PokemonEvolutionChainModel {
            return PokemonEvolutionChainModel()
        }

        override suspend fun savePokemonList() {}

        override suspend fun getPokemonList(
            offset: Int,
            limit: Int,
        ): List<PokemonListItemModel> {
            val pokemonList = PokemonSampleData.pokemonListSampleData()
            val maxIndex = offset + limit
            val toIndex = if (maxIndex > pokemonList.size) pokemonList.size else maxIndex
            return pokemonList.subList(offset, toIndex)
        }

        override suspend fun getPokemonSearchList(
            name: String,
            offset: Int,
            limit: Int,
        ): List<PokemonListItemModel> {
            val searchPokemonList =
                PokemonSampleData.pokemonListSampleData()
                    .filter { it.name.contains(other = name, ignoreCase = true) }
            val maxIndex = offset + limit
            val toIndex = if (maxIndex > searchPokemonList.size) searchPokemonList.size else maxIndex
            return searchPokemonList.subList(offset, toIndex)
        }

        override suspend fun getPokemonListItem(pokemonId: String): PokemonListItemModel? {
            return PokemonSampleData.pokemonListSampleData().find { it.id == pokemonId }
        }

        override suspend fun getRandomPokemonMinimalInfo(): PokemonMinimalInfo {
            return PokemonMinimalInfo(id = "94", name = "Gengar")
        }

        override suspend fun sharePokemonToReceiver(sharePokemonModel: SharePokemonModel) {
        }
    }
