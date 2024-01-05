package com.example.pokedexapp.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokedexapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailTopAppBar(
    isShinySprite: Boolean,
    changeShinySprite: () -> Unit
) {
    TopAppBar(
        title = @Composable {},
        actions = { ShinyIconButton(isShinySprite, changeShinySprite) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun ShinyIconButton(
    isShinySprite: Boolean,
    changeShinySprite: () -> Unit
) {
    IconButton(
        onClick = { changeShinySprite() },

        ) {
        Icon(
            painter = if(isShinySprite) painterResource(id = R.drawable.baseline_star_24) else painterResource(id = R.drawable.baseline_star_border_24),
            contentDescription = null,
            tint = if(isShinySprite) Color.Red else Color.Black
            )
    }
}

@Preview
@Composable
private fun PokemonDetailTopAppBarPreview() {
    PokemonDetailTopAppBar(false){}
}

@Preview
@Composable
private fun PokemonDetailTopAppBarShinyPreview() {
    PokemonDetailTopAppBar(true){}
}