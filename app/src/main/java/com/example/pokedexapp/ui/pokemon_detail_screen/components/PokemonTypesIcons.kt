package com.example.pokedexapp.ui.pokemon_detail_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.domain.models.PokemonTypes
import com.example.pokedexapp.ui.components.PokemonTypeIcon

@Composable
fun PokemonTypesIcons(
    primaryType: PokemonTypes,
    secondaryType: PokemonTypes?,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        modifier = modifier,
    ) {
        PokemonTypeIcon(
            pokemonType = primaryType,
            textPaddingValues = PaddingValues(vertical = 5.dp),
            textStyle = MaterialTheme.typography.titleMedium,
            modifier = Modifier.width(100.dp),
        )
        if (secondaryType != null) {
            PokemonTypeIcon(
                pokemonType = secondaryType,
                textPaddingValues = PaddingValues(vertical = 5.dp),
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.width(100.dp),
            )
        }
    }
}

@Preview
@Composable
private fun PokemonTypesIconsOneTypePreview() {
    PokemonTypesIcons(primaryType = PokemonTypes.GHOST, secondaryType = null)
}

@Preview
@Composable
private fun PokemonTypesIconsTwoTypePreview() {
    PokemonTypesIcons(primaryType = PokemonTypes.GHOST, secondaryType = PokemonTypes.POISON)
}
