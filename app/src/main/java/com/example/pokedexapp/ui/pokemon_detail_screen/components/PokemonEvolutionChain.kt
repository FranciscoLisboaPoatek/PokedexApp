package com.example.pokedexapp.ui.pokemon_detail_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.data.utils.treatName
import com.example.pokedexapp.domain.models.ChainModel
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel

@Composable
fun PokemonEvolutionChain(
    modifier: Modifier = Modifier,
    evolutionChain: PokemonEvolutionChainModel,
    onClickPokemon: (String) -> Unit,
) {
    evolutionChain.evolutions?.let {
        PokemonColumn(
            modifier = modifier,
            evolutions = it,
            onClickPokemon = onClickPokemon,
        )
    }
}

@Composable
private fun PokemonColumn(
    modifier: Modifier = Modifier,
    evolutions: List<ChainModel>,
    onClickPokemon: (String) -> Unit,
) {
    Column(modifier) {
        evolutions.firstOrNull()?.let { firstStage ->
            PokemonEvolution(
                pokemonName = firstStage.name,
                spriteUrl = firstStage.spriteUrl,
                isFirstStage = true,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                onClickPokemon(firstStage.id)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                firstStage.evolutions?.forEach { secondStage ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        PokemonEvolution(
                            modifier = Modifier,
                            pokemonName = secondStage.name,
                            spriteUrl = secondStage.spriteUrl,
                        ) {
                            onClickPokemon(secondStage.id)
                        }

                        Row {
                            secondStage.evolutions?.forEach { thirdStage ->
                                PokemonEvolution(
                                    modifier = Modifier.weight(1f),
                                    pokemonName = thirdStage.name,
                                    spriteUrl = thirdStage.spriteUrl,
                                ) {
                                    onClickPokemon(thirdStage.id)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PokemonEvolution(
    modifier: Modifier = Modifier,
    pokemonName: String,
    spriteUrl: String?,
    isFirstStage: Boolean = false,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        if (!isFirstStage) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier =
                    Modifier
                        .size(48.dp)
                        .align(Alignment.CenterHorizontally),
            )
        }

        Column(
            modifier =
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .align(Alignment.CenterHorizontally)
                    .clickable { onClick() },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PokemonImageWrapper(
                modifier =
                    Modifier
                        .sizeIn(maxHeight = 120.dp, maxWidth = 120.dp)
                        .aspectRatio(1f),
                image = spriteUrl,
                imageSize = null,
            )

            Text(
                text = pokemonName.treatName(),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
