package com.example.pokedexapp.domain.use_cases

import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonDetailUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    fun getPokemonById(pokemonId: String):PokemonModel?{
        return pokemonRepository.getPokemonById(pokemonId = pokemonId)
    }
}