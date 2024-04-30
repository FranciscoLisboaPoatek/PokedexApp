package com.example.pokedexapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pokedexapp.R
import com.example.pokedexapp.ui.components.PokeballLoadingAnimationTestTags.POKEBALL_LOADING_ANIMATION_TAG

object PokeballLoadingAnimationTestTags {
    const val POKEBALL_LOADING_ANIMATION_TAG = "POKEBALL_LOADING_ANIMATION"
}

@Composable
fun PokeballLoadingAnimation(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.pokeball_animation))
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.testTag(POKEBALL_LOADING_ANIMATION_TAG),
    ) {
        Surface(
            shape = CircleShape,
            shadowElevation = 5.dp,
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier =
                    Modifier
                        .size(50.dp),
            )
        }
    }
}
