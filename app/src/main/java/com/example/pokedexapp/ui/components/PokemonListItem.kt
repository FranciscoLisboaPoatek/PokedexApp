package com.example.pokedexapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedexapp.domain.models.PokemonListItemModel
import com.example.pokedexapp.domain.sample_data.PokemonSampleData

@Composable
fun TwoColorStrokeBox(
    firstColor: Color,
    secondColor: Color,
    strokeWidthDp: Dp,
    modifier: Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        modifier =
            modifier
                .clickable { onClick() }
                .drawBehind {
                    drawPath(
                        path =
                            Path().apply {
                                moveTo(0f, 0f)
                                lineTo(size.width, 0f)
                                lineTo(0f, size.height)
                                close()
                            },
                        color = firstColor,
                    )
                    drawPath(
                        path =
                            Path().apply {
                                moveTo(size.width, size.height)
                                lineTo(0f, size.height)
                                lineTo(size.width, 0f)
                                close()
                            },
                        color = secondColor,
                    )
                },
    ) {
        Box(modifier = Modifier.padding(strokeWidthDp - 0.4.dp)) {
            content()
        }
    }
}

@Preview
@Composable
fun PokemonListItemPreview() {
    PokemonListItem(
        pokemon = PokemonSampleData.singlePokemonListItemSampleData(),
        strokeWidthDp = 10.dp,
        onClick = { },
        modifier = Modifier.size(186.dp, 210.dp),
    )
}

@Composable
fun PokemonListItem(
    pokemon: PokemonListItemModel,
    strokeWidthDp: Dp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TwoColorStrokeBox(
        pokemon.primaryType.color,
        pokemon.secondaryType?.color ?: pokemon.primaryType.color,
        strokeWidthDp,
        onClick = { onClick() },
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
    ) {
        Column(
            modifier =
                Modifier.background(
                    Brush.verticalGradient(
                        colorStops =
                            arrayOf(
                                0f to pokemon.primaryType.color,
                                1f to MaterialTheme.colorScheme.background,
                            ),
                    ),
                    shape = RoundedCornerShape(8.dp),
                ),
        ) {
            if (pokemon.spriteUrl != null) {
                PokemonImage(
                    image = pokemon.spriteUrl,
                    modifier =
                        Modifier
                            .padding(top = 5.dp, start = 5.dp, end = 5.dp)
                            .weight(3f)
                            .fillMaxWidth(),
                )
            } else {
                NoPokemonImageIcon(
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier =
                        Modifier
                            .padding(top = 40.dp, bottom = 20.dp, start = 40.dp, end = 40.dp)
                            .weight(3f)
                            .fillMaxWidth(),
                )
            }

            PokemonName(
                name = pokemon.name,
                modifier =
                    Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f)
                        .fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun PokemonImage(
    image: String?,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = image,
        contentDescription = null,
        modifier = modifier,
    )
}

@Composable
private fun PokemonName(
    name: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = name,
        textAlign = TextAlign.Center,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
    )
}
