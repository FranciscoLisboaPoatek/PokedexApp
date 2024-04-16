package com.example.pokedexapp.ui.pokemon_detail_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.R

@Composable
fun PokemonHeightWeight(
    pokemonHeight: Float,
    pokemonWeight: Float,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        PokemonMeasurement(
            content = stringResource(id = R.string.weight_in_kg, pokemonWeight.toString()),
            icon = painterResource(id = R.drawable.weight_icon),
            color = Color.Gray,
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End
        )
        PokemonMeasurement(
            content = stringResource(id = R.string.height_in_m, pokemonHeight.toString()),
            icon = painterResource(id = R.drawable.height_icon),
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun PokemonMeasurement(
    content: String,
    icon: Painter,
    color: Color,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
) {
    Row(
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = content,
            color = color,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
@Preview
@Composable
private fun PokemonHeightWeightPreview() {
    PokemonHeightWeight(
        pokemonWeight = 10f,
        pokemonHeight = 10f
    )
}