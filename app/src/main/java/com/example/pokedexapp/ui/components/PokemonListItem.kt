package com.example.pokedexapp.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedexapp.domain.models.PokemonModel
import com.example.pokedexapp.domain.sample_data.PokemonSampleData


@Composable
fun TwoColorStrokeBox(
    firstColor: Color,
    secondColor: Color,
    strokeWidthDp: Dp,
    modifier: Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .drawBehind {
                val strokeWidthPx = strokeWidthDp.toPx()
                val offset = strokeWidthPx / 2
                //top line
                drawLine(
                    start = Offset(0f, offset),
                    end = Offset((size.width - strokeWidthPx) + 1, offset),
                    color = firstColor,
                    strokeWidth = strokeWidthPx
                )
                //start line
                drawLine(
                    start = Offset(offset, strokeWidthPx - 1),
                    end = Offset(offset, (size.height - strokeWidthPx) + 1),
                    color = firstColor,
                    strokeWidth = strokeWidthPx
                )

                //bottom line
                drawLine(
                    start = Offset(size.width, size.height - offset),
                    end = Offset(strokeWidthPx - 1, size.height - offset),
                    color = secondColor,
                    strokeWidth = strokeWidthPx
                )
                //end line
                drawLine(
                    start = Offset(size.width - offset, strokeWidthPx - 1),
                    end = Offset(size.width - offset, (size.height - strokeWidthPx) + 1),
                    color = secondColor,
                    strokeWidth = strokeWidthPx
                )

                //start-bottom corner
                drawPath(
                    path = Path().apply {
                        moveTo(0f, size.height - strokeWidthPx)
                        lineTo(0f, size.height)
                        lineTo(strokeWidthPx, size.height - strokeWidthPx)
                        close()
                    },
                    color = firstColor,
                )
                //top-end corner
                drawPath(
                    path = Path().apply {
                        moveTo(size.width - strokeWidthPx, 0f)
                        lineTo(size.width - strokeWidthPx, strokeWidthPx)
                        lineTo(size.width, 0f)
                        close()
                    },
                    color = firstColor,
                )
                //bottom-start corner
                drawPath(
                    path = Path().apply {
                        moveTo(strokeWidthPx, size.height)
                        lineTo(0f, size.height)
                        lineTo(strokeWidthPx, size.height - strokeWidthPx)
                        close()
                    },
                    color = secondColor,
                )
                //end-top corner
                drawPath(
                    path = Path().apply {
                        moveTo(size.width, strokeWidthPx)
                        lineTo(size.width - strokeWidthPx, strokeWidthPx)
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
        pokemon = PokemonSampleData.singlePokemonSampleData(),
        strokeWidthDp = 10.dp,
        onClick = { },
        modifier = Modifier.size(186.dp, 210.dp))
}

@Composable
fun PokemonListItem(pokemon: PokemonModel, strokeWidthDp: Dp, onClick: () -> Unit,modifier: Modifier = Modifier) {
    TwoColorStrokeBox(
        pokemon.primaryType.color,
        pokemon.secondaryType?.color ?: pokemon.primaryType.color,
        strokeWidthDp,
        onClick = { onClick() },
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        0f to pokemon.primaryType.color,
                        1f to Color.White
                    )
                )
            )
        ) {
            PokemonImage(
                image = pokemon.frontDefaultSprite.spriteUrl,
                modifier = Modifier
                    .padding(top = 5.dp, start = 5.dp, end = 5.dp)
                    .weight(3f)
                    .fillMaxWidth()
            )

            PokemonName(
                name = pokemon.name,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

        }
    }
}

@Composable
private fun PokemonImage(
    image: String?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = image,
        contentDescription = null,
        modifier = modifier

    )
}

@Composable
private fun PokemonName(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}