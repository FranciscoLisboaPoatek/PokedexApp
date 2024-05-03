package com.example.pokedexapp.ui.pokemon_list_screen

import android.Manifest
import android.app.Instrumentation
import android.app.NotificationManager
import android.app.UiAutomation
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.ExperimentalTestApi
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
import androidx.core.content.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.test.platform.app.InstrumentationRegistry
import com.example.pokedexapp.TestActivity
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.ui.components.PokemonTopAppBarTestTags
import com.example.pokedexapp.ui.components.PokemonTopAppBarTestTags.OPEN_SEARCH_BUTTON_TAG
import com.example.pokedexapp.ui.components.PokemonTopAppBarTestTags.SHOW_RANDOM_POKEMON_NOTIFICATION_BUTTON_TAG
import com.example.pokedexapp.ui.pokemon_list_screen.PokemonListScreenTestTags.POKEMON_LIST_TAG
import com.example.pokedexapp.ui.pokemon_list_screen.components.NoSearchResultsFoundTestTag.NO_SEARCH_RESULT_FOUND_TAG
import com.example.pokedexapp.ui.utils.DAILY_NOTIFICATION_ID_KEY
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PokemonListScreenViewModelIntegrationTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<TestActivity>()

    companion object {
        @JvmStatic
        @BeforeClass
        fun grantPhonePermission() {
            val instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
            val uiAutomation: UiAutomation = instrumentation.uiAutomation
            if (Build.VERSION.SDK_INT >= 28) {
                uiAutomation.adoptShellPermissionIdentity()

                uiAutomation.grantRuntimePermission(
                    instrumentation.targetContext.packageName,
                    Manifest.permission.POST_NOTIFICATIONS,
                )

                uiAutomation.dropShellPermissionIdentity()
            }
        }
    }

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
        composeTestRule.onNodeWithTag(OPEN_SEARCH_BUTTON_TAG)
            .performClick()
        composeTestRule.onNodeWithTag(PokemonTopAppBarTestTags.SEARCH_TEXT_FIELD_TAG)
            .performTextInput("Nat")
        composeTestRule.waitUntilExactlyOneExists(matcher = hasTextExactly("Natu"))
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchPokemon_noResultsFound() {
        composeTestRule.onNodeWithTag(OPEN_SEARCH_BUTTON_TAG).performClick()
        composeTestRule.onNodeWithTag(PokemonTopAppBarTestTags.SEARCH_TEXT_FIELD_TAG)
            .performTextInput("Potato")
        composeTestRule.waitUntilExactlyOneExists(matcher = hasTestTag(NO_SEARCH_RESULT_FOUND_TAG))
    }

    @Test
    fun show_random_pokemon_notification() {
        composeTestRule.onNodeWithTag(SHOW_RANDOM_POKEMON_NOTIFICATION_BUTTON_TAG)
            .performClick()
        val notification =
            composeTestRule.activity.getSystemService<NotificationManager>()?.activeNotifications?.first()
        assertEquals(DAILY_NOTIFICATION_ID_KEY, notification?.id)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun is_showing_correct_list() {
        composeTestRule.onNodeWithTag(OPEN_SEARCH_BUTTON_TAG)
            .performClick()
        composeTestRule.onNodeWithText("Natu").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(PokemonTopAppBarTestTags.SEARCH_TEXT_FIELD_TAG)
            .performTextInput("Nat")
        composeTestRule.waitUntilExactlyOneExists(matcher = hasTextExactly("Natu"))
        composeTestRule.onNodeWithTag(PokemonTopAppBarTestTags.SEARCH_TEXT_FIELD_TAG)
            .performTextClearance()
        composeTestRule.waitUntilExactlyOneExists(matcher = hasTextExactly(PokemonSampleData.pokemonListSampleData()[0].name))
        composeTestRule.onNodeWithText("Natu").assertIsNotDisplayed()
    }

    @Test
    fun append_to_default_list() {
        composeTestRule.onNodeWithTag(POKEMON_LIST_TAG).performScrollToIndex(10)
        composeTestRule.onNodeWithTag(POKEMON_LIST_TAG)
            .performScrollToIndex(PokemonSampleData.pokemonListSampleData().lastIndex)
        composeTestRule.onNodeWithText(
            PokemonSampleData.pokemonListSampleData().last().name,
        ).assertExists()
    }

    @Test
    fun append_to_search_list() {
        composeTestRule.onNodeWithTag(OPEN_SEARCH_BUTTON_TAG).performClick()
        composeTestRule.onNodeWithTag(PokemonTopAppBarTestTags.SEARCH_TEXT_FIELD_TAG)
            .performTextInput("a")
        composeTestRule.onNodeWithText("Natu").assertDoesNotExist()
        composeTestRule.onNodeWithTag(POKEMON_LIST_TAG).performScrollToIndex(10)
        composeTestRule.onNodeWithTag(POKEMON_LIST_TAG)
            .performScrollToNode(hasTextExactly("Natu")).assertExists()
    }
}
