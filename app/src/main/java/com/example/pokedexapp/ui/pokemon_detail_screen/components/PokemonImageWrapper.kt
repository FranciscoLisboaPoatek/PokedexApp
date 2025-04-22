package com.example.pokedexapp.ui.pokemon_detail_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedexapp.ui.components.NoPokemonImageIcon

@Composable
fun PokemonImageWrapper(
    image: String?,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        if (image != null) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            NoPokemonImageIcon(
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxSize(0.5f),
            )
        }
    }
}

@Preview
@Composable
private fun PokemonImageWrapperPreview() {
    Surface {
        PokemonImageWrapper(image = "", modifier = Modifier.size(200.dp))
    }
}

@Preview
@Composable
private fun PokemonImageWrapperNoImagePreview() {
    Surface {
        PokemonImageWrapper(image = null, modifier = Modifier.size(200.dp))
    }
}
