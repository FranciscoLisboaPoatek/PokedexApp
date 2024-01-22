package com.example.pokedexapp.data.repository

import com.example.pokedexapp.data.PokemonMapper.toPokemonDaoDto
import com.example.pokedexapp.data.PokemonMapper.toPokemonModel
import com.example.pokedexapp.data.local_database.PokemonDao
import com.example.pokedexapp.data.network.PokemonApi
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val pokemonDao: PokemonDao
) : PokemonRepository {
    override suspend fun getPokemonById(pokemonId: String): PokemonModel =
        withContext(Dispatchers.IO) {
            val pokemonApiDto = pokemonApi.getPokemonById(pokemonId.toInt())
            return@withContext pokemonApiDto.toPokemonModel()
        }

    override suspend fun getPokemonByName(name: String): PokemonModel =
        withContext(Dispatchers.IO) {
            return@withContext pokemonApi.getPokemonByName(name).toPokemonModel()

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

    override suspend fun getPokemonList(offset: Int, limit: Int): List<PokemonModel> =
        withContext(Dispatchers.IO) {
            val pokemonModelList = mutableListOf<PokemonModel>()
            val pokemonDaoDtoList = pokemonDao.pokemonPagination(offset, limit)
            pokemonDaoDtoList.forEach { pokemonModelList.add(getPokemonByName(it.name)) }
            return@withContext pokemonModelList
        }

    override suspend fun getPokemonSearchList(name: String, offset: Int, limit: Int): List<PokemonModel> =
        withContext(Dispatchers.IO){
            val searchName = "%$name%"
            val pokemonModelList = mutableListOf<PokemonModel>()
            val pokemonDaoDtoList = pokemonDao.searchPokemonByName(searchName, offset, limit)
            pokemonDaoDtoList.forEach { pokemonModelList.add(getPokemonByName(it.name)) }
            return@withContext pokemonModelList
        }

}