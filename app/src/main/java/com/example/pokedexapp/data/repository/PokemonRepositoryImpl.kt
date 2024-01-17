package com.example.pokedexapp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.pokedexapp.data.PokemonMapper.toPokemonDaoDto
import com.example.pokedexapp.data.PokemonMapper.toPokemonModel
import com.example.pokedexapp.data.local_database.PokemonDao
import com.example.pokedexapp.data.local_database.PokemonDaoDto
import com.example.pokedexapp.data.network.PokemonApi
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val pokemonDao: PokemonDao,
    private val pager: Pager<Int, PokemonDaoDto>
) : PokemonRepository {
    override suspend fun getPokemonById(pokemonId: String): PokemonModel =
        withContext(Dispatchers.IO) {
            val pokemonApiDto = pokemonApi.getPokemonById(pokemonId.toInt())
            return@withContext pokemonApiDto.toPokemonModel()
        }

    override suspend fun getPokemonByName(name: String): PokemonModel =
        withContext(Dispatchers.IO) {
            val pokemon = pokemonApi.getPokemonByName(name)
            Log.w("list", pokemon.toString())
            return@withContext pokemon.toPokemonModel()

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

    override suspend fun getPokemonList(): Flow<PagingData<PokemonModel>> =
        pager.flow.map { pagingData ->
            pagingData.map {
                getPokemonByName(it.name)
            }
        }
}