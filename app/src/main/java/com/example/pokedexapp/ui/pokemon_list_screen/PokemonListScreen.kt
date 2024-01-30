package com.example.pokedexapp.ui.pokemon_list_screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
                viewModel.changeSearchText(event.text)
            }

            is PokemonListScreenOnEvent.OnPokemonCLick -> {
                navigateToDetails(event.pokemonId)
            }

            PokemonListScreenOnEvent.AppendToList -> {
                if (state.isSearchMode && !state.searchListEnded && !state.isLoadingAppend) viewModel.appendSearchList()
                else if (!state.isSearchMode && !state.defaultListEnded && !state.isLoadingAppend) viewModel.getPokemonList()
            }
        }
    }

    PokemonListScreenContent(
        isLoading = state.isLoading,
        isLoadingAppend = state.isLoadingAppend,
        isSearchMode = state.isSearchMode,
        isDefaultList = state.isDefaultList,
        searchText = state.searchText,
        pokemonList = state.pokemonList,
        onEvent = ::onEvent
    )
}

@Composable
private fun PokemonListScreenContent(
    isLoading: Boolean,
    isLoadingAppend: Boolean,
    isSearchMode: Boolean,
    isDefaultList: Boolean,
    searchText: String,
    pokemonList: SnapshotStateList<PokemonModel>,
    onEvent: (PokemonListScreenOnEvent) -> Unit
) {
    val defaultListState = rememberLazyGridState()
    Scaffold(
        topBar = {
            PokemonTopAppBar(
                searchText = searchText,
                searchMode = isSearchMode,
                onSearchTextChange = { onEvent(PokemonListScreenOnEvent.OnSearchTextValueChange(it)) },
                handleSearchClick = { onEvent(PokemonListScreenOnEvent.OnSearchClick) }
            )
        }
    ) {
        if (isLoading) {
            LoadingScreen(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
        } else {
            PokemonList(
                pokemonList = pokemonList,
                state = if (isDefaultList) defaultListState else rememberLazyGridState(),
                onEvent = onEvent,
                isLoadingAppend = isLoadingAppend,
                modifier = Modifier.padding(it)
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PokemonList(
    pokemonList: SnapshotStateList<PokemonModel>,
    state: LazyGridState,
    onEvent: (PokemonListScreenOnEvent) -> Unit,
    isLoadingAppend: Boolean,
    modifier: Modifier
) {
    val controller = LocalSoftwareKeyboardController.current
    SideEffect {
        if (state.isScrollInProgress) controller?.hide()
    }
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
            items(pokemonList.size, key = { pokemonList[it].id }) { pokemonIndex ->
                val pokemon = pokemonList[pokemonIndex]

                LaunchedEffect(pokemonList.size) {
                    if (pokemonIndex == pokemonList.size - 10) {
                        onEvent(PokemonListScreenOnEvent.AppendToList)
                    }
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

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
        )
    }
}

@Preview
@Composable
fun PokemonListScreenPreview() {
    PokemonListScreenContent(
        isLoading = false,
        isLoadingAppend = false,
        isSearchMode = false,
        isDefaultList = true,
        searchText = "",
        pokemonList = PokemonSampleData.pokemonListSampleData() as SnapshotStateList<PokemonModel>,
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
        isDefaultList = false,
        searchText = "",
        pokemonList = PokemonSampleData.pokemonListSampleData() as SnapshotStateList<PokemonModel>,
        onEvent = {}
    )
}

@Preview
@Composable
fun PokemonListScreenLoadingPreview() {
    PokemonListScreenContent(
        isLoading = true,
        isLoadingAppend = false,
        isSearchMode = false,
        isDefaultList = true,
        searchText = "",
        pokemonList = PokemonSampleData.pokemonListSampleData() as SnapshotStateList<PokemonModel>,
        onEvent = {}
    )
}

@Preview
@Composable
fun PokemonListScreenLoadingAppendPreview() {
    PokemonListScreenContent(
        isLoading = false,
        isLoadingAppend = true,
        isSearchMode = false,
        isDefaultList = true,
        searchText = "",
        pokemonList = PokemonSampleData.pokemonSearchListSampleData() as SnapshotStateList<PokemonModel>,
        onEvent = {}
    )
}
