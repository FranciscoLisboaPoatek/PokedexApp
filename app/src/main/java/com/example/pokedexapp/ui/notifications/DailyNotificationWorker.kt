package com.example.pokedexapp.ui.notifications

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.pokedexapp.domain.use_cases.RandomPokemonUseCase
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
            return try {
                val notificationPokemon = randomPokemonUseCase.getRandomPokemonMinimalInfo()

                DailyPokemonNotification(appContext).showNotification(
                    pokemonId = notificationPokemon.id,
                    pokemonName = notificationPokemon.name,
                )
                return Result.success()
            } catch (ex: Exception) {
                Result.failure()
            }
        }
    }
