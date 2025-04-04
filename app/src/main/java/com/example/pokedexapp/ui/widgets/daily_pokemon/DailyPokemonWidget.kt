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
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.models.PokemonTypes
import com.example.pokedexapp.domain.sample_data.PokemonSampleData


class DailyPokemonWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val dailyPokemonState = DailyPokemonWidgetStateManager.state
        val state = dailyPokemonState ?: PokemonSampleData.pokemonWidgetDataSample()

        provideContent {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically,
                modifier = GlanceModifier.fillMaxSize()
                    .background(state.primaryType.color)
            ) {
                var imageBitmap by remember {
                    mutableStateOf<Bitmap?>(null)
                }

                LaunchedEffect(state.imageUrl) {
                    imageBitmap = getImageBitmap(context, state.imageUrl)
                }

                imageBitmap?.let {
                    Image(
                        modifier = GlanceModifier
                            .fillMaxWidth()
                            .defaultWeight(),
                        provider = ImageProvider(it),
                        contentDescription = null
                    )
                } ?: run {
                    Image(
                        modifier = GlanceModifier.fillMaxSize(),
                        provider = ImageProvider(R.drawable.baseline_no_photography_24),
                        contentDescription = null
                    )
                }
//
//                    Text(
//                        modifier = GlanceModifier.padding(
//                            start = 8.dp,
//                            end = 8.dp,
//                            top = 8.dp,
//                            bottom = 32.dp
//                        ),
//                        style = TextStyle(
//                            fontWeight = FontWeight.Medium,
//                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
//                            textAlign = TextAlign.Center
//                        ),
//
//                        text = state.name,
//                    )
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