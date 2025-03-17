package com.example.pokedexapp.ui.pokemon_detail_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.models.PokemonDetailModel
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.ui.components.PokeballLoadingAnimation
import com.example.pokedexapp.ui.pokemon_detail_screen.PokemonDetailScreenOnEvent

@Composable
fun PokemonInformationSheet(
    isLoading: Boolean,
    isError: Boolean,
    pokemon: PokemonDetailModel?,
    evolutionChain: PokemonEvolutionChainModel,
    contentTopSpace: Dp,
    onEvent: (PokemonDetailScreenOnEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val informationSheetModifier = Modifier.padding(vertical = contentTopSpace)
    Surface(
        color = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 10.dp,
        modifier = modifier,
    ) {
        if (isLoading) {
            LoadingPokemonInformationSheet(
                modifier = informationSheetModifier,
            )
        } else if (isError) {
            ErrorPokemonInformationSheet(
                modifier = informationSheetModifier,
            )
        } else {
            if (pokemon != null) {
                SuccessPokemonInformationSheet(
                    pokemon = pokemon,
                    evolutionChain = evolutionChain,
                    onEvent = onEvent,
                    modifier = informationSheetModifier,
                )
            }
        }
    }
}

@Composable
private fun LoadingPokemonInformationSheet(modifier: Modifier = Modifier) {
    PokeballLoadingAnimation(modifier = modifier)
}

@Composable
private fun ErrorPokemonInformationSheet(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.error,
        )
        Text(
            text = stringResource(R.string.pokemon_information_sheet_error),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error,
        )
    }
}

@Composable
private fun SuccessPokemonInformationSheet(
    pokemon: PokemonDetailModel,
    evolutionChain: PokemonEvolutionChainModel,
    onEvent: (PokemonDetailScreenOnEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        PokemonName(
            pokemonId = pokemon.id,
            pokemonName = pokemon.name,
            modifier = Modifier.padding(horizontal = 8.dp),
        )

        Spacer(modifier = Modifier.height(24.dp))

        PokemonTypesIcons(
            primaryType = pokemon.primaryType,
            secondaryType = pokemon.secondaryType,
        )

        Spacer(modifier = Modifier.height(32.dp))

        PokemonHeightWeight(
            pokemonHeight = pokemon.height,
            pokemonWeight = pokemon.weight,
            modifier =
                Modifier
                    .padding(horizontal = 8.dp),
        )

        Spacer(modifier = Modifier.height(32.dp))

        PokemonBaseStatsGraph(
            pokemon = pokemon,
            modifier =
                Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
        )

        PokemonEvolutionChain(evolutionChain = evolutionChain)
    }
}

@Preview
@Composable
private fun LoadingPokemonInformationSheetPreview() {
    PokemonInformationSheet(
        isLoading = true,
        isError = false,
        pokemon = PokemonSampleData.singlePokemonDetailSampleData(),
        evolutionChain = PokemonEvolutionChainModel(),
        contentTopSpace = 20.dp,
        onEvent = { },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Preview
@Composable
private fun ErrorPokemonInformationSheetPreview() {
    PokemonInformationSheet(
        isLoading = false,
        isError = true,
        pokemon = PokemonSampleData.singlePokemonDetailSampleData(),
        evolutionChain = PokemonEvolutionChainModel(),
        contentTopSpace = 20.dp,
        onEvent = { },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Preview
@Composable
private fun SuccessPokemonInformationSheetPreview() {
    PokemonInformationSheet(
        isLoading = false,
        isError = false,
        pokemon = PokemonSampleData.singlePokemonDetailSampleData(),
        evolutionChain =
            PokemonEvolutionChainModel(
//                evolvesFromPokemonId = PokemonSampleData.singlePokemonDetailSampleData().id,
//                evolvesFromPokemonName = PokemonSampleData.singlePokemonDetailSampleData().name,
//                evolvesFromPokemonSpriteUrl = PokemonSampleData.singlePokemonDetailSampleData().frontDefaultSprite.spriteUrl,
            ),
        contentTopSpace = 20.dp,
        onEvent = { },
        modifier = Modifier.fillMaxWidth(),
    )
}
