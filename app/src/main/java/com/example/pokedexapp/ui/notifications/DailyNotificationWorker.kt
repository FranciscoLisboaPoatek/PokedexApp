package com.example.pokedexapp.ui.notifications

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.pokedexapp.domain.models.DailyPokemonWidgetModel
import com.example.pokedexapp.domain.use_cases.DailyPokemonWidgetUseCase
import com.example.pokedexapp.domain.use_cases.PokemonDetailUseCase
import com.example.pokedexapp.domain.use_cases.RandomPokemonUseCase
import com.example.pokedexapp.domain.utils.Response
import com.example.pokedexapp.ui.widgets.daily_pokemon.DailyPokemonWidget
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class DailyNotificationWorker
@AssistedInject
constructor(
    @Assisted
    private val appContext: Context,
    @Assisted
    private val params: WorkerParameters,
    private val randomPokemonUseCase: RandomPokemonUseCase,
    private val pokemonDetailUseCase: PokemonDetailUseCase,
    private val pokemonWidgetUseCase: DailyPokemonWidgetUseCase
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val notificationPokemonResponse = randomPokemonUseCase.getRandomPokemonMinimalInfo()

        when (notificationPokemonResponse) {
            is Response.Error -> {
                return Result.failure()
            }

            is Response.Success -> {
                withContext(Dispatchers.IO) {
                    val pokemonDetails =
                        pokemonDetailUseCase.getPokemonById(notificationPokemonResponse.data.id)

                    if (pokemonDetails is Response.Success) {

                        pokemonWidgetUseCase.saveDailyPokemon(DailyPokemonWidgetModel(
                            id = pokemonDetails.data.id,
                            imageUrl = pokemonDetails.data.frontDefaultSprite.spriteUrl,
                            primaryType = pokemonDetails.data.primaryType,
                        ))

                        DailyPokemonWidget().updateAll(appContext)
                    }

                    DailyPokemonWidget().updateAll(context = appContext)
                }

                DailyPokemonNotification(appContext).showNotification(
                    pokemonId = notificationPokemonResponse.data.id,
                    pokemonName = notificationPokemonResponse.data.name,
                )
                return Result.success()
            }
        }
    }
}
