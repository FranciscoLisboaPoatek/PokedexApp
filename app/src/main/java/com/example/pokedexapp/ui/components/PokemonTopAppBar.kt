package com.example.pokedexapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.R
import com.example.pokedexapp.ui.components.PokemonTopAppBarTestTags.SHOW_RANDOM_POKEMON_NOTIFICATION_BUTTON_TAG
import com.example.pokedexapp.ui.theme.TopBarBlueColor

object PokemonTopAppBarTestTags {
    const val SEARCH_TEXT_FIELD_TAG = "SEARCH_TEXT_FIELD"
    const val OPEN_SEARCH_BUTTON_TAG = "OPEN_SEARCH_BUTTON"
    const val CLOSE_SEARCH_BUTTON_TAG = "CLOSE_SEARCH_BUTTON"
    const val SHOW_RANDOM_POKEMON_NOTIFICATION_BUTTON_TAG =
        "SHOW_RANDOM_POKEMON_NOTIFICATION_BUTTON"
}

@Composable
fun PokemonTopAppBar(
    areActionsEnabled: Boolean,
    onSendNotificationClick: () -> Unit,
) {
    LogoPokemonTopAppBar(
        areActionsEnabled = areActionsEnabled,
        onSendNotificationClick = onSendNotificationClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LogoPokemonTopAppBar(
    areActionsEnabled: Boolean,
    onSendNotificationClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            PokemonLogo(modifier = Modifier.height(60.dp))
        },
        actions = {
            TopAppBarDailyNotificationButton(
                enabled = areActionsEnabled,
                onClick = {
                    onSendNotificationClick()
                },
            )
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = TopBarBlueColor,
            ),
    )
}

@Composable
private fun PokemonLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.pokemon_logo),
        contentDescription = null,
        alignment = Alignment.Center,
        modifier = modifier,
    )
}

@Composable
private fun TopAppBarIconButton(
    icon: ImageVector,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    IconButton(onClick = onClick, enabled = enabled, modifier = modifier) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (enabled) Color.White else Color.LightGray,
        )
    }
}

@Composable
private fun TopAppBarDailyNotificationButton(
    enabled: Boolean,
    onClick: () -> Unit,
) {
    TopAppBarIconButton(
        icon = ImageVector.vectorResource(id = R.drawable.baseline_notifications_active_24),
        enabled = enabled,
        onClick = onClick,
        contentDescription = stringResource(R.string.show_random_pokemon_notification),
        modifier = Modifier.testTag(SHOW_RANDOM_POKEMON_NOTIFICATION_BUTTON_TAG),
    )
}

@Preview
@Composable
fun PokemonTopAppBarPreview() {
    PokemonTopAppBar(
        areActionsEnabled = true,
        onSendNotificationClick = { },
    )
}
