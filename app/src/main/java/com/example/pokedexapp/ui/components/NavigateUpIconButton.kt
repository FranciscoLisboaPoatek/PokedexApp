package com.example.pokedexapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.pokedexapp.R

@Composable
fun NavigateUpIconButton(
    navigateUp: () -> Unit,
    color: Color,
) {
    IconButton(onClick = { navigateUp() }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.navigate_up),
            tint = color,
        )
    }
}
