package com.example.pokedexapp.ui.pokemon_list_screen

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.pokedexapp.domain.models.PokemonListItemModel

@Immutable
data class PokemonListScreenUiState(
    val isLoading: Boolean = false,
    val isLoadingAppend: Boolean = false,
    val errorAppendingDefaultList: Boolean = false,
    val errorAppendingSearchList: Boolean = false,
    val errorSearching: Boolean = false,
    val couldLoadInitialData: Boolean = false,
    val isDefaultList: Boolean = true,
    val defaultListEnded: Boolean = false,
    val searchListEnded: Boolean = false,
    val showNoSearchResultsFound: Boolean = false,
    val searchText: String = "",
    val pokemonList: SnapshotStateList<PokemonListItemModel> = SnapshotStateList(),
)
