package com.example.pokedexapp.ui.pokemon_detail_screen.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.pokedexapp.domain.sample_data.PokemonSampleData

@Composable
fun PokemonName(pokemonId: String, pokemonName: String, modifier: Modifier = Modifier) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append("#$pokemonId ")
            }
            withStyle(style = SpanStyle()) {
                append(pokemonName)
            }
        },
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp),
        modifier = modifier,
    )
}

@Preview
@Composable
private fun PokemonNamePreview() {
    Surface {
        PokemonName(
            pokemonId = PokemonSampleData.singlePokemonDetailSampleData().id,
            pokemonName = PokemonSampleData.singlePokemonDetailSampleData().name
        )
    }
}