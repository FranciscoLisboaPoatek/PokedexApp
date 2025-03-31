package com.example.pokedexapp.ui.pokemon_detail_screen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.domain.models.SpriteType
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.domain.use_cases.PokemonDetailUseCase
import com.example.pokedexapp.domain.use_cases.PokemonEvolutionChainUseCase
import com.example.pokedexapp.domain.use_cases.SharePokemonUseCase
import com.example.pokedexapp.domain.utils.Response
import com.example.pokedexapp.ui.navigation.Navigator
import com.example.pokedexapp.ui.utils.POKEMON_ID_KEY
import io.mockk.coEvery
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

class PokemonDetailViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    private val pokemonDetailUseCaseMock = mockk<PokemonDetailUseCase>()

    private val sharePokemonUseCaseMock = mockk<SharePokemonUseCase>()

    private val pokemonEvolutionChainUseCaseMock = mockk<PokemonEvolutionChainUseCase>()

    private val navigatorMock = mockk<Navigator>()

    private val savedStateHandleMock = SavedStateHandle()

    private lateinit var viewModel: PokemonDetailViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        coEvery {
            pokemonDetailUseCaseMock.getPokemonById("1")
        } returns Response.Success(PokemonSampleData.singlePokemonDetailSampleData())

        coEvery {
            pokemonEvolutionChainUseCaseMock.getPokemonChain("1")
        } returns Response.Success(PokemonEvolutionChainModel())

        savedStateHandleMock[POKEMON_ID_KEY] = "1"

        viewModel =
            PokemonDetailViewModel(
                savedStateHandle = savedStateHandleMock,
                pokemonDetailUseCase = pokemonDetailUseCaseMock,
                sharePokemonUseCase = sharePokemonUseCaseMock,
                pokemonEvolutionChainUseCase = pokemonEvolutionChainUseCaseMock,
                navigator = navigatorMock,
            )

        dispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun switchSharePokemonToReceiverDialog() {
        assertEquals(false, viewModel.state.value.isSharingPokemonToReceiver)
        viewModel.switchSharePokemonToReceiverDialog()
        assertEquals(true, viewModel.state.value.isSharingPokemonToReceiver)
        viewModel.switchSharePokemonToReceiverDialog()
        assertEquals(false, viewModel.state.value.isSharingPokemonToReceiver)
    }

    @Test
    fun updateReceiverToken() {
        val token = "1234"
        viewModel.updateReceiverToken(token)
        assertEquals(token, viewModel.state.value.receiverToken)
    }

    @Test
    fun rotatePokemonSprite() {
        val pokemon = PokemonSampleData.singlePokemonDetailSampleData()

        viewModel.rotatePokemonSprite(SpriteType.FRONT_DEFAULT)
        assertEquals(
            pokemon.backDefaultSprite.spriteType,
            viewModel.state.value.pokemonSprite?.spriteType,
        )

        viewModel.rotatePokemonSprite(SpriteType.BACK_DEFAULT)
        assertEquals(
            pokemon.frontDefaultSprite.spriteType,
            viewModel.state.value.pokemonSprite?.spriteType,
        )

        viewModel.rotatePokemonSprite(SpriteType.FRONT_SHINY_DEFAULT)
        assertEquals(
            pokemon.backShinySprite.spriteType,
            viewModel.state.value.pokemonSprite?.spriteType,
        )

        viewModel.rotatePokemonSprite(SpriteType.BACK_SHINY_DEFAULT)
        assertEquals(
            pokemon.frontShinySprite.spriteType,
            viewModel.state.value.pokemonSprite?.spriteType,
        )
    }

    @Test
    fun changeShinyPokemonSprite() {
        val pokemon = PokemonSampleData.singlePokemonDetailSampleData()

        viewModel.changeShinyPokemonSprite(SpriteType.FRONT_DEFAULT)
        assertEquals(
            pokemon.frontShinySprite.spriteType,
            viewModel.state.value.pokemonSprite?.spriteType,
        )

        viewModel.changeShinyPokemonSprite(SpriteType.BACK_DEFAULT)
        assertEquals(
            pokemon.backShinySprite.spriteType,
            viewModel.state.value.pokemonSprite?.spriteType,
        )

        viewModel.changeShinyPokemonSprite(SpriteType.FRONT_SHINY_DEFAULT)
        assertEquals(
            pokemon.frontDefaultSprite.spriteType,
            viewModel.state.value.pokemonSprite?.spriteType,
        )

        viewModel.changeShinyPokemonSprite(SpriteType.BACK_SHINY_DEFAULT)
        assertEquals(
            pokemon.backDefaultSprite.spriteType,
            viewModel.state.value.pokemonSprite?.spriteType,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updatePokemon() =
        runTest {
            val pokemon = PokemonSampleData.pokemonDetailListSampleData()[0]

            val stateList = mutableListOf<PokemonDetailScreenUiState>()

            val job =
                launch(UnconfinedTestDispatcher(testScheduler)) {
                    viewModel.state.collect {
                        stateList.add(it)
                    }
                }

            coEvery {
                pokemonDetailUseCaseMock.getPokemonById("2")
            } returns Response.Success(pokemon)

            coEvery {
                pokemonEvolutionChainUseCaseMock.getPokemonChain(any())
            } returns Response.Success(PokemonEvolutionChainModel())

            viewModel.updatePokemon("2")
            dispatcher.scheduler.advanceUntilIdle()

            job.cancel()

            assertEquals(pokemon, viewModel.state.value.pokemonDetailModel)
            assertEquals(listOf(false, true, false), stateList.map { it.isLoading })
        }

    @Test
    fun updatePokemon_error() {
        coEvery {
            pokemonDetailUseCaseMock.getPokemonById(any())
        } returns Response.Error(Exception())

        coEvery {
            pokemonEvolutionChainUseCaseMock.getPokemonChain(any())
        } returns Response.Success(PokemonEvolutionChainModel())

        viewModel.updatePokemon("2")
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.state.value.isError)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun sharePokemonToReceiver() {
        coEvery {
            sharePokemonUseCaseMock.sharePokemonTo(any())
        } returns Response.Success(Unit)

        assertEquals(false, viewModel.state.value.isErrorSharingPokemonToReceiver)
    }

    @Test
    fun sharePokemonToReceiver_error() {
        coEvery {
            sharePokemonUseCaseMock.sharePokemonTo(any())
        } returns Response.Error(Exception())

        viewModel.sharePokemonToReceiver()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.state.value.isErrorSharingPokemonToReceiver)
    }
}
