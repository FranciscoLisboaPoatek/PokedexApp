package com.example.pokedexapp.domain.use_cases

import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonEvolutionChainUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend fun getPokemonChain(pokemonId: String): PokemonEvolutionChainModel{
        return pokemonRepository.getPokemonEvolutionChain(pokemonId = pokemonId)
    }
}