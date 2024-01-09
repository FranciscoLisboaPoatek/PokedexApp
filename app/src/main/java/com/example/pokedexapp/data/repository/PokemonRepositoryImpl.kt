package com.example.pokedexapp.data.repository

import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.repository.PokemonRepository
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(): PokemonRepository {
    override fun getPokemonById(pokemonId: String): PokemonModel? {
        val pokemonList = PokemonSampleData.pokemonListSampleData()
        return pokemonList.find { it.id == pokemonId }
    }
}