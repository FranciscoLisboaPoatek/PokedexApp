package com.example.pokedexapp.ui.pokemon_detail_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.sample_data.PokemonSampleData

@Composable
fun EvolvesFromPokemon(
    pokemonId: String,
    pokemonName: String,
    pokemonSpriteUrl: String?,
    navigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.evolves_from),
            style = MaterialTheme.typography.titleLarge,
        )
        PokemonImageWrapper(
            image = pokemonSpriteUrl,
            imageSize = 120.dp,
            modifier =
                Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .clickable { navigateToDetails(pokemonId) },
        )
        Text(text = pokemonName, style = MaterialTheme.typography.titleMedium)
    }
}

@Preview
@Composable
private fun EvolvesFromPokemonPreview() {
    Surface {
        EvolvesFromPokemon(
            pokemonId = PokemonSampleData.singlePokemonDetailSampleData().id,
            pokemonName = PokemonSampleData.singlePokemonDetailSampleData().name,
            pokemonSpriteUrl = PokemonSampleData.singlePokemonDetailSampleData().frontDefaultSprite.spriteUrl,
            navigateToDetails = { },
        )
    }
}
