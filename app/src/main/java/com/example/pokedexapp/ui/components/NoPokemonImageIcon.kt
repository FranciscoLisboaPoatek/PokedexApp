package com.example.pokedexapp.ui.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.pokedexapp.R

@Composable
fun NoPokemonImageIcon(
    tint: Color,
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(id = R.drawable.baseline_no_photography_24),
        contentDescription = null,
        tint = tint,
        modifier = modifier,
    )
}
