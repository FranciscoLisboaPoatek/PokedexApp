package com.example.pokedexapp.ui.pokemon_list_screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
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

            PokemonListScreenOnEvent.appendToDefaultList -> {
                viewModel.getPokemonList()
            }
        }
    }

    PokemonListScreenContent(
        isLoading = state.isLoading,
        isLoadingAppend = state.isLoadingAppend,
        isSearchMode = state.isSearchMode,
        pokemonList = state.pokemonList,
        onEvent = ::onEvent
    )
}

@Composable
private fun PokemonListScreenContent(
    isLoading: Boolean,
    isLoadingAppend: Boolean,
    isSearchMode: Boolean,
    pokemonList: List<PokemonModel>,
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
            PokemonList(
                pokemonList = pokemonList,
                onEvent = onEvent,
                isLoadingAppend = isLoadingAppend,
                modifier = Modifier.padding(it)
            )

    }
}

@Composable
private fun PokemonList(
    pokemonList: List<PokemonModel>,
    onEvent: (PokemonListScreenOnEvent) -> Unit,
    isLoadingAppend: Boolean,
    modifier: Modifier
) {
    val state = rememberLazyGridState()
    val GRID_SPAN = 2
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_SPAN),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
            state = state
        ) {
            items(pokemonList.size) { pokemonIndex ->
                val pokemon = pokemonList[pokemonIndex]

                if (pokemonIndex == pokemonList.size - 10) {
                    onEvent(PokemonListScreenOnEvent.appendToDefaultList)
                }

                PokemonListItem(
                    pokemon = pokemon,
                    strokeWidthDp = 10.dp,
                    onClick = { onEvent(PokemonListScreenOnEvent.OnPokemonCLick(pokemon.id)) },
                    modifier = Modifier.height(210.dp)
                )
            }

            if (isLoadingAppend) {
                item(span = { GridItemSpan(GRID_SPAN) }) {
                    Box(Modifier.height(100.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PokemonListScreenPreview() {
    PokemonListScreenContent(
        isLoading = false,
        isLoadingAppend = false,
        isSearchMode = false,
        pokemonList = PokemonSampleData.pokemonListSampleData(),
        onEvent = {}
    )
}

@Preview
@Composable
fun PokemonListScreenSearchPreview() {
    PokemonListScreenContent(
        isLoading = false,
        isLoadingAppend = false,
        isSearchMode = true,
        pokemonList = PokemonSampleData.pokemonListSampleData(),
        onEvent = {}
    )
}
