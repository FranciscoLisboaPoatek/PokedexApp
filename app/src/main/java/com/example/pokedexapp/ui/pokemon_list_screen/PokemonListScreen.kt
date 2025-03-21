package com.example.pokedexapp.ui.pokemon_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.domain.models.PokemonListItemModel
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.ui.components.PokeballLoadingAnimation
import com.example.pokedexapp.ui.components.PokemonListItem
import com.example.pokedexapp.ui.components.PokemonTopAppBar
import com.example.pokedexapp.ui.components.SearchBar
import com.example.pokedexapp.ui.pokemon_list_screen.PokemonListScreenTestTags.POKEMON_LIST_ITEM_TAG
import com.example.pokedexapp.ui.pokemon_list_screen.PokemonListScreenTestTags.POKEMON_LIST_SEARCH_BAR_TEST_TAG
import com.example.pokedexapp.ui.pokemon_list_screen.PokemonListScreenTestTags.POKEMON_LIST_TAG
import com.example.pokedexapp.ui.pokemon_list_screen.components.ErrorSearching
import com.example.pokedexapp.ui.pokemon_list_screen.components.NoSearchResultsFound
import com.example.pokedexapp.ui.pokemon_list_screen.components.RetryLoadingData
import com.example.pokedexapp.ui.utils.REMAINING_LIST_ITEMS_TO_LOAD_MORE

object PokemonListScreenTestTags {
    const val POKEMON_LIST_TAG = "POKEMON_LIST"
    const val POKEMON_LIST_ITEM_TAG = "POKEMON_LIST_ITEM"
    const val POKEMON_LIST_SEARCH_BAR_TEST_TAG = "POKEMON_LIST_SEARCH_BAR"
}

@Composable
fun PokemonListScreen(
    state: PokemonListScreenUiState,
    onEvent: (PokemonListScreenOnEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val defaultListState = rememberLazyGridState()
    val context = LocalContext.current
    Scaffold(
        modifier = modifier,
        topBar = {
            PokemonTopAppBar(
                areActionsEnabled = state.couldLoadInitialData,
                onSendNotificationClick = {
                    onEvent(
                        PokemonListScreenOnEvent.OnSendNotificationClick(
                            context,
                        ),
                    )
                },
            )
        },
    ) {
        PokemonList(
            uiState = state,
            lazyGridState = if (state.isDefaultList) defaultListState else rememberLazyGridState(),
            onEvent = onEvent,
            modifier = Modifier.padding(it),
        )

        if (state.isLoading) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                PokeballLoadingAnimation(
                    modifier =
                        Modifier
                            .padding(it)
                            .align(Alignment.Center)
                )
            }
        }
    }
}


@Composable
private fun PokemonList(
    uiState: PokemonListScreenUiState,
    lazyGridState: LazyGridState,
    onEvent: (PokemonListScreenOnEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val controller = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = lazyGridState.isScrollInProgress) {
        if (lazyGridState.isScrollInProgress) controller?.hide()
    }

    val gridSpan = remember { 2 }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(gridSpan),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
            state = lazyGridState,
            modifier = Modifier.testTag(POKEMON_LIST_TAG),
        ) {
            item(span = { GridItemSpan(gridSpan) }) {
                SearchBar(
                    modifier = Modifier.testTag(POKEMON_LIST_SEARCH_BAR_TEST_TAG),
                    searchText = uiState.searchText,
                    onClearText = {
                        onEvent(PokemonListScreenOnEvent.ChangeToDefaultList)
                    },
                    onSearchTextChange = {
                        onEvent(
                            PokemonListScreenOnEvent.OnSearchTextValueChange(
                                it
                            )
                        )
                    })
            }

            when {
                !uiState.couldLoadInitialData && !uiState.isLoading -> {
                    item(span = { GridItemSpan(gridSpan) }) {
                        RetryLoadingData(
                            reloadData = { onEvent(PokemonListScreenOnEvent.RetryLoadingData) },
                            modifier =
                                Modifier
                                    .fillMaxSize(),
                        )
                    }
                }

                !uiState.isDefaultList && uiState.showNoSearchResultsFound -> {
                    item(span = { GridItemSpan(gridSpan) }) {
                        NoSearchResultsFound(
                            modifier =
                                Modifier
                                    .padding(top = 24.dp)
                                    .fillMaxWidth(),
                        )
                    }
                }

                !uiState.isDefaultList && uiState.errorSearching -> {
                    item(span = { GridItemSpan(gridSpan) }) {
                        ErrorSearching(
                            modifier =
                                Modifier
                                    .padding(top = 24.dp)
                                    .fillMaxWidth(),
                        )
                    }
                }

                !uiState.isLoading -> {
                    pokemonListItems(
                        uiState.pokemonList,
                        onEvent
                    )

                    if (if (uiState.isDefaultList) uiState.errorAppendingDefaultList else uiState.errorAppendingSearchList) {
                        item(span = { GridItemSpan(gridSpan) }) {
                            RetryLoadingData(
                                reloadData = {
                                    onEvent(PokemonListScreenOnEvent.AppendToList)
                                },
                                modifier = Modifier.wrapContentSize(),
                            )
                        }
                    } else {
                        if (uiState.isLoadingAppend) {
                            item(span = { GridItemSpan(gridSpan) }) {
                                PokeballLoadingAnimation(Modifier.height(100.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun LazyGridScope.pokemonListItems(
    pokemonList: SnapshotStateList<PokemonListItemModel>,
    onEvent: (PokemonListScreenOnEvent) -> Unit,
) {
    items(pokemonList.size, key = { pokemonList[it].id }) { pokemonIndex ->
        val pokemon = pokemonList[pokemonIndex]

        LaunchedEffect(pokemonList.size) {
            if (pokemonIndex == pokemonList.size - REMAINING_LIST_ITEMS_TO_LOAD_MORE) {
                onEvent(PokemonListScreenOnEvent.AppendToList)
            }
        }

        PokemonListItem(
            pokemon = pokemon,
            strokeWidthDp = 10.dp,
            onClick = { onEvent(PokemonListScreenOnEvent.OnPokemonCLick(pokemon.id)) },
            modifier = Modifier.height(210.dp).testTag(POKEMON_LIST_ITEM_TAG),
        )
    }
}

@Preview
@Composable
fun PokemonListScreenPreview() {
    PokemonListScreen(
        PokemonListScreenUiState(
            isLoading = false,
            isLoadingAppend = false,
            couldLoadInitialData = true,
            isDefaultList = true,
            showNoSearchResultsFound = false,
            searchText = "",
            pokemonList = PokemonSampleData.pokemonListSampleData().toMutableStateList(),
        ),
        onEvent = {},
    )
}

@Preview
@Composable
fun PokemonListScreenSearchPreview() {
    PokemonListScreen(
        PokemonListScreenUiState(
            isLoading = false,
            isLoadingAppend = false,
            couldLoadInitialData = true,
            isDefaultList = false,
            showNoSearchResultsFound = false,
            searchText = "",
            pokemonList = PokemonSampleData.pokemonListSampleData().toMutableStateList(),
        ),
        onEvent = {},
    )
}

@Preview
@Composable
fun PokemonListScreenLoadingPreview() {
    PokemonListScreen(
        PokemonListScreenUiState(
            isLoading = true,
            isLoadingAppend = false,
            couldLoadInitialData = true,
            isDefaultList = true,
            showNoSearchResultsFound = false,
            searchText = "",
            pokemonList = PokemonSampleData.pokemonListSampleData().toMutableStateList(),
        ),
        onEvent = {},
    )
}

@Preview
@Composable
fun PokemonListScreenLoadingAppendPreview() {
    PokemonListScreen(
        PokemonListScreenUiState(
            isLoading = false,
            isLoadingAppend = true,
            couldLoadInitialData = true,
            isDefaultList = true,
            showNoSearchResultsFound = false,
            searchText = "",
            pokemonList = PokemonSampleData.pokemonSearchListSampleData().toMutableStateList(),
        ),
        onEvent = {},
    )
}
