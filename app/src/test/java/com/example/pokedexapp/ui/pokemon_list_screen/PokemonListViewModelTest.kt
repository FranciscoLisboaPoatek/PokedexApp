package com.example.pokedexapp.ui.pokemon_list_screen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.domain.use_cases.PokemonListUseCase
import com.example.pokedexapp.domain.use_cases.RandomPokemonUseCase
import com.example.pokedexapp.domain.utils.Response
import com.example.pokedexapp.ui.analytics.AnalyticsLogger
import com.example.pokedexapp.ui.navigation.Navigator
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PokemonListViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    private val pokemonListUseCaseMock = mockk<PokemonListUseCase>(relaxed = true)

    private val randomPokemonUseCaseMock = mockk<RandomPokemonUseCase>()

    private val analyticsLoggerMock = mockk<AnalyticsLogger>()

    private val navigatorMock = mockk<Navigator>()

    private lateinit var viewModel: PokemonListViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel =
            PokemonListViewModel(
                pokemonListUseCase = pokemonListUseCaseMock,
                randomPokemonUseCase = randomPokemonUseCaseMock,
                analyticsLogger = analyticsLoggerMock,
                navigator = navigatorMock,
            )
        dispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun loadInitialData() {
        coEvery {
            pokemonListUseCaseMock.getPokemonList(
                any(),
                any(),
            )
        } returns Response.Success(listOf())

        coEvery { pokemonListUseCaseMock.insertAllPokemon() } returns Response.Success(Unit)

        viewModel.loadInitialData()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.state.value.couldLoadInitialData)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun loadInitialData_error() {
        coEvery {
            pokemonListUseCaseMock.getPokemonList(
                any(),
                any(),
            )
        } returns Response.Error(Exception())

        viewModel.loadInitialData()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(false, viewModel.state.value.couldLoadInitialData)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPokemonList() =
        runTest {
            val stateList = mutableListOf<PokemonListScreenUiState>()

            val job =
                launch(UnconfinedTestDispatcher(testScheduler)) {
                    viewModel.state.collect {
                        stateList.add(it)
                    }
                }

            coEvery {
                pokemonListUseCaseMock.getPokemonList(
                    any(),
                    any(),
                )
            } returns Response.Success(PokemonSampleData.pokemonListSampleData())

            viewModel.getPokemonList()
            dispatcher.scheduler.advanceUntilIdle()

            job.cancel()

            assertEquals(
                PokemonSampleData.pokemonListSampleData(),
                viewModel.state.value.pokemonList.toList(),
            )

            assertEquals(true, viewModel.state.value.isDefaultList)

            // #1 State starts with isLoading = false
            // #2 When enters coroutine isLoading = true
            // #3 When it finishes getting the list, isLoading = false
            assertEquals(listOf(false, true, false), stateList.map { it.isLoadingAppend })

            viewModel.getPokemonList()
            dispatcher.scheduler.advanceUntilIdle()

            assertEquals(
                PokemonSampleData.pokemonListSampleData()
                    .plus(PokemonSampleData.pokemonListSampleData()),
                viewModel.state.value.pokemonList.toList(),
            )
        }

    @Test
    fun getPokemonList_listEnded() {
        assertEquals(false, viewModel.state.value.defaultListEnded)

        coEvery {
            pokemonListUseCaseMock.getPokemonList(
                any(),
                any(),
            )
        } returns Response.Success(listOf())

        viewModel.getPokemonList()

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.state.value.defaultListEnded)
        assertEquals(false, viewModel.state.value.isLoadingAppend)
    }

    @Test
    fun getPokemonList_error() {
        assertEquals(false, viewModel.state.value.errorAppendingDefaultList)

        coEvery {
            pokemonListUseCaseMock.getPokemonList(
                any(),
                any(),
            )
        } returns Response.Error(Exception())

        viewModel.getPokemonList()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.state.value.errorAppendingDefaultList)
        assertEquals(false, viewModel.state.value.isLoadingAppend)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun searchPokemonListByName() =
        runTest {
            val stateList = mutableListOf<PokemonListScreenUiState>()

            val job =
                launch(UnconfinedTestDispatcher(testScheduler)) {
                    viewModel.state.collect {
                        stateList.add(it)
                    }
                }

            coEvery { pokemonListUseCaseMock.getPokemonSearchList("Bulbasaur", any(), any()) } returns
                Response.Success(listOf(PokemonSampleData.singlePokemonListItemSampleData()))

            coEvery { analyticsLoggerMock.logEvent(any(), any()) } just Runs

            viewModel.changeSearchText("Bulbasaur")
            dispatcher.scheduler.advanceUntilIdle()

            job.cancel()

            assertEquals(
                viewModel.state.value.pokemonList[0],
                PokemonSampleData.singlePokemonListItemSampleData(),
            )

            // #1 State starts with isLoading = false
            // #2 ChangeSearchText updates the searchText, but still isLoading = false
            // #3 When enters coroutine isLoading = true
            // #4 When it finishes getting the list, isLoading = false
            assertEquals(listOf(false, false, true, false), stateList.map { it.isLoading })

            assertEquals(false, viewModel.state.value.isDefaultList)
            assertEquals(false, viewModel.state.value.isLoading)
        }

    @Test
    fun searchPokemonListByName_noResultsFound() {
        coEvery { pokemonListUseCaseMock.getPokemonSearchList(any(), any(), any()) } returns Response.Success(emptyList())

        coEvery { analyticsLoggerMock.logEvent(any(), any()) } just Runs

        viewModel.changeSearchText("Pikachu")
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.state.value.showNoSearchResultsFound)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun searchPokemonListByName_error() {
        coEvery {
            pokemonListUseCaseMock.getPokemonSearchList(
                any(),
                any(),
                any(),
            )
        } returns Response.Error(Exception())

        coEvery { analyticsLoggerMock.logEvent(any(), any()) } just Runs

        viewModel.changeSearchText("Pikachu")
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.state.value.errorSearching)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun appendSearchList() =
        runTest {
            val stateList = mutableListOf<PokemonListScreenUiState>()

            coEvery { pokemonListUseCaseMock.getPokemonSearchList("Pikachu", any(), any()) } returns
                Response.Success(listOf(PokemonSampleData.singlePokemonListItemSampleData()))

            coEvery { analyticsLoggerMock.logEvent(any(), any()) } just Runs

            viewModel.changeSearchText("Pikachu")
            dispatcher.scheduler.advanceUntilIdle()

            assertEquals(1, viewModel.state.value.pokemonList.size)

            val job =
                launch(UnconfinedTestDispatcher(testScheduler)) {
                    viewModel.state.collect {
                        stateList.add(it)
                    }
                }

            viewModel.appendSearchList()
            dispatcher.scheduler.advanceUntilIdle()

            job.cancel()

            // #1 State starts with isLoading = false
            // #2 When enters coroutine isLoading = true
            // #3 When it finishes getting the list, isLoading = false
            assertEquals(listOf(false, true, false), stateList.map { it.isLoadingAppend })

            assertEquals(2, viewModel.state.value.pokemonList.size)
            assertEquals(false, viewModel.state.value.isDefaultList)
        }

    @Test
    fun appendSearchList_listEnded() {
        coEvery {
            pokemonListUseCaseMock.getPokemonSearchList(
                any(),
                any(),
                any(),
            )
        } returns Response.Success(emptyList())

        viewModel.appendSearchList()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.state.value.searchListEnded)
        assertEquals(false, viewModel.state.value.isLoadingAppend)
    }

    @Test
    fun appendSearchList_error() {
        coEvery {
            pokemonListUseCaseMock.getPokemonSearchList(
                any(),
                any(),
                any(),
            )
        } returns Response.Error(Exception())

        coEvery { analyticsLoggerMock.logEvent(any(), any()) } just Runs

        viewModel.appendSearchList()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.state.value.errorAppendingSearchList)
        assertEquals(false, viewModel.state.value.isLoadingAppend)
    }
}
