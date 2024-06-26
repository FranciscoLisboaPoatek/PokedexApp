package com.example.pokedexapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokedexapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailTopAppBar(
    isShinySprite: Boolean,
    navigateUp: () -> Unit,
    rotateSprite: () -> Unit,
    changeShinySprite: () -> Unit,
    openSharePokemonToReceiverDialog: () -> Unit,
) {
    TopAppBar(
        title = @Composable {},
        navigationIcon = { NavigateUpIconButton(navigateUp) },
        actions = {
            SharePokemonToReceiverIcon(openSharePokemonToReceiverDialog)
            RotateIconButton(rotateSprite)
            ShinyIconButton(isShinySprite, changeShinySprite)
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
            ),
    )
}

@Composable
private fun NavigateUpIconButton(navigateUp: () -> Unit) {
    IconButton(onClick = { navigateUp() }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.navigate_up),
            tint = Color.Black,
        )
    }
}

@Composable
private fun ShinyIconButton(
    isShinySprite: Boolean,
    changeShinySprite: () -> Unit,
) {
    IconButton(
        onClick = { changeShinySprite() },
    ) {
        Icon(
            painter =
                if (isShinySprite) {
                    painterResource(id = R.drawable.baseline_star_24)
                } else {
                    painterResource(
                        id = R.drawable.baseline_star_border_24,
                    )
                },
            contentDescription = stringResource(R.string.shiny_icon_button),
            tint = if (isShinySprite) Color.Red else Color.Black,
        )
    }
}

@Composable
private fun RotateIconButton(rotateSprite: () -> Unit) {
    IconButton(
        onClick = { rotateSprite() },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_360_24),
            contentDescription = stringResource(R.string.rotate_sprite_icon_button),
            tint = Color.Black,
        )
    }
}

@Composable
private fun SharePokemonToReceiverIcon(openSharePokemonToReceiverDialog: () -> Unit) {
    IconButton(
        onClick = { openSharePokemonToReceiverDialog() },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_share_24),
            contentDescription = stringResource(R.string.share_pokemon_to_a_receiver),
            tint = Color.Black,
        )
    }
}

@Preview
@Composable
private fun PokemonDetailTopAppBarPreview() {
    PokemonDetailTopAppBar(
        isShinySprite = false,
        navigateUp = {},
        rotateSprite = {},
        changeShinySprite = {},
        openSharePokemonToReceiverDialog = {},
    )
}

@Preview
@Composable
private fun PokemonDetailTopAppBarShinyPreview() {
    PokemonDetailTopAppBar(
        isShinySprite = true,
        navigateUp = {},
        rotateSprite = {},
        changeShinySprite = {},
        openSharePokemonToReceiverDialog = {},
    )
}
