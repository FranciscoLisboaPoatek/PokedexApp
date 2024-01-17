package com.example.pokedexapp.ui.pokemon_list_screen

import androidx.paging.PagingData
import com.example.pokedexapp.domain.models.PokemonModel
import kotlinx.coroutines.flow.MutableStateFlow

data class PokemonListScreenUiState(
    val isLoading: Boolean = false,
    val isSearchMode: Boolean = false,
    val pokemonList: MutableStateFlow<PagingData<PokemonModel>> = MutableStateFlow(value = PagingData.empty())

)

