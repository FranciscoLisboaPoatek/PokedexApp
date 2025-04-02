package com.example.pokedexapp.ui.notifications

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.pokedexapp.domain.repository.PokemonRepository
import com.example.pokedexapp.domain.use_cases.RandomPokemonUseCase
import com.example.pokedexapp.domain.utils.Response
import com.example.pokedexapp.ui.widgets.daily_pokemon.DailyPokemonWidget
import com.example.pokedexapp.ui.widgets.daily_pokemon.DailyPokemonWidgetState
import com.example.pokedexapp.ui.widgets.daily_pokemon.DailyPokemonWidgetStateManager
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
    private val pokemonRepository: PokemonRepository
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
                        pokemonRepository.getPokemonDetailById(notificationPokemonResponse.data.id)

                    if (pokemonDetails is Response.Success) {

                        DailyPokemonWidgetStateManager.state = DailyPokemonWidgetState(
                            pokemonDetails.data.id,
                            pokemonDetails.data.name,
                            pokemonDetails.data.frontDefaultSprite.spriteUrl,
                            pokemonDetails.data.primaryType,
                            pokemonDetails.data.secondaryType,
                        )

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
