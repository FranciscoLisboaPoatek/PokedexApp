package com.example.pokedexapp.domain.repository

import com.example.pokedexapp.domain.models.PokemonModel

interface PokemonRepository {
    suspend fun getPokemonById(pokemonId: String): PokemonModel?

    suspend fun savePokemonList()
}