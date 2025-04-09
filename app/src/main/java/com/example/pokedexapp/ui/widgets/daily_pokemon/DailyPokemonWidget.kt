package com.example.pokedexapp.ui.widgets.daily_pokemon

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.pokedexapp.domain.use_cases.DailyPokemonWidgetUseCase
import com.example.pokedexapp.ui.widgets.PokemonWidgetContent
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

class DailyPokemonWidget : GlanceAppWidget() {
    override suspend fun provideGlance(
        context: Context,
        id: GlanceId,
    ) {
        val dailyPokemonWidgetUseCase: DailyPokemonWidgetUseCase =
            DailyPokemonWidgetUseCaseEntrypoint.get(context)

        val dailyPokemonState = dailyPokemonWidgetUseCase.getDailyPokemon()

        provideContent {
            var imageBitmap by remember {
                mutableStateOf<Bitmap?>(null)
            }

            LaunchedEffect(dailyPokemonState.imageUrl) {
                imageBitmap = getImageBitmap(context, dailyPokemonState.imageUrl)
            }

            PokemonWidgetContent(
                GlanceModifier,
                dailyPokemonState.id,
                imageBitmap,
                dailyPokemonState.primaryType
            )
        }
    }
}

private suspend fun getImageBitmap(
    context: Context,
    url: String?,
): Bitmap? {
    val request =
        ImageRequest.Builder(context).data(url).apply {
            memoryCachePolicy(CachePolicy.DISABLED)
            diskCachePolicy(CachePolicy.DISABLED)
        }.build()

    return when (val result = context.imageLoader.execute(request)) {
        is ErrorResult -> null
        is SuccessResult -> result.drawable.toBitmapOrNull()
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface DailyPokemonWidgetUseCaseEntrypoint {
    fun getDailyPokemonWidgetUseCase(): DailyPokemonWidgetUseCase

    companion object {
        fun get(context: Context): DailyPokemonWidgetUseCase =
            EntryPoints.get(context, DailyPokemonWidgetUseCaseEntrypoint::class.java).getDailyPokemonWidgetUseCase()
    }
}
