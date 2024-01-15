package com.example.pokedexapp.domain.use_cases

import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.repository.PokemonRepository

class PokemonListUseCase(
    private val repository: PokemonRepository
) {
    suspend fun insertAllPokemon() {
        repository.savePokemonList()
    }

    suspend fun getPokemonList(offset: Int, limit: Int = 20): List<PokemonModel> {
        return repository.getPokemonList(offset = offset, limit = limit)
    }
}