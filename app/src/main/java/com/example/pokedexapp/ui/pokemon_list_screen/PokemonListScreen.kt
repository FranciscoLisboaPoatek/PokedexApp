package com.example.pokedexapp.ui.pokemon_list_screen


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.ui.components.PokemonListItem
import com.example.pokedexapp.ui.components.PokemonTopAppBar

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel = hiltViewModel(),
    navigateToDetails: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    fun onEvent(event: PokemonListScreenOnEvent) {
        when (event) {
            is PokemonListScreenOnEvent.OnSearchClick -> {
                viewModel.changeIsSearchMode()
            }

            is PokemonListScreenOnEvent.OnSearchTextValueChange -> {
                viewModel.searchPokemonByName(event.text)
            }

            is PokemonListScreenOnEvent.OnPokemonCLick -> {
                navigateToDetails(event.pokemonId)
            }
        }
    }

    PokemonListScreenContent(
        isSearchMode = state.isSearchMode,
        pokemonList = state.pokemonList.collectAsLazyPagingItems(),
        onEvent = ::onEvent
    )
}

@Composable
private fun PokemonListScreenContent(
    isSearchMode: Boolean,
    pokemonList: LazyPagingItems<PokemonModel>,
    onEvent: (PokemonListScreenOnEvent) -> Unit
) {
    Scaffold(
        topBar = {
            PokemonTopAppBar(
                searchMode = isSearchMode,
                onSearchTextChange = { onEvent(PokemonListScreenOnEvent.OnSearchTextValueChange(it)) },
                handleSearchClick = { onEvent(PokemonListScreenOnEvent.OnSearchClick) }
            )
        }
    ) {
        PokemonList(pokemonList = pokemonList, onEvent = onEvent, modifier = Modifier.padding(it))
    }
}

@Composable
private fun PokemonList(
    pokemonList: LazyPagingItems<PokemonModel>,
    onEvent: (PokemonListScreenOnEvent) -> Unit,
    modifier: Modifier
) {
    val state = rememberLazyGridState()
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = state,
            contentPadding = PaddingValues(16.dp)
        ) {
            items(pokemonList.itemCount) { index ->
                val pokemon = pokemonList[index]
                if (pokemon != null) {
                    PokemonListItem(
                        pokemon = pokemon,
                        strokeWidthDp = 10.dp,
                        onClick = { onEvent(PokemonListScreenOnEvent.OnPokemonCLick(pokemon.id)) },
                        modifier = Modifier.height(210.dp)
                    )
                }
            }
            item {
                when (pokemonList.loadState.append) {
                    LoadState.Loading -> {
                        Log.w("list", "loading, ${pokemonList.itemSnapshotList.size}")
                        CircularProgressIndicator(modifier = Modifier.height(210.dp))
                    }

                    is LoadState.Error -> {
                        Log.w("list", "error")

                    }

                    is LoadState.NotLoading -> {
                        Log.w("list", "not loading")

                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun PokemonListScreenPreview() {
//    PokemonListScreenContent(
//        isSearchMode = false,
//        pokemonList = PokemonSampleData.pokemonListSampleData(),
//        onEvent = {}
//    )
}

@Preview
@Composable
fun PokemonListScreenSearchPreview() {
//    PokemonListScreenContent(
//        isSearchMode = true,
//        pokemonList = PokemonSampleData.pokemonListSampleData(),
//        onEvent = {}
//    )
}
