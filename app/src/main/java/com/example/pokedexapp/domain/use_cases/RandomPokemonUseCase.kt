package com.example.pokedexapp.domain.use_cases

import com.example.pokedexapp.domain.models.PokemonMinimalInfo
import com.example.pokedexapp.domain.repository.PokemonRepository
import com.example.pokedexapp.domain.utils.Response

class RandomPokemonUseCase(
    private val repository: PokemonRepository,
) {
    suspend fun getRandomPokemonMinimalInfo(): Response<PokemonMinimalInfo> = repository.getRandomPokemonMinimalInfo()
}
