package com.example.pokedexapp.domain.use_cases

import com.example.pokedexapp.domain.models.PokemonListItemModel
import com.example.pokedexapp.domain.repository.PokemonRepository

class PokemonListUseCase(
    private val repository: PokemonRepository,
) {
    suspend fun insertAllPokemon() {
        repository.savePokemonList()
    }

    suspend fun getPokemonList(
        offset: Int,
        limit: Int,
    ): List<PokemonListItemModel> {
        return repository.getPokemonList(offset = offset, limit = limit)
    }

    suspend fun getPokemonSearchList(
        name: String,
        offset: Int,
        limit: Int,
    ): List<PokemonListItemModel> {
        return repository.getPokemonSearchList(name = name, offset = offset, limit = limit)
    }
}
