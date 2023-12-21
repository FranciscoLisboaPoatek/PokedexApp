package com.example.pokedexapp.ui.pokemon_detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.models.PokemonTypes
import com.example.pokedexapp.ui.components.PokemonTypeIcon

@Composable
fun PokemonDetailScreen() {
    Scaffold {
        PokemonDetailScreenContent(
            pokemon = PokemonModel(
                1, "Bulbasaur", PokemonTypes.GRASS, PokemonTypes.POISON,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
            ), modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun PokemonDetailScreenContent(pokemon: PokemonModel, modifier: Modifier = Modifier) {
    val pokemonImageSize = 150.dp
    val pokemonImageTopPadding = 100.dp
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(
                    Brush.linearGradient(
                        colorStops = arrayOf(
                            0f to pokemon.primaryType.color,
                            1f to (pokemon.secondaryType?.color ?: Color.White)
                        )
                    )
                )
        )
        {
            PokemonInformationSheet(
                pokemon = pokemon,
                modifier = Modifier.padding(
                    top = pokemonImageTopPadding + pokemonImageSize/2 ,
                    start = 30.dp,
                    end = 30.dp
                )
            )
            PokemonImage(null, pokemonImageSize, modifier = Modifier.padding(top = pokemonImageTopPadding))
        }
    }
}

@Composable
private fun PokemonImage(image: Any?, imageSize: Dp, modifier: Modifier = Modifier) {
    Surface(
        color = Color.Transparent,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(imageSize)
                    .background(Color.Green)
            )
        }
    }
}

@Composable
fun PokemonInformationSheet(pokemon: PokemonModel, modifier: Modifier = Modifier) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier

    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.height(100.dp))

            PokemonName(
                pokemonId = pokemon.id,
                pokemonName = pokemon.name,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            PokemonTypesIcons(
                primaryType = pokemon.primaryType,
                secondaryType = pokemon.secondaryType,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

        }
    }

}

@Composable
private fun PokemonName(pokemonId: Int, pokemonName: String, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black.copy(alpha = 0.5f))) {
                    append("#$pokemonId")
                }
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append(" $pokemonName")
                }
            },
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun PokemonTypesIcons(
    primaryType: PokemonTypes,
    secondaryType: PokemonTypes?,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            PokemonTypeIcon(primaryType, MaterialTheme.typography.titleMedium)
            if (secondaryType != null) {
                Spacer(modifier = Modifier.width(8.dp))
                PokemonTypeIcon(secondaryType, MaterialTheme.typography.titleMedium)
            }
        }
    }

}

@Preview
@Composable
fun PokemonDetailScreenPreview() {
    PokemonDetailScreen()
}