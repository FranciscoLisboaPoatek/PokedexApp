package com.example.pokedexapp.domain.use_cases

import com.example.pokedexapp.domain.models.SharePokemonModel
import com.example.pokedexapp.domain.repository.PokemonRepository

class SharePokemonUseCase (
    private val repository: PokemonRepository
){
    suspend fun sharePokemonTo(sharePokemonModel: SharePokemonModel){
        repository.sharePokemonToReceiver(sharePokemonModel = sharePokemonModel)
    }
}