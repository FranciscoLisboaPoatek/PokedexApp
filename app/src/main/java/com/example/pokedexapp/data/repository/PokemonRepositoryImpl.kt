package com.example.pokedexapp.data.repository

import com.example.pokedexapp.data.PokemonMapper.toPokemonDaoDto
import com.example.pokedexapp.data.PokemonMapper.toPokemonEvolutionChainModel
import com.example.pokedexapp.data.PokemonMapper.toPokemonListItemModel
import com.example.pokedexapp.data.PokemonMapper.toPokemonMinimalInfo
import com.example.pokedexapp.data.PokemonMapper.toPokemonModel
import com.example.pokedexapp.data.PokemonMapper.toSharePokemonNotificationDto
import com.example.pokedexapp.data.local_database.PokemonDao
import com.example.pokedexapp.data.network.Chain
import com.example.pokedexapp.data.network.PokemonApi
import com.example.pokedexapp.data.pokedex_server.PokedexServerApi
import com.example.pokedexapp.data.utils.extractPokemonIdFromUrl
import com.example.pokedexapp.domain.models.ChainModel
import com.example.pokedexapp.domain.models.PokemonDetailModel
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.domain.models.PokemonListItemModel
import com.example.pokedexapp.domain.models.PokemonMinimalInfo
import com.example.pokedexapp.domain.models.SharePokemonModel
import com.example.pokedexapp.domain.repository.PokemonRepository
import com.example.pokedexapp.domain.utils.Response
import com.example.pokedexapp.domain.utils.response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl
    @Inject
    constructor(
        private val pokemonApi: PokemonApi,
        private val pokemonDao: PokemonDao,
        private val pokedexServerApi: PokedexServerApi,
    ) : PokemonRepository {
        override suspend fun getPokemonDetailById(pokemonId: String): Response<PokemonDetailModel> =
            response(Dispatchers.IO) {
                val pokemonApiDto =
                    pokemonApi.getPokemonById(pokemonId.toInt()) ?: throw NoSuchElementException()
                pokemonApiDto.toPokemonModel()
            }

        override suspend fun getPokemonDetailByName(name: String): PokemonDetailModel? =
            withContext(Dispatchers.IO) {
                return@withContext pokemonApi.getPokemonByName(name)?.toPokemonModel()
            }

        override suspend fun getPokemonEvolutionChain(speciesId: String): Response<PokemonEvolutionChainModel> =
            response(Dispatchers.IO) {
                val species = pokemonApi.getPokemonSpeciesById(speciesId.toInt())

                val evolutionChain =
                    pokemonApi.getPokemonEvolutionChain(species.evolutionChain.url.extractPokemonIdFromUrl())

                val evolutionChainList = mutableListOf<ChainModel>()

                evolutionChainList.add(evolutionChain.chain.recursiveToChainModel())

                // The Evolution Chain must have a base pokemon, so .first() throwing an exception if there isn't is the intended behaviour
                evolutionChain.toPokemonEvolutionChainModel(evolutionChainList.first())
            }

        override suspend fun savePokemonList(): Response<Unit> =
            response(Dispatchers.IO) {
                pokemonDao.deleteAllPokemon()
                val pokemonApiList = pokemonApi.getPokemonEntireList().results
                val pokemonDaoList =
                    pokemonApiList.map { it.toPokemonDaoDto() }

                pokemonDao.insertAllPokemon(pokemonDaoList)
            }

        override suspend fun getPokemonList(
            offset: Int,
            limit: Int,
        ): Response<List<PokemonListItemModel>> =
            response(Dispatchers.IO) {
                val pokemonModelList = mutableListOf<PokemonListItemModel>()
                val pokemonDaoDtoList = pokemonDao.pokemonPagination(offset, limit)
                pokemonDaoDtoList.forEach {
                    getPokemonListItem(it.id.toString())?.let { pokemonListItem ->
                        pokemonModelList.add(
                            pokemonListItem,
                        )
                    }
                }
                pokemonModelList
            }

        override suspend fun getPokemonSearchList(
            name: String,
            offset: Int,
            limit: Int,
        ): Response<List<PokemonListItemModel>> =
            response(Dispatchers.IO) {
                val searchName = "%$name%"
                val pokemonModelList = mutableListOf<PokemonListItemModel>()
                val pokemonDaoDtoList = pokemonDao.searchPokemonByName(searchName, offset, limit)
                pokemonDaoDtoList.forEach {
                    getPokemonListItem(it.id.toString())?.let { pokemonListItem ->
                        pokemonModelList.add(
                            pokemonListItem,
                        )
                    }
                }
                pokemonModelList
            }

        override suspend fun getPokemonListItem(pokemonId: String): PokemonListItemModel? =
            withContext(Dispatchers.IO) {
                val pokemonApiDto = pokemonApi.getPokemonById(pokemonId.toInt())
                return@withContext pokemonApiDto?.toPokemonListItemModel()
            }

        override suspend fun getRandomPokemonMinimalInfo(): Response<PokemonMinimalInfo> =
            response(Dispatchers.IO) {
                val pokemonTableCount = pokemonDao.getPokemonTableCount()
                val randomPokemonDaoDto = pokemonDao.getRandomPokemon((0..<pokemonTableCount).random())
                randomPokemonDaoDto.toPokemonMinimalInfo()
            }

        override suspend fun sharePokemonToReceiver(sharePokemonModel: SharePokemonModel) =
            response(Dispatchers.IO) {
                val response =
                    pokedexServerApi.sharePokemon(body = sharePokemonModel.toSharePokemonNotificationDto())
                        .execute()
                if (response.code() == 400) throw IllegalArgumentException()
            }

        private suspend fun Chain.recursiveToChainModel(): ChainModel {
            val pokemonSpecies =
                pokemonApi.getPokemonSpeciesById(this.species.url.extractPokemonIdFromUrl())

            val defaultPokemon =
                pokemonSpecies.varieties.find { it.isDefault } ?: pokemonSpecies.varieties.first()

            val pokemonDetails =
                pokemonApi.getPokemonByName(defaultPokemon.pokemon.name)
                    ?: throw IllegalArgumentException()

            return ChainModel(
                id = pokemonDetails.id.toString(),
                name = this.species.name,
                spriteUrl = pokemonDetails.sprites.frontDefault,
                isBaby = this.isBaby,
                evolutions =
                    this.evolvesTo.map { chain ->
                        chain.recursiveToChainModel()
                    },
            )
        }
    }
