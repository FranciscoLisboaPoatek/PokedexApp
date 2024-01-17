package com.example.pokedexapp.domain.use_cases

import androidx.paging.PagingData
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class PokemonListUseCase(
    private val repository: PokemonRepository
) {
    suspend fun insertAllPokemon() {
        repository.savePokemonList()
    }

    suspend fun getPokemonList(): Flow<PagingData<PokemonModel>> {
        return repository.getPokemonList()
    }
}