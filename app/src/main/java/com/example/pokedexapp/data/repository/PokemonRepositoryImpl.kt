package com.example.pokedexapp.data.repository

import com.example.pokedexapp.data.PokemonMapper
import com.example.pokedexapp.data.network.PokemonApi
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
   private val pokemonApi: PokemonApi,
): PokemonRepository {
    override suspend fun getPokemonById(pokemonId: String): PokemonModel =
          withContext(Dispatchers.IO){
              val pokemonApiDto = pokemonApi.getPokemonById(pokemonId.toInt())
              return@withContext PokemonMapper.pokemonApiDtoToPokemonModel(pokemonApiDto)
          }

    override suspend fun savePokemonList() {
        withContext(Dispatchers.IO){
            pokemonApi.getPokemonEntireList()
            //TODO SAVE IN LOCAL DATABASE
        }
    }

}