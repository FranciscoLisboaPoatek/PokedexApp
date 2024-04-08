package com.example.pokedexapp.ui.pokemon_list_screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.models.PokemonListItemModel
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.ui.components.PokeballLoadingAnimation
import com.example.pokedexapp.ui.components.PokemonListItem
import com.example.pokedexapp.ui.components.PokemonTopAppBar
import com.example.pokedexapp.ui.theme.TopBarBlueColor

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
                if (state.isDefaultList) viewModel.getPokemonList()
                else viewModel.appendSearchList()
            }

            PokemonListScreenOnEvent.RetryLoadingData -> {
                viewModel.loadInitialData()
            }
        }
    }
    PokemonListScreenContent(
        state = state,
        onEvent = ::onEvent
    )
}

@Composable
private fun PokemonListScreenContent(
    state: PokemonListScreenUiState,
    onEvent: (PokemonListScreenOnEvent) -> Unit
) {
    val defaultListState = rememberLazyGridState()
    Scaffold(
        topBar = {
            PokemonTopAppBar(
                searchText = state.searchText,
                searchMode = state.isSearchMode,
                enableSearch = state.couldLoadInitialData,
                onSearchTextChange = { onEvent(PokemonListScreenOnEvent.OnSearchTextValueChange(it)) },
                handleSearchClick = { onEvent(PokemonListScreenOnEvent.OnSearchClick) }
            )
        },
    ) {
        if (state.isLoading) {
            PokeballLoadingAnimation(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
        } else {
            if (!state.couldLoadInitialData) {
                RetryLoadingData(
                    reloadData = { onEvent(PokemonListScreenOnEvent.RetryLoadingData) },
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                )
            } else
                if (state.isSearchMode && state.showNoSearchResultsFound) {
                    NoSearchResultsFound(
                        modifier = Modifier
                            .padding(it)
                            .padding(top = 250.dp)
                            .fillMaxWidth()
                    )
                }
                else if(state.isSearchMode && state.errorSearching){
                    ErrorSearching(modifier = Modifier
                        .padding(it)
                        .padding(top = 250.dp)
                        .fillMaxWidth()
                    )
                }else {
                    PokemonList(
                        pokemonList = state.pokemonList,
                        state = if (state.isDefaultList) defaultListState else rememberLazyGridState(),
                        onEvent = onEvent,
                        isLoadingAppend = state.isLoadingAppend,
                        errorAppending = if(state.isDefaultList) state.errorAppendingDefaultList else state.errorAppendingSearchList,
                        modifier = Modifier.padding(it)
                    )
                }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PokemonList(
    pokemonList: SnapshotStateList<PokemonListItemModel>,
    state: LazyGridState,
    onEvent: (PokemonListScreenOnEvent) -> Unit,
    isLoadingAppend: Boolean,
    errorAppending: Boolean,
    modifier: Modifier
) {
    val controller = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = state.isScrollInProgress) {
        if (state.isScrollInProgress) controller?.hide()
    }
    val gridSpan = 2
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(gridSpan),
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

            if (errorAppending) {
                item(span = { GridItemSpan(gridSpan) }) {

                    RetryLoadingData(
                        reloadData = {
                            onEvent(PokemonListScreenOnEvent.AppendToList)
                        },
                        modifier = Modifier.wrapContentSize()
                    )
                }
            } else {
                if (isLoadingAppend) {
                    item(span = { GridItemSpan(gridSpan) }) {
                        PokeballLoadingAnimation(Modifier.height(100.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun NoSearchResultsFound(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.no_search_results_found),
            color = Color.Gray,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun ErrorSearching(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            text = stringResource(R.string.error_searching_content),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun RetryLoadingData(reloadData: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        IconButton(onClick = { reloadData() }) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(R.string.retry_loading),
                tint = TopBarBlueColor,
                modifier = Modifier.size(50.dp)
            )
        }
        Text(
            text = stringResource(R.string.something_went_wrong),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Preview
@Composable
fun PokemonListScreenPreview() {
    PokemonListScreenContent(
        PokemonListScreenUiState(
            isLoading = false,
            isLoadingAppend = false,
            couldLoadInitialData = true,
            isSearchMode = false,
            isDefaultList = true,
            showNoSearchResultsFound = false,
            searchText = "",
            pokemonList = PokemonSampleData.pokemonListSampleData().toMutableStateList()
        ),
        onEvent = {}
    )
}

@Preview
@Composable
fun PokemonListScreenSearchPreview() {
    PokemonListScreenContent(
        PokemonListScreenUiState(
            isLoading = false,
            isLoadingAppend = false,
            couldLoadInitialData = true,
            isSearchMode = true,
            isDefaultList = false,
            showNoSearchResultsFound = false,
            searchText = "",
            pokemonList = PokemonSampleData.pokemonListSampleData().toMutableStateList() ,
        ),
        onEvent = {}
    )
}

@Preview
@Composable
fun PokemonListScreenLoadingPreview() {
    PokemonListScreenContent(
        PokemonListScreenUiState(
            isLoading = true,
            isLoadingAppend = false,
            couldLoadInitialData = true,
            isSearchMode = false,
            isDefaultList = true,
            showNoSearchResultsFound = false,
            searchText = "",
            pokemonList = PokemonSampleData.pokemonListSampleData().toMutableStateList() ,
        ),
        onEvent = {}
    )
}

@Preview
@Composable
fun PokemonListScreenLoadingAppendPreview() {
    PokemonListScreenContent(
        PokemonListScreenUiState(
            isLoading = false,
            isLoadingAppend = true,
            couldLoadInitialData = true,
            isSearchMode = false,
            isDefaultList = true,
            showNoSearchResultsFound = false,
            searchText = "",
            pokemonList = PokemonSampleData.pokemonSearchListSampleData().toMutableStateList() ,
        ),
        onEvent = {}
    )
}
