package com.example.pokedexapp.ui.widgets

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.size
import com.example.pokedexapp.MainActivity
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.models.PokemonTypes
import com.example.pokedexapp.ui.navigation.Screen
import com.example.pokedexapp.ui.utils.INTENT_EXTRA_DEEPLINK_KEY

@Composable
fun PokemonWidgetContent(
    modifier: GlanceModifier,
    pokemonId: String,
    pokemonImage: Bitmap?,
    pokemonPrimaryType: PokemonTypes
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier.fillMaxSize()
                .background(pokemonPrimaryType.color)
                .clickable(
                    actionStartActivity<MainActivity>(
                        parameters = actionParametersOf(
                            ActionParameters.Key<String>(INTENT_EXTRA_DEEPLINK_KEY) to Screen.PokemonDetailScreen.makeDeeplink(
                                pokemonId = pokemonId
                            )
                        )
                    )
                ),
    ) {
        pokemonImage?.let {
            Image(
                modifier =
                    GlanceModifier
                        .fillMaxSize(),
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
