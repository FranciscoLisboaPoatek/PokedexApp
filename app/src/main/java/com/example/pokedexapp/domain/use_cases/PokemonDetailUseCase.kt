package com.example.pokedexapp.domain.use_cases

import android.util.Log
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonDetailUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend fun getPokemonById(pokemonId: String):PokemonModel?{
        Log.w("detail", "use case")

        return pokemonRepository.getPokemonById(pokemonId = pokemonId)
    }
}