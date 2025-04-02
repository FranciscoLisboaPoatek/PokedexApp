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
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.models.PokemonTypes


class DailyPokemonWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val state = DailyPokemonWidgetStateManager.state

        provideContent {
            state?.let { state ->
                var imageBitmap by remember {
                    mutableStateOf<Bitmap?>(null)
                }

                LaunchedEffect(state.imageUrl) {
                    imageBitmap = getImageBitmap(context, state.imageUrl)
                }

                imageBitmap?.let {
                    Image(
                        provider = ImageProvider(it),
                        contentDescription = null
                    )
                } ?: run {
                    Image(
                        provider = ImageProvider(R.drawable.baseline_no_photography_24),
                        contentDescription = null
                    )
                }
            }
        }
    }

    private suspend fun getImageBitmap(context: Context, url: String?): Bitmap? {
        val request = ImageRequest.Builder(context).data(url).apply {
            memoryCachePolicy(CachePolicy.DISABLED)
            diskCachePolicy(CachePolicy.DISABLED)
        }.build()

        return when (val result = context.imageLoader.execute(request)) {
            is ErrorResult -> null
            is SuccessResult -> result.drawable.toBitmapOrNull()
        }
    }
}

object DailyPokemonWidgetStateManager {
    var state: DailyPokemonWidgetState? = null
}

data class DailyPokemonWidgetState(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val primaryType: PokemonTypes,
    val secondaryType: PokemonTypes?
)