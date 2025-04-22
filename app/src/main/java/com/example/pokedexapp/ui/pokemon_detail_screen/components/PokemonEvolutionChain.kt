package com.example.pokedexapp.ui.pokemon_detail_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.R
import com.example.pokedexapp.data.utils.treatName
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.ui.utils.EEVEE_POKEDEX_ID_STRING

@Composable
fun PokemonEvolutionChain(
    modifier: Modifier = Modifier,
    evolutionChain: PokemonEvolutionChainModel,
    currentPokemonDetailId: String? = null,
    onClickPokemon: (String) -> Unit,
) {
    evolutionChain.basePokemon?.let {
        if (
            it.id == EEVEE_POKEDEX_ID_STRING
        ) {
            Eeveelutions(
                modifier = modifier,
                evolutionChain = evolutionChain,
                currentPokemonDetailId = currentPokemonDetailId,
                onClickPokemon = onClickPokemon,
            )
        } else {
            PokemonColumn(
                modifier = modifier,
                evolutionChain = evolutionChain,
                currentPokemonDetailId = currentPokemonDetailId,
                onClickPokemon = onClickPokemon,
            )
        }
    }
}

@Composable
private fun Eeveelutions(
    modifier: Modifier = Modifier,
    evolutionChain: PokemonEvolutionChainModel,
    currentPokemonDetailId: String? = null,
    onClickPokemon: (String) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        evolutionChain.basePokemon?.let { eevee ->
            PokemonEvolution(
                modifier = Modifier.weight(1f),
                pokemonEvolutionId = eevee.id,
                pokemonEvolutionName = eevee.name,
                spriteUrl = eevee.spriteUrl,
                currentPokemonDetailId = currentPokemonDetailId,
                isFirstStage = true,
            ) {
                onClickPokemon(eevee.id)
            }

            Column(
                modifier = Modifier.weight(1f),
            ) {
                eevee.evolutions?.forEach { eeveelution ->
                    PokemonEvolution(
                        pokemonEvolutionId = eeveelution.id,
                        pokemonEvolutionName = eeveelution.name,
                        spriteUrl = eeveelution.spriteUrl,
                        currentPokemonDetailId = currentPokemonDetailId,
                        isVertical = false,
                    ) {
                        onClickPokemon(eeveelution.id)
                    }
                }
            }
        }
    }
}

@Composable
private fun PokemonColumn(
    modifier: Modifier = Modifier,
    evolutionChain: PokemonEvolutionChainModel,
    currentPokemonDetailId: String? = null,
    onClickPokemon: (String) -> Unit,
) {
    Column(modifier) {
        evolutionChain.basePokemon?.let { firstStage ->
            PokemonEvolution(
                pokemonEvolutionId = firstStage.id,
                pokemonEvolutionName = firstStage.name,
                spriteUrl = firstStage.spriteUrl,
                currentPokemonDetailId = currentPokemonDetailId,
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
                            pokemonEvolutionId = secondStage.id,
                            pokemonEvolutionName = secondStage.name,
                            spriteUrl = secondStage.spriteUrl,
                            currentPokemonDetailId = currentPokemonDetailId,
                        ) {
                            onClickPokemon(secondStage.id)
                        }

                        Row {
                            secondStage.evolutions?.forEach { thirdStage ->
                                PokemonEvolution(
                                    modifier = Modifier.weight(1f),
                                    pokemonEvolutionId = thirdStage.id,
                                    pokemonEvolutionName = thirdStage.name,
                                    spriteUrl = thirdStage.spriteUrl,
                                    currentPokemonDetailId = currentPokemonDetailId,
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
    pokemonEvolutionId: String,
    pokemonEvolutionName: String,
    spriteUrl: String?,
    currentPokemonDetailId: String? = null,
    isFirstStage: Boolean = false,
    isVertical: Boolean = true,
    onClick: () -> Unit,
) {
    if (isVertical) {
        Column(
            modifier = modifier,
        ) {
            Spacer(Modifier.height(8.dp))

            if (!isFirstStage) {
                EvolvesToIcon(
                    modifier =
                        Modifier
                            .size(48.dp)
                            .align(Alignment.CenterHorizontally)
                            .rotate(90f),
                )
            }

            PokemonWithName(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                pokemonEvolutionId = pokemonEvolutionId,
                pokemonEvolutionName = pokemonEvolutionName,
                spriteUrl = spriteUrl,
                currentPokemonDetailId = currentPokemonDetailId,
                onClick = onClick,
            )
        }
    } else {
        Row(
            modifier = modifier,
        ) {
            if (!isFirstStage) {
                EvolvesToIcon(
                    modifier =
                        Modifier
                            .size(48.dp)
                            .align(Alignment.CenterVertically),
                )
            }

            PokemonWithName(
                modifier = Modifier.align(Alignment.CenterVertically),
                pokemonEvolutionId = pokemonEvolutionId,
                pokemonEvolutionName = pokemonEvolutionName,
                spriteUrl = spriteUrl,
                onClick = onClick,
            )
        }
    }
}

@Composable
private fun EvolvesToIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Gray,
    contentDescription: String? = null,
) {
    Icon(
        painter = painterResource(R.drawable.ic_baseline_arrow_right_alt_24),
        contentDescription = contentDescription,
        modifier =
        modifier,
        tint = tint,
    )
}

@Composable
private fun PokemonWithName(
    modifier: Modifier = Modifier,
    pokemonEvolutionId: String,
    pokemonEvolutionName: String,
    spriteUrl: String?,
    currentPokemonDetailId: String? = null,
    onClick: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .clip(RoundedCornerShape(16.dp))
                .clickable(currentPokemonDetailId != pokemonEvolutionId) { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PokemonImageWrapper(
            modifier =
                Modifier
                    .sizeIn(maxHeight = 120.dp, maxWidth = 120.dp)
                    .aspectRatio(1f),
            image = spriteUrl,
        )

        Text(
            text = pokemonEvolutionName.treatName(),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
