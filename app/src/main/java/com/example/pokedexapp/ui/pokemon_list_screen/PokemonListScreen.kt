package com.example.pokedexapp.ui.pokemon_list_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import com.example.pokedexapp.ui.theme.SurfaceColorDark
import com.example.pokedexapp.ui.theme.SurfaceColorLight
import com.example.pokedexapp.ui.theme.TopBarBlueColor
import com.example.pokedexapp.ui.utils.REMAINING_LIST_ITEMS_TO_LOAD_MORE
import kotlinx.coroutines.launch

object PokemonListScreenTestTags {
    const val POKEMON_LIST_TAG = "POKEMON_LIST"
    const val POKEMON_LIST_ITEM_TAG = "POKEMON_LIST_ITEM"
    const val POKEMON_LIST_SEARCH_BAR_TEST_TAG = "POKEMON_LIST_SEARCH_BAR"
}

private val SEARCH_BAR_TOP_PADDING = 8.dp
private val SEARCH_BAR_BOTTOM_PADDING = 16.dp

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
                    .background(Color.Transparent),
            ) {
                PokeballLoadingAnimation(
                    modifier =
                        Modifier
                            .padding(it)
                            .align(Alignment.Center),
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
    var topSpacing by remember {
        mutableIntStateOf(0)
    }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        if (!uiState.isLoading) {
            if (!uiState.couldLoadInitialData) {
                RetryLoadingData(
                    reloadData = { onEvent(PokemonListScreenOnEvent.RetryLoadingData) },
                    modifier =
                        Modifier
                            .fillMaxSize(),
                )
            } else {
                PokemonGridWrapper(
                    uiState = uiState,
                    lazyGridState = lazyGridState,
                    topSpacing = topSpacing,
                    onEvent = onEvent,
                )
            }
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = lazyGridState.canScrollBackward && (!uiState.errorAppendingSearchList && lazyGridState.canScrollForward),
            enter =
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec =
                        spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow,
                        ),
                ),
            exit =
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec =
                        spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium,
                        ),
                ),
        ) {
            ScrollToTopButton {
                lazyGridState.animateScrollToItem(0)
            }
        }

        val shadowSize by animateDpAsState(if (uiState.couldLoadInitialData)10.dp else 0.dp)

        SearchBar(
            modifier =
                Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = SEARCH_BAR_BOTTOM_PADDING)
                    .background(
                        MaterialTheme.colorScheme.background,
                        RoundedCornerShape(0, 0, 50, 50),
                    )
                    .padding(top = SEARCH_BAR_TOP_PADDING)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .testTag(POKEMON_LIST_SEARCH_BAR_TEST_TAG)
                    .onGloballyPositioned { topSpacing = it.size.height }
                    .shadow(shadowSize, shape = CircleShape),
            searchText = uiState.searchText,
            enabled = uiState.couldLoadInitialData,
            onClearText = {
                onEvent(PokemonListScreenOnEvent.ChangeToDefaultList)
            },
            onSearchTextChange = {
                onEvent(
                    PokemonListScreenOnEvent.OnSearchTextValueChange(
                        it,
                    ),
                )
            },
        )
    }
}

@Composable
private fun PokemonGridWrapper(
    uiState: PokemonListScreenUiState,
    lazyGridState: LazyGridState,
    topSpacing: Int,
    onEvent: (PokemonListScreenOnEvent) -> Unit,
) {
    val controller = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = lazyGridState.isScrollInProgress) {
        if (lazyGridState.isScrollInProgress) controller?.hide()
    }

    val gridSpan = remember { 2 }

    val density = LocalDensity.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(gridSpan),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding =
            PaddingValues(
                top = with(density) { topSpacing.toDp() } + SEARCH_BAR_TOP_PADDING + SEARCH_BAR_BOTTOM_PADDING,
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp,
            ),
        state = lazyGridState,
        modifier = Modifier.testTag(POKEMON_LIST_TAG),
    ) {
        when {
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

            else -> {
                pokemonListItems(
                    uiState.pokemonList,
                    onEvent,
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
            modifier =
                Modifier
                    .height(210.dp)
                    .testTag(POKEMON_LIST_ITEM_TAG),
        )
    }
}

@Composable
private fun ScrollToTopButton(
    modifier: Modifier = Modifier,
    scrollToTop: suspend () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    IconButton(
        modifier =
            modifier
                .padding(bottom = 24.dp)
                .shadow(10.dp, shape = CircleShape)
                .border(2.dp, TopBarBlueColor, CircleShape)
                .clip(CircleShape)
                .size(48.dp),
        colors =
            IconButtonDefaults.iconButtonColors(
                containerColor = if (isSystemInDarkTheme()) SurfaceColorDark else SurfaceColorLight,
            ),
        onClick = {
            coroutineScope.launch {
                scrollToTop()
            }
        },
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = null,
            tint = TopBarBlueColor,
            modifier =
                Modifier
                    .fillMaxSize(),
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
