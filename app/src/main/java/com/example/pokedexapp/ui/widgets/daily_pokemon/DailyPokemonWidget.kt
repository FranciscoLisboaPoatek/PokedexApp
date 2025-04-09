package com.example.pokedexapp.ui.widgets.daily_pokemon

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.size
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.use_cases.DailyPokemonWidgetUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

class DailyPokemonWidget : GlanceAppWidget() {
    override suspend fun provideGlance(
        context: Context,
        id: GlanceId,
    ) {
        val dailyPokemonWidgetUseCase: DailyPokemonWidgetUseCase = DailyPokemonWidgetUseCaseEntrypoint.get(context)

        val dailyPokemonState = dailyPokemonWidgetUseCase.getDailyPokemon()

        provideContent {
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                    GlanceModifier.fillMaxSize()
                        .background(dailyPokemonState.primaryType.color),
            ) {
                var imageBitmap by remember {
                    mutableStateOf<Bitmap?>(null)
                }

                LaunchedEffect(dailyPokemonState.imageUrl) {
                    imageBitmap = getImageBitmap(context, dailyPokemonState.imageUrl)
                }

                imageBitmap?.let {
                    Image(
                        modifier =
                            GlanceModifier
                                .fillMaxWidth(),
                        provider = ImageProvider(it),
                        contentDescription = null,
                    )
                } ?: run {
                    Image(
                        modifier = GlanceModifier.size(48.dp),
                        provider = ImageProvider(R.drawable.baseline_no_photography_24),
                        contentDescription = null,
                    )
                }
            }
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
