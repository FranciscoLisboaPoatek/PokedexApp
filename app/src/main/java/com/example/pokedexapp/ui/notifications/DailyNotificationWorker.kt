package com.example.pokedexapp.ui.notifications

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.pokedexapp.domain.use_cases.RandomPokemonUseCase
import com.example.pokedexapp.domain.utils.Response
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DailyNotificationWorker
    @AssistedInject
    constructor(
        @Assisted
        private val appContext: Context,
        @Assisted
        private val params: WorkerParameters,
        private val randomPokemonUseCase: RandomPokemonUseCase,
    ) : CoroutineWorker(appContext, params) {
        override suspend fun doWork(): Result {
            val notificationPokemonResponse = randomPokemonUseCase.getRandomPokemonMinimalInfo()

            when (notificationPokemonResponse) {
                is Response.Error -> {
                    return Result.failure()
                }

                is Response.Success -> {
                    DailyPokemonNotification(appContext).showNotification(
                        pokemonId = notificationPokemonResponse.data.id,
                        pokemonName = notificationPokemonResponse.data.name,
                    )
                    return Result.success()
                }
            }
        }
    }
