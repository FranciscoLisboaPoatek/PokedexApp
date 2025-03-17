package com.example.pokedexapp.ui.pokemon_detail_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.domain.models.ChainModel
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel

@Composable
fun PokemonEvolutionChain(
    modifier: Modifier = Modifier,
    evolutionChain: PokemonEvolutionChainModel,
) {
    evolutionChain.evolutions?.let {
        PokemonColumn2(
            modifier,
            it
        )
    }
}

@Composable
private fun PokemonColumn2(
    modifier: Modifier = Modifier,
    evolutions: List<ChainModel>
) {
    Column(modifier) {
        evolutions.firstOrNull()?.let { firstStage ->
            PokemonImageWrapper(
                firstStage.spriteUrl,
                120.dp,
                Modifier.align(Alignment.CenterHorizontally),
            )

            Row(
                Modifier.fillMaxWidth(),

                ) {
                firstStage.evolutions?.forEach { secondStage ->
                    Column(
                        Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PokemonEvolution(
                            Modifier,
                            "",
                            secondStage.spriteUrl
                        )
                        Row {
                            secondStage.evolutions?.forEach { thirdStage ->
                                PokemonEvolution(
                                    Modifier.weight(1f),
                                    //TODO
                                    "",
                                    thirdStage.spriteUrl
                                )
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

    ) {
    Column(
        modifier = modifier
    ) {
        Icon(
            Icons.Default.KeyboardArrowDown,
            null,
            Modifier
                .size(48.dp)
                .align(Alignment.CenterHorizontally),
        )
        PokemonImageWrapper(
            spriteUrl,
null,
            Modifier.sizeIn(maxHeight = 120.dp, maxWidth = 120.dp).aspectRatio(1f)
        )
    }

}
