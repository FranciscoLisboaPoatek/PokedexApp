package com.example.pokedexapp.domain.repository

import com.example.pokedexapp.domain.models.PokemonModel

interface PokemonRepository {
    fun getPokemonById(pokemonId: String): PokemonModel?
}