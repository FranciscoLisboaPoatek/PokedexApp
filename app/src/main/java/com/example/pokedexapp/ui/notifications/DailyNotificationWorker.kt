package com.example.pokedexapp.ui.notifications

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.pokedexapp.domain.models.DailyPokemonWidgetModel
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.domain.use_cases.DailyPokemonWidgetUseCase
import com.example.pokedexapp.domain.use_cases.PokemonDetailUseCase
import com.example.pokedexapp.domain.use_cases.RandomPokemonUseCase
import com.example.pokedexapp.domain.utils.Response
import com.example.pokedexapp.ui.widgets.daily_pokemon.DailyPokemonWidget
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val POKEMON_ID_OUTPUT_KEY = "POKEMON_ID"
const val POKEMON_IMAGE_URL_OUTPUT_KEY = "POKEMON_IMAGE_URL"
const val POKEMON_PRIMARY_TYPE_NAME_OUTPUT_KEY = "POKEMON_PRIMARY_TYPE_NAME"
const val MAX_DAILY_NOTIFICATION_WORKER_REQUESTS = 3

@HiltWorker
class DailyNotificationWorker
@AssistedInject
constructor(
    @Assisted
    private val appContext: Context,
    @Assisted
    private val params: WorkerParameters,
    private val enqueueWorker: EnqueueWorker,
    private val randomPokemonUseCase: RandomPokemonUseCase,
    private val pokemonDetailUseCase: PokemonDetailUseCase,
    private val pokemonWidgetUseCase: DailyPokemonWidgetUseCase,
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val notificationPokemonResponse = randomPokemonUseCase.getRandomPokemonMinimalInfo()

        enqueueWorker.enqueue(context = appContext)

        when (notificationPokemonResponse) {
            is Response.Error -> {
                return Result.failure()
            }

            is Response.Success -> {
                var pokemonDetails = PokemonSampleData.dailyPokemonWidgetDataSample()
                withContext(Dispatchers.IO) {
                    var foundPokemonWithImage = false

                    var tries = 0

                    do {
                        val pokemonRequest =
                            pokemonDetailUseCase.getPokemonById(notificationPokemonResponse.data.id)

                        tries++

                        when (pokemonRequest) {
                            is Response.Error -> {}
                            is Response.Success -> {
                                if (pokemonRequest.data.frontDefaultSprite.spriteUrl != null) {
                                    foundPokemonWithImage = true
                                    pokemonDetails =
                                        DailyPokemonWidgetModel(
                                            id = pokemonRequest.data.id,
                                            imageUrl = pokemonRequest.data.frontDefaultSprite.spriteUrl,
                                            primaryType = pokemonRequest.data.primaryType,
                                        )
                                }
                            }
                        }
                    } while (!foundPokemonWithImage && tries < MAX_DAILY_NOTIFICATION_WORKER_REQUESTS)

                    pokemonWidgetUseCase.saveDailyPokemon(
                        DailyPokemonWidgetModel(
                            id = pokemonDetails.id,
                            imageUrl = pokemonDetails.imageUrl,
                            primaryType = pokemonDetails.primaryType,
                        ),
                    )

                    DailyPokemonWidget().updateAll(context = appContext)
                }
                DailyPokemonNotification(appContext).showNotification(
                    pokemonId = notificationPokemonResponse.data.id,
                    pokemonName = notificationPokemonResponse.data.name,
                )

                val outputData =
                    Data.Builder().putAll(
                        mapOf(
                            POKEMON_ID_OUTPUT_KEY to pokemonDetails.id,
                            POKEMON_IMAGE_URL_OUTPUT_KEY to pokemonDetails.imageUrl,
                            POKEMON_PRIMARY_TYPE_NAME_OUTPUT_KEY to pokemonDetails.primaryType.name,
                        ),
                    ).build()

                return Result.success(outputData)
            }
        }
    }
}
