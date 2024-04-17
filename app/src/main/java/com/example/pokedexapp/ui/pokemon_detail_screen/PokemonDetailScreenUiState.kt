package com.example.pokedexapp.ui.pokemon_detail_screen

import com.example.pokedexapp.domain.models.PokemonDetailModel
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.domain.models.PokemonSprite

data class PokemonDetailScreenUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val pokemonDetailModel: PokemonDetailModel? = null,
    val pokemonSprite: PokemonSprite? = pokemonDetailModel?.frontDefaultSprite,
    val evolutionChain: PokemonEvolutionChainModel = PokemonEvolutionChainModel(),
    val receiverToken: String = "",
    val isSharingPokemonToReceiver: Boolean = false,
    val isErrorSharingPokemonToReceiver: Boolean = false,
)
