package com.example.pokedexapp.ui.pokemon_list_screen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.domain.use_cases.PokemonListUseCase
import com.example.pokedexapp.domain.use_cases.RandomPokemonUseCase
import com.example.pokedexapp.ui.firebase.FirebaseAnalyticsLogger
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

    private val pokemonListUseCaseMock = mockk<PokemonListUseCase>()

    private val randomPokemonUseCaseMock = mockk<RandomPokemonUseCase>()

    private val firebaseAnalyticsLoggerMock = mockk<FirebaseAnalyticsLogger>()

    private lateinit var viewModel: PokemonListViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = PokemonListViewModel(
            pokemonListUseCase = pokemonListUseCaseMock,
            randomPokemonUseCase = randomPokemonUseCaseMock,
            firebaseAnalyticsLogger = firebaseAnalyticsLoggerMock
        )
        dispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun loadInitialData() {

        coEvery {
            pokemonListUseCaseMock.getPokemonList(
                any(),
                any()
            )
        } returns listOf()

        coEvery { pokemonListUseCaseMock.insertAllPokemon() } returns Unit

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
                any()
            )
        } throws Exception()

        viewModel.loadInitialData()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(false, viewModel.state.value.couldLoadInitialData)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun changeIsSearchMode() {
        viewModel.changeIsSearchMode()
        assertEquals(true, viewModel.state.value.isSearchMode)
        viewModel.changeIsSearchMode()
        assertEquals(false, viewModel.state.value.isSearchMode)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPokemonList() = runTest {

        val stateList = mutableListOf<PokemonListScreenUiState>()

        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect {
                stateList.add(it)
            }
        }

        coEvery {
            pokemonListUseCaseMock.getPokemonList(
                any(),
                any()
            )
        } returns PokemonSampleData.pokemonListSampleData()

        viewModel.getPokemonList()
        dispatcher.scheduler.advanceUntilIdle()

        job.cancel()

        stateList.forEach {
            println(it)
        }
        assertEquals(
            PokemonSampleData.pokemonListSampleData(),
            viewModel.state.value.pokemonList.toList()
        )

        assertEquals(true, viewModel.state.value.isDefaultList)

        assertEquals(listOf(false, true, false), stateList.map { it.isLoadingAppend })
    }

    @Test
    fun getPokemonList_listEnded() {

        assertEquals(false, viewModel.state.value.defaultListEnded)

        coEvery {
            pokemonListUseCaseMock.getPokemonList(
                any(),
                any()
            )
        } returns listOf()

        viewModel.getPokemonList()

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.state.value.defaultListEnded)

    }

    @Test
    fun getPokemonList_error() {
        assertEquals(false, viewModel.state.value.errorAppendingDefaultList)

        coEvery {
            pokemonListUseCaseMock.getPokemonList(
                any(),
                any()
            )
        } throws Exception()

        try {
            viewModel.getPokemonList()

            dispatcher.scheduler.advanceUntilIdle()

        } catch (ex: Exception) {
            assertEquals(true, viewModel.state.value.errorAppendingDefaultList)
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun searchPokemonListByName() = runTest {

        val stateList = mutableListOf<PokemonListScreenUiState>()


        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect {
                stateList.add(it)
            }
        }

        coEvery { pokemonListUseCaseMock.getPokemonSearchList("Pikachu", 0) } returns listOf(
            PokemonSampleData.singlePokemonListItemSampleData()
        )

        coEvery { firebaseAnalyticsLoggerMock.logFirebaseEvent(any(), any()) } just Runs

        viewModel.changeSearchText("Pikachu")
        dispatcher.scheduler.advanceUntilIdle()

        job.cancel()

        assertEquals(
            viewModel.state.value.pokemonList[0],
            PokemonSampleData.singlePokemonListItemSampleData()
        )

        assertEquals(listOf(false, false, true, false), stateList.map { it.isLoading })

        assertEquals(false, viewModel.state.value.isDefaultList)

    }

    @Test
    fun searchPokemonListByName_noResultsFound() = runTest {
        coEvery { pokemonListUseCaseMock.getPokemonSearchList(any(), any()) } returns emptyList()

        coEvery { firebaseAnalyticsLoggerMock.logFirebaseEvent(any(), any()) } just Runs


        viewModel.changeSearchText("Pikachu")
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.state.value.showNoSearchResultsFound)
    }

    @Test
    fun searchPokemonListByName_error() {
        coEvery {
            pokemonListUseCaseMock.getPokemonSearchList(
                any(),
                any()
            )
        } throws Exception()

        coEvery { firebaseAnalyticsLoggerMock.logFirebaseEvent(any(), any()) } just Runs

        viewModel.changeSearchText("Pikachu")
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.state.value.errorSearching)

    }

    @Test
    fun appendSearchList() {
        coEvery { pokemonListUseCaseMock.getPokemonSearchList("Pikachu", any()) } returns listOf(
            PokemonSampleData.singlePokemonListItemSampleData()
        )

        coEvery { firebaseAnalyticsLoggerMock.logFirebaseEvent(any(), any()) } just Runs

        viewModel.changeSearchText("Pikachu")
        dispatcher.scheduler.advanceUntilIdle()
        assertEquals(1, viewModel.state.value.pokemonList.size)

        viewModel.appendSearchList()
        dispatcher.scheduler.advanceUntilIdle()
        assertEquals(2, viewModel.state.value.pokemonList.size)
        assertEquals(false, viewModel.state.value.isDefaultList)
    }

    @Test
    fun appendSearchList_error() {
        coEvery {
            pokemonListUseCaseMock.getPokemonSearchList(
                any(),
                any()
            )
        } throws Exception()

        coEvery { firebaseAnalyticsLoggerMock.logFirebaseEvent(any(), any()) } just Runs

        viewModel.appendSearchList()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.state.value.errorAppendingSearchList)
    }

}

