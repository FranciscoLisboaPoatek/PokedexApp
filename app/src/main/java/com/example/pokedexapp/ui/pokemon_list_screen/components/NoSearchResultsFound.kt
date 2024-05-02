package com.example.pokedexapp.ui.pokemon_list_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokedexapp.R

@Composable
fun NoSearchResultsFound(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.no_search_results_found),
            color = Color.Gray,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Preview
@Composable
private fun NoSearchResultsFoundPreview() {
    Surface {
        NoSearchResultsFound()
    }
}
