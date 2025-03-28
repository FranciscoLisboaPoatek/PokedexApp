package com.example.pokedexapp.domain.use_cases

import com.example.pokedexapp.domain.models.SharePokemonModel
import com.example.pokedexapp.domain.repository.PokemonRepository
import com.example.pokedexapp.domain.utils.Response

class SharePokemonUseCase(
    private val repository: PokemonRepository,
) {
    suspend fun sharePokemonTo(sharePokemonModel: SharePokemonModel): Response<Unit> =
        repository.sharePokemonToReceiver(sharePokemonModel = sharePokemonModel)
}
