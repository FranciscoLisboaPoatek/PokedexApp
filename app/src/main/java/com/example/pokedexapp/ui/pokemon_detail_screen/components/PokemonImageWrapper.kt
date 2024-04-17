package com.example.pokedexapp.ui.pokemon_detail_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedexapp.ui.components.NoPokemonImageIcon

@Composable
fun PokemonImageWrapper(
    image: String?,
    imageSize: Dp,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        if (image != null) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier.size(imageSize),
            )
        } else {
            NoPokemonImageIcon(
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(imageSize / 2),
            )
        }
    }
}

@Preview
@Composable
private fun PokemonImageWrapperPreview() {
    Surface {
        PokemonImageWrapper(image = "", imageSize = 200.dp)
    }
}

@Preview
@Composable
private fun PokemonImageWrapperNoImagePreview() {
    Surface {
        PokemonImageWrapper(image = null, imageSize = 200.dp)
    }
}
