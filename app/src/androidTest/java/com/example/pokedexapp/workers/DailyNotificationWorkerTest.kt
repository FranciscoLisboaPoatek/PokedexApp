package com.example.pokedexapp.workers

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import androidx.work.testing.TestListenableWorkerBuilder
import com.example.pokedexapp.domain.models.PokemonMinimalInfo
import com.example.pokedexapp.domain.models.PokemonSprite
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.domain.use_cases.DailyPokemonWidgetUseCase
import com.example.pokedexapp.domain.use_cases.PokemonDetailUseCase
import com.example.pokedexapp.domain.use_cases.RandomPokemonUseCase
import com.example.pokedexapp.domain.utils.Response
import com.example.pokedexapp.ui.notifications.DailyNotificationWorker
import com.example.pokedexapp.ui.notifications.MAX_DAILY_NOTIFICATION_WORKER_REQUESTS
import com.example.pokedexapp.ui.notifications.POKEMON_IMAGE_URL_OUTPUT_KEY
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DailyNotificationWorkerTest {
    private val randomPokemonUseCase: RandomPokemonUseCase = mockk(relaxed = true)
    private val pokemonDetailUseCase: PokemonDetailUseCase = mockk()
    private val pokemonWidgetUseCase: DailyPokemonWidgetUseCase = mockk(relaxed = true)

    private var context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun when_error_on_all_requests_should_return_default_pokemon() =
        runTest {
            val worker =
                TestListenableWorkerBuilder<DailyNotificationWorker>(context)
                    .setWorkerFactory(
                        ExampleWorkerFactory(
                            randomPokemonUseCase,
                            pokemonDetailUseCase,
                            pokemonWidgetUseCase,
                        ),
                    )
                    .build()

            coEvery {
                pokemonDetailUseCase.getPokemonById(any())
            } returns
                Response.Success(
                    PokemonSampleData.singlePokemonDetailSampleData().copy(
                        frontDefaultSprite =
                            PokemonSprite.FrontDefaultSprite(
                                null,
                            ),
                    ),
                )

            coEvery {
                randomPokemonUseCase.getRandomPokemonMinimalInfo()
            } returns
                Response.Success(
                    PokemonMinimalInfo(
                        id = "9999",
                        name = "Any",
                    ),
                )

            val result = worker.doWork()

            val pokemonDetailImageUrl = result.outputData.keyValueMap[POKEMON_IMAGE_URL_OUTPUT_KEY]

            coVerify(exactly = MAX_DAILY_NOTIFICATION_WORKER_REQUESTS) {
                pokemonDetailUseCase.getPokemonById(any())
            }

            assertEquals(PokemonSampleData.pokemonWidgetDataSample().imageUrl, pokemonDetailImageUrl)
        }

    @Test
    fun when_success_on_all_requests_should_make_one_request_only() =
        runTest {
            val worker =
                TestListenableWorkerBuilder<DailyNotificationWorker>(context)
                    .setWorkerFactory(
                        ExampleWorkerFactory(
                            randomPokemonUseCase,
                            pokemonDetailUseCase,
                            pokemonWidgetUseCase,
                        ),
                    )
                    .build()

            coEvery {
                pokemonDetailUseCase.getPokemonById(any())
            } returnsMany
                listOf(
                    Response.Success(
                        PokemonSampleData.singlePokemonDetailSampleData().copy(
                            id = "1",
                            frontDefaultSprite =
                                PokemonSprite.FrontDefaultSprite(
                                    "url/1",
                                ),
                        ),
                    ),
                    Response.Success(
                        PokemonSampleData.singlePokemonDetailSampleData().copy(
                            id = "2",
                            frontDefaultSprite =
                                PokemonSprite.FrontDefaultSprite(
                                    "url/2",
                                ),
                        ),
                    ),
                    Response.Success(
                        PokemonSampleData.singlePokemonDetailSampleData().copy(
                            id = "3",
                            frontDefaultSprite =
                                PokemonSprite.FrontDefaultSprite(
                                    "url/3",
                                ),
                        ),
                    ),
                )

            coEvery {
                randomPokemonUseCase.getRandomPokemonMinimalInfo()
            } returns
                Response.Success(
                    PokemonMinimalInfo(
                        id = "9999",
                        name = "Any",
                    ),
                )

            val result = worker.doWork()

            val pokemonDetailImageUrl = result.outputData.keyValueMap[POKEMON_IMAGE_URL_OUTPUT_KEY]

            coVerify(exactly = 1) {
                pokemonDetailUseCase.getPokemonById(any())
            }

            assertEquals("url/1", pokemonDetailImageUrl)
        }

    @Test
    fun when_error_on_request_should_return_the_next_successful_one() =
        runTest {
            val worker =
                TestListenableWorkerBuilder<DailyNotificationWorker>(context)
                    .setWorkerFactory(
                        ExampleWorkerFactory(
                            randomPokemonUseCase,
                            pokemonDetailUseCase,
                            pokemonWidgetUseCase,
                        ),
                    )
                    .build()

            coEvery {
                pokemonDetailUseCase.getPokemonById(any())
            } returnsMany
                listOf(
                    Response.Success(
                        PokemonSampleData.singlePokemonDetailSampleData().copy(
                            id = "1",
                            frontDefaultSprite =
                                PokemonSprite.FrontDefaultSprite(
                                    null,
                                ),
                        ),
                    ),
                    Response.Success(
                        PokemonSampleData.singlePokemonDetailSampleData().copy(
                            id = "2",
                            frontDefaultSprite =
                                PokemonSprite.FrontDefaultSprite(
                                    "url/2",
                                ),
                        ),
                    ),
                    Response.Success(
                        PokemonSampleData.singlePokemonDetailSampleData().copy(
                            id = "3",
                            frontDefaultSprite =
                                PokemonSprite.FrontDefaultSprite(
                                    "url/3",
                                ),
                        ),
                    ),
                )

            coEvery {
                randomPokemonUseCase.getRandomPokemonMinimalInfo()
            } returns
                Response.Success(
                    PokemonMinimalInfo(
                        id = "9999",
                        name = "Any",
                    ),
                )

            val result = worker.doWork()

            val pokemonDetailImageUrl = result.outputData.keyValueMap[POKEMON_IMAGE_URL_OUTPUT_KEY]

            coVerify(exactly = 2) {
                pokemonDetailUseCase.getPokemonById(any())
            }

            assertEquals("url/2", pokemonDetailImageUrl)
        }
}

class ExampleWorkerFactory(
    private val randomPokemonUseCase: RandomPokemonUseCase,
    private val pokemonDetailUseCase: PokemonDetailUseCase,
    private val pokemonWidgetUseCase: DailyPokemonWidgetUseCase,
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker {
        return DailyNotificationWorker(
            appContext,
            workerParameters,
            randomPokemonUseCase,
            pokemonDetailUseCase,
            pokemonWidgetUseCase,
        )
    }
}
