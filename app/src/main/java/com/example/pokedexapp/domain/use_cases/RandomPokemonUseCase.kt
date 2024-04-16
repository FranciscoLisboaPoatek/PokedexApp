package com.example.pokedexapp.domain.use_cases

import com.example.pokedexapp.domain.models.PokemonMinimalInfo
import com.example.pokedexapp.domain.repository.PokemonRepository

class RandomPokemonUseCase(
    private val repository: PokemonRepository,
) {
    suspend fun getRandomPokemonMinimalInfo(): PokemonMinimalInfo {
        return repository.getRandomPokemonMinimalInfo()
    }
}
