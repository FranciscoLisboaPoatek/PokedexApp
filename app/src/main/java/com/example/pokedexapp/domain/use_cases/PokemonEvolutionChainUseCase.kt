package com.example.pokedexapp.domain.use_cases

import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.domain.repository.PokemonRepository
import com.example.pokedexapp.domain.utils.Response
import javax.inject.Inject

class PokemonEvolutionChainUseCase
    @Inject
    constructor(
        private val pokemonRepository: PokemonRepository,
    ) {
        suspend fun getPokemonChain(speciesId: String): Response<PokemonEvolutionChainModel> =
            pokemonRepository.getPokemonEvolutionChain(speciesId = speciesId)
    }
