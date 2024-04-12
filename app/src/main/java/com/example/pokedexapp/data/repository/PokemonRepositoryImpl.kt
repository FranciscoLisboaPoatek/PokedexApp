package com.example.pokedexapp.data.repository

import com.example.pokedexapp.data.PokemonMapper.toPokemonDaoDto
import com.example.pokedexapp.data.PokemonMapper.toPokemonMinimalInfo
import com.example.pokedexapp.data.PokemonMapper.toPokemonListItemModel
import com.example.pokedexapp.data.PokemonMapper.toPokemonModel
import com.example.pokedexapp.data.PokemonMapper.toSharePokemonNotificationDto
import com.example.pokedexapp.data.local_database.PokemonDao
import com.example.pokedexapp.data.network.PokemonApi
import com.example.pokedexapp.data.utils.POKEMON_SPRITE_BASE_URL
import com.example.pokedexapp.data.utils.extractPokemonIdFromUrl
import com.example.pokedexapp.data.utils.treatName
import com.example.pokedexapp.domain.models.PokemonListItemModel
import com.example.pokedexapp.domain.models.PokemonDetailModel
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.data.pokedex_server.PokedexServerApi
import com.example.pokedexapp.domain.models.PokemonMinimalInfo
import com.example.pokedexapp.domain.models.SharePokemonModel
import com.example.pokedexapp.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException
import javax.inject.Inject

class  PokemonRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val pokemonDao: PokemonDao,
    private val pokedexServerApi: PokedexServerApi
) : PokemonRepository {
    override suspend fun getPokemonDetailById(pokemonId: String): PokemonDetailModel? =
        withContext(Dispatchers.IO) {
            val pokemonApiDto = pokemonApi.getPokemonById(pokemonId.toInt())
            return@withContext pokemonApiDto?.toPokemonModel()
        }

    override suspend fun getPokemonDetailByName(name: String): PokemonDetailModel? =
        withContext(Dispatchers.IO) {
            return@withContext pokemonApi.getPokemonByName(name)?.toPokemonModel()

        }

    override suspend fun getPokemonEvolutionChain(speciesId: String): PokemonEvolutionChainModel =
        withContext(Dispatchers.IO) {
            val evolvesFrom =
                pokemonApi.getPokemonSpeciesById(speciesId.toInt())?.evolvesFromSpecies

            if (evolvesFrom != null) {
                val evolvesFromPokemonId = evolvesFrom.url.extractPokemonIdFromUrl()
                val spriteUrl = POKEMON_SPRITE_BASE_URL.plus("$evolvesFromPokemonId.png")
                return@withContext PokemonEvolutionChainModel(
                    evolvesFromPokemonId = evolvesFromPokemonId.toString(),
                    evolvesFromPokemonName = evolvesFrom.name.treatName(),
                    evolvesFromPokemonSpriteUrl = spriteUrl
                )
            } else {
                return@withContext PokemonEvolutionChainModel(
                    evolvesFromPokemonName = null,
                    evolvesFromPokemonSpriteUrl = null
                )
            }
        }


    override suspend fun savePokemonList() {
        withContext(Dispatchers.IO) {
            pokemonDao.deleteAllPokemon()
            val pokemonApiList = pokemonApi.getPokemonEntireList().results
            val pokemonDaoList =
                pokemonApiList.map { it.toPokemonDaoDto() }

            pokemonDao.insertAllPokemon(pokemonDaoList)
        }
    }

    override suspend fun getPokemonList(offset: Int, limit: Int): List<PokemonListItemModel> =
        withContext(Dispatchers.IO) {
            val pokemonModelList = mutableListOf<PokemonListItemModel>()
            val pokemonDaoDtoList = pokemonDao.pokemonPagination(offset, limit)
            pokemonDaoDtoList.forEach {
                getPokemonListItem(it.id.toString())?.let { pokemonListItem ->
                    pokemonModelList.add(
                        pokemonListItem
                    )
                }
            }
            return@withContext pokemonModelList
        }

    override suspend fun getPokemonSearchList(
        name: String,
        offset: Int,
        limit: Int
    ): List<PokemonListItemModel> =
        withContext(Dispatchers.IO) {
            val searchName = "%$name%"
            val pokemonModelList = mutableListOf<PokemonListItemModel>()
            val pokemonDaoDtoList = pokemonDao.searchPokemonByName(searchName, offset, limit)
            pokemonDaoDtoList.forEach {
                getPokemonListItem(it.id.toString())?.let { pokemonListItem ->
                    pokemonModelList.add(
                        pokemonListItem
                    )
                }
            }
            return@withContext pokemonModelList
        }

    override suspend fun getPokemonListItem(pokemonId: String): PokemonListItemModel? =
        withContext(Dispatchers.IO) {
            val pokemonApiDto = pokemonApi.getPokemonById(pokemonId.toInt())
            return@withContext pokemonApiDto?.toPokemonListItemModel()
        }

    override suspend fun getRandomPokemonMinimalInfo(): PokemonMinimalInfo =
        withContext(Dispatchers.IO) {
            val pokemonTableCount = pokemonDao.getPokemonTableCount()
            val randomPokemonDaoDto = pokemonDao.getRandomPokemon((0..< pokemonTableCount).random())
            return@withContext randomPokemonDaoDto.toPokemonMinimalInfo()
        }

    override suspend fun sharePokemonToReceiver(sharePokemonModel: SharePokemonModel) {
        withContext(Dispatchers.IO) {
            val response = pokedexServerApi.sharePokemon(body = sharePokemonModel.toSharePokemonNotificationDto()).execute()
            if (response.code() == 400) throw IllegalArgumentException()
        }
    }
}