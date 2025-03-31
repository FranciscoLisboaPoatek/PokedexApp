package com.example.pokedexapp.domain.use_cases

import com.example.pokedexapp.domain.models.PokemonListItemModel
import com.example.pokedexapp.domain.repository.PokemonRepository
import com.example.pokedexapp.domain.utils.Response

class PokemonListUseCase(
    private val repository: PokemonRepository,
) {
    suspend fun insertAllPokemon(): Response<Unit> = repository.savePokemonList()

    suspend fun getPokemonList(
        offset: Int,
        limit: Int,
    ): Response<List<PokemonListItemModel>> = repository.getPokemonList(offset = offset, limit = limit)

    suspend fun getPokemonSearchList(
        name: String,
        offset: Int,
        limit: Int,
    ): Response<List<PokemonListItemModel>> = repository.getPokemonSearchList(name = name, offset = offset, limit = limit)
}
