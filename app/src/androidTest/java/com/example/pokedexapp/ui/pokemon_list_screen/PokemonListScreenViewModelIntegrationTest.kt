package com.example.pokedexapp.ui.pokemon_list_screen

import androidx.compose.runtime.getValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pokedexapp.TestActivity
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.ui.pokemon_list_screen.PokemonListScreenTestTags.POKEMON_LIST_SEARCH_BAR_TEST_TAG
import com.example.pokedexapp.ui.pokemon_list_screen.PokemonListScreenTestTags.POKEMON_LIST_TAG
import com.example.pokedexapp.ui.pokemon_list_screen.components.NoSearchResultsFoundTestTag.NO_SEARCH_RESULT_FOUND_TAG
import com.example.pokedexapp.ui.utils.LIST_ITEMS_PER_PAGE
import com.example.pokedexapp.ui.utils.REMAINING_LIST_ITEMS_TO_LOAD_MORE
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PokemonListScreenViewModelIntegrationTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<TestActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.setContent {
            val viewModel = hiltViewModel<PokemonListViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            PokemonListScreen(
                state = state,
                onEvent = {
                    viewModel.onEvent(it)
                },
            )
        }
    }

    @Test
    fun is_showing_pokemonList() {
        composeTestRule.onNodeWithText(PokemonSampleData.pokemonListSampleData()[0].name)
            .assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchPokemon() {
        composeTestRule.onNodeWithTag(POKEMON_LIST_SEARCH_BAR_TEST_TAG)
            .performTextInput("Nat")
        composeTestRule.waitUntilExactlyOneExists(matcher = hasTextExactly("Natu"))
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchPokemon_noResultsFound() {
        composeTestRule.onNodeWithTag(POKEMON_LIST_SEARCH_BAR_TEST_TAG)
            .performTextInput("Potato")
        composeTestRule.waitUntilExactlyOneExists(matcher = hasTestTag(NO_SEARCH_RESULT_FOUND_TAG))
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun is_showing_correct_list() {
        // is showing default list
        composeTestRule.onNodeWithText(PokemonSampleData.pokemonListSampleData()[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithText("Natu").assertIsNotDisplayed()

        // click on search button, but it is still showing default list
        composeTestRule.onNodeWithTag(POKEMON_LIST_SEARCH_BAR_TEST_TAG).assertIsDisplayed()
            .performClick()
        composeTestRule.onNodeWithText(PokemonSampleData.pokemonListSampleData()[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithText("Natu").assertIsNotDisplayed()

        // is showing search list
        composeTestRule.onNodeWithTag(POKEMON_LIST_SEARCH_BAR_TEST_TAG)
            .performTextInput("Nat")
        composeTestRule.waitUntilExactlyOneExists(matcher = hasTextExactly("Natu"))
        composeTestRule.onNodeWithText(PokemonSampleData.pokemonListSampleData()[0].name).assertIsNotDisplayed()

        // is showing default list again
        composeTestRule.onNodeWithTag(POKEMON_LIST_SEARCH_BAR_TEST_TAG)
            .performTextClearance()
        composeTestRule.waitUntilExactlyOneExists(matcher = hasTextExactly(PokemonSampleData.pokemonListSampleData()[0].name))
        composeTestRule.onNodeWithText("Natu").assertIsNotDisplayed()
    }

    @Test
    fun append_to_default_list() {
        composeTestRule.onNodeWithTag(POKEMON_LIST_TAG).performScrollToIndex(LIST_ITEMS_PER_PAGE - REMAINING_LIST_ITEMS_TO_LOAD_MORE)
        composeTestRule.onNodeWithTag(POKEMON_LIST_TAG)
            .performScrollToIndex(PokemonSampleData.pokemonListSampleData().lastIndex)
        composeTestRule.onNodeWithText(
            PokemonSampleData.pokemonListSampleData().last().name,
        ).assertExists()
    }

    @Test
    fun append_to_search_list() {
        composeTestRule.onNodeWithTag(POKEMON_LIST_SEARCH_BAR_TEST_TAG)
            .performTextInput("a")
        composeTestRule.onNodeWithText("Natu").assertDoesNotExist()
        composeTestRule.onNodeWithTag(POKEMON_LIST_TAG).performScrollToIndex(LIST_ITEMS_PER_PAGE - REMAINING_LIST_ITEMS_TO_LOAD_MORE)
        composeTestRule.onNodeWithTag(POKEMON_LIST_TAG)
            .performScrollToNode(hasTextExactly("Natu")).assertExists()
    }
}
