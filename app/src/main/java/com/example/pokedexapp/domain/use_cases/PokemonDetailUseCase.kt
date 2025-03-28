package com.example.pokedexapp.domain.use_cases

import com.example.pokedexapp.domain.models.PokemonDetailModel
import com.example.pokedexapp.domain.repository.PokemonRepository
import com.example.pokedexapp.domain.utils.Response
import javax.inject.Inject

class PokemonDetailUseCase
    @Inject
    constructor(
        private val pokemonRepository: PokemonRepository,
    ) {
        suspend fun getPokemonById(pokemonId: String): Response<PokemonDetailModel> =
            pokemonRepository.getPokemonDetailById(pokemonId = pokemonId)
    }
