package com.example.pokedexapp.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.domain.models.PokemonTypes
import com.example.pokedexapp.ui.theme.PokedexAppTheme

@Composable
fun PokemonTypeIcon(pokemonType: PokemonTypes,textPaddingValues: PaddingValues, textStyle: TextStyle, modifier: Modifier = Modifier) {
    Surface(
        color = pokemonType.color,
        shape = CircleShape,
        modifier = modifier
    ) {
        Text(
            text = pokemonType.name,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(textPaddingValues),
            style = textStyle,
        )
    }
}

@Preview
@Composable
fun PokemonTypeIconPreview() {
    PokedexAppTheme{
        PokemonTypeIcon(
            PokemonTypes.POISON,
            PaddingValues(horizontal = 5.dp),
            MaterialTheme.typography.titleLarge,
            modifier = Modifier.wrapContentSize()
        )
    }
}