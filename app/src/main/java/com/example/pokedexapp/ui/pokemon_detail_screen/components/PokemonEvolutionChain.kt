package com.example.pokedexapp.ui.pokemon_detail_screen.components

import android.util.Log
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.data.utils.treatName
import com.example.pokedexapp.domain.models.ChainModel
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.ui.utils.EEVEE_POKEDEX_ID_STRING

@Composable
fun PokemonEvolutionChain(
    modifier: Modifier = Modifier,
    evolutionChain: PokemonEvolutionChainModel,
    currentPokemonDetailId: String? = null,
    onClickPokemon: (String) -> Unit,
) {
    evolutionChain.evolutions?.let {
        if (
            it.firstOrNull()?.id == EEVEE_POKEDEX_ID_STRING
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
                evolutions = it,
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
        evolutionChain.evolutions?.firstOrNull()?.let { eevee ->
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
    evolutions: List<ChainModel>,
    currentPokemonDetailId: String? = null,
    onClickPokemon: (String) -> Unit,
) {
    Column(modifier) {
        evolutions.firstOrNull()?.let { firstStage ->
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
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(48.dp)
                            .align(Alignment.CenterVertically)
                            .rotate(-90f),
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
private fun PokemonWithName(
    modifier: Modifier = Modifier,
    pokemonEvolutionId: String,
    pokemonEvolutionName: String,
    spriteUrl: String?,
    currentPokemonDetailId: String? = null,
    onClick: () -> Unit,
) {
    Log.w("batata", "pokemonEvolutionId: $pokemonEvolutionId | currentPokemonDetailId: $currentPokemonDetailId")
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
            imageSize = null,
        )

        Text(
            text = pokemonEvolutionName.treatName(),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
