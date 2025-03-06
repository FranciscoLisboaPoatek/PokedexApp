package com.example.pokedexapp.ui.pokemon_list_screen

import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.ui.components.PokeballLoadingAnimationTestTags.POKEBALL_LOADING_ANIMATION_TAG
import com.example.pokedexapp.ui.components.PokemonTopAppBarTestTags
import com.example.pokedexapp.ui.components.PokemonTopAppBarTestTags.OPEN_SEARCH_BUTTON_TAG
import com.example.pokedexapp.ui.components.PokemonTopAppBarTestTags.SHOW_RANDOM_POKEMON_NOTIFICATION_BUTTON_TAG
import com.example.pokedexapp.ui.pokemon_list_screen.PokemonListScreenTestTags.POKEMON_LIST_TAG
import com.example.pokedexapp.ui.pokemon_list_screen.components.ErrorSearchingTestTags.ERROR_SEARCHING_TAG
import com.example.pokedexapp.ui.pokemon_list_screen.components.NoSearchResultsFoundTestTag.NO_SEARCH_RESULT_FOUND_TAG
import com.example.pokedexapp.ui.pokemon_list_screen.components.RetryLoadingDataTestTags.RETRY_LOADING_DATA_TAG
import org.junit.Rule
import org.junit.Test

class PokemonListScreenTest {
    @get:Rule
    var composeTestRule = createComposeRule()

    @Test
    fun loadInitialData_isShowing_error() {
        composeTestRule.setContent {
            PokemonListScreen(
                state =
                    PokemonListScreenUiState(
                        couldLoadInitialData = false,
                    ),
                onEvent = { },
            )
        }

        composeTestRule.onNodeWithTag(RETRY_LOADING_DATA_TAG)
            .assertExists()
        composeTestRule.onNodeWithTag(OPEN_SEARCH_BUTTON_TAG).assertIsNotEnabled()
        composeTestRule.onNodeWithTag(SHOW_RANDOM_POKEMON_NOTIFICATION_BUTTON_TAG)
            .assertIsNotEnabled()
        composeTestRule.onNodeWithTag(POKEMON_LIST_TAG).assertDoesNotExist()
    }

    @Test
    fun loadInitialData_isShowing_loading() {
        composeTestRule.setContent {
            PokemonListScreen(
                state =
                    PokemonListScreenUiState(
                        isLoading = true,
                    ),
                onEvent = { },
            )
        }

        composeTestRule.onNodeWithTag(POKEBALL_LOADING_ANIMATION_TAG)
            .assertExists()
        composeTestRule.onNodeWithTag(OPEN_SEARCH_BUTTON_TAG).assertIsNotEnabled()
        composeTestRule.onNodeWithTag(SHOW_RANDOM_POKEMON_NOTIFICATION_BUTTON_TAG)
            .assertIsNotEnabled()
        composeTestRule.onNodeWithTag(POKEMON_LIST_TAG).assertDoesNotExist()
    }

    @Test
    fun loadInitialData_success() {
        composeTestRule.setContent {
            PokemonListScreen(
                state =
                    PokemonListScreenUiState(
                        couldLoadInitialData = true,
                        pokemonList = PokemonSampleData.pokemonListSampleData().toMutableStateList(),
                    ),
                onEvent = { },
            )
        }

        composeTestRule.onNodeWithTag(POKEMON_LIST_TAG).assertExists()
        composeTestRule.onNodeWithText(PokemonSampleData.pokemonListSampleData()[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithTag(OPEN_SEARCH_BUTTON_TAG).assertIsEnabled()
        composeTestRule.onNodeWithTag(SHOW_RANDOM_POKEMON_NOTIFICATION_BUTTON_TAG)
            .assertIsEnabled()
    }

    @Test
    fun appendToList_isShowing_loading() {
        composeTestRule.setContent {
            PokemonListScreen(
                state =
                    PokemonListScreenUiState(
                        isLoadingAppend = true,
                        couldLoadInitialData = true,
                        pokemonList = PokemonSampleData.pokemonListSampleData().toMutableStateList(),
                    ),
                onEvent = { },
            )
        }

        composeTestRule.onNodeWithTag(POKEMON_LIST_TAG).performScrollToNode(
            hasTestTag(POKEBALL_LOADING_ANIMATION_TAG),
        ).assertIsDisplayed()
    }

    @Test
    fun appendToList_isShowing_error() {
        composeTestRule.setContent {
            PokemonListScreen(
                state =
                    PokemonListScreenUiState(
                        errorAppendingDefaultList = true,
                        couldLoadInitialData = true,
                        pokemonList = PokemonSampleData.pokemonListSampleData().toMutableStateList(),
                    ),
                onEvent = { },
            )
        }

        composeTestRule.onNodeWithTag(POKEMON_LIST_TAG).performScrollToNode(
            hasTestTag(RETRY_LOADING_DATA_TAG),
        ).assertIsDisplayed()
    }

    @Test
    fun searchMode() {
        composeTestRule.setContent {
            PokemonListScreen(
                state =
                    PokemonListScreenUiState(
                        isSearchMode = true,
                        couldLoadInitialData = true,
                        pokemonList = PokemonSampleData.pokemonListSampleData().toMutableStateList(),
                    ),
                onEvent = { },
            )
        }

        composeTestRule.onNodeWithTag(PokemonTopAppBarTestTags.SEARCH_TEXT_FIELD_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(PokemonTopAppBarTestTags.CLOSE_SEARCH_BUTTON_TAG).assertIsEnabled()
    }

    @Test
    fun search_isShowing_error() {
        composeTestRule.setContent {
            PokemonListScreen(
                state =
                    PokemonListScreenUiState(
                        errorSearching = true,
                        isSearchMode = true,
                        couldLoadInitialData = true,
                        pokemonList = PokemonSampleData.pokemonListSampleData().toMutableStateList(),
                    ),
                onEvent = { },
            )
        }

        composeTestRule.onNodeWithTag(ERROR_SEARCHING_TAG).assertIsDisplayed()
    }

    @Test
    fun search_isShowing_noResultsFound() {
        composeTestRule.setContent {
            PokemonListScreen(
                state =
                    PokemonListScreenUiState(
                        isSearchMode = true,
                        couldLoadInitialData = true,
                        showNoSearchResultsFound = true,
                        pokemonList = PokemonSampleData.pokemonListSampleData().toMutableStateList(),
                    ),
                onEvent = { },
            )
        }

        composeTestRule.onNodeWithTag(NO_SEARCH_RESULT_FOUND_TAG).assertIsDisplayed()
    }
}
