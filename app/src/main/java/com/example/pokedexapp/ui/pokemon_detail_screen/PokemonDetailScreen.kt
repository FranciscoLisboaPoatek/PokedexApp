package com.example.pokedexapp.ui.pokemon_detail_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.domain.models.PokemonDetailModel
import com.example.pokedexapp.domain.models.PokemonSprite
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.ui.components.PokemonDetailTopAppBar
import com.example.pokedexapp.ui.pokemon_detail_screen.components.PokemonImageWrapper
import com.example.pokedexapp.ui.pokemon_detail_screen.components.PokemonInformationSheet
import com.example.pokedexapp.ui.pokemon_detail_screen.components.ChoosePokemonAsWidgetDialog
import com.example.pokedexapp.ui.pokemon_detail_screen.components.SharePokemonToReceiverDialog
import com.example.pokedexapp.ui.theme.TopBarBlueColor

@Composable
fun PokemonDetailScreen(
    state: PokemonDetailScreenUiState,
    onEvent: (PokemonDetailScreenOnEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pokemonImageSize = 200.dp

    PokemonTypesColorBackground(pokemon = state.pokemonDetailModel, modifier = modifier) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                PokemonDetailTopAppBarWrapper(
                    currentSprite = state.pokemonSprite,
                    pokemonHasCry = !state.pokemonDetailModel?.latestCry.isNullOrBlank(),
                    onEvent = onEvent,
                )
            },
        ) { scaffoldPadding ->

            val context = LocalContext.current

            Box(
                modifier =
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(scaffoldPadding),
            ) {
                PokemonInformationSheet(
                    isLoading = state.isLoading,
                    isError = state.isError,
                    pokemon = state.pokemonDetailModel,
                    evolutionChain = state.evolutionChain,
                    contentTopSpace = pokemonImageSize / 2 - 20.dp,
                    onEvent = onEvent,
                    modifier =
                        Modifier
                            .animateContentSize()
                            .padding(
                                top = pokemonImageSize / 2 + 20.dp,
                                start = 30.dp,
                                end = 30.dp,
                            )
                            .padding(bottom = 32.dp)
                            .fillMaxWidth(),
                )

                if (!state.isLoading && !state.isError) {
                    PokemonImageWrapper(
                        image = state.pokemonSprite?.spriteUrl,
                        imageSize = pokemonImageSize,
                        modifier =
                            Modifier
                                .fillMaxWidth(),
                    )
                }

                if (state.isSharingPokemonToReceiver) {
                    SharePokemonToReceiverDialog(
                        isError = state.isErrorSharingPokemonToReceiver,
                        text = state.receiverToken,
                        onDismiss = { onEvent(PokemonDetailScreenOnEvent.SwitchIsSharingPokemonToReceiver) },
                        onConfirm = { onEvent(PokemonDetailScreenOnEvent.SharePokemonToReceiver) },
                        onTokenChange = { newToken ->
                            onEvent(
                                PokemonDetailScreenOnEvent.OnReceiverTokenChange(
                                    newToken,
                                ),
                            )
                        },
                    )
                }

                if (state.isSettingPokemonAsWidget) {
                    state.pokemonDetailModel?.primaryType?.let {
                        ChoosePokemonAsWidgetDialog(
                            pokemonId = state.pokemonDetailModel.id,
                            pokemonFrontSpriteImageUrl = state.pokemonDetailModel.frontDefaultSprite.spriteUrl,
                            pokemonFrontShinySpriteImageUrl = state.pokemonDetailModel.frontShinySprite.spriteUrl,
                            pokemonBackSpriteImageUrl = state.pokemonDetailModel.backDefaultSprite.spriteUrl,
                            pokemonBackShinySpriteImageUrl = state.pokemonDetailModel.backShinySprite.spriteUrl,
                            pokemonPrimaryType = it,
                            onDismiss = { onEvent(PokemonDetailScreenOnEvent.SwitchIsSettingPokemonAsWidget) },
                            onConfirm = { choosePokemonWidgetModel ->
                                onEvent(PokemonDetailScreenOnEvent.SwitchIsSettingPokemonAsWidget)
                                onEvent(PokemonDetailScreenOnEvent.SetChoosePokemonWidgetModel(choosePokemonWidgetModel, context))
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PokemonTypesColorBackground(
    pokemon: PokemonDetailModel?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier =
            modifier
                .background(
                    Brush.linearGradient(
                        colorStops =
                            arrayOf(
                                0f to (pokemon?.primaryType?.color ?: TopBarBlueColor),
                                1f to (
                                    pokemon?.secondaryType?.color
                                        ?: MaterialTheme.colorScheme.background
                                ),
                            ),
                    ),
                ),
    ) {
        content()
    }
}

@Composable
private fun PokemonDetailTopAppBarWrapper(
    currentSprite: PokemonSprite?,
    pokemonHasCry: Boolean,
    onEvent: (PokemonDetailScreenOnEvent) -> Unit,
) {
    if (currentSprite != null) {
        val context = LocalContext.current

        PokemonDetailTopAppBar(
            isShinySprite = currentSprite.spriteType.isShiny,
            showCryAction = pokemonHasCry,
            rotateSprite = { onEvent(PokemonDetailScreenOnEvent.RotateSprite(currentSprite.spriteType)) },
            changeShinySprite = { onEvent(PokemonDetailScreenOnEvent.ChangeShinySprite(currentSprite.spriteType)) },
            openSharePokemonToReceiverDialog = { onEvent(PokemonDetailScreenOnEvent.SwitchIsSharingPokemonToReceiver) },
            playCry = { onEvent(PokemonDetailScreenOnEvent.PlayPokemonCry(context)) },
            setPokemonAsWidget = { onEvent(PokemonDetailScreenOnEvent.SwitchIsSettingPokemonAsWidget) },
            navigateUp = { onEvent(PokemonDetailScreenOnEvent.NavigateUp) },
        )
    } else {
        PokemonDetailTopAppBar(
            isShinySprite = false,
            showCryAction = pokemonHasCry,
            rotateSprite = { },
            changeShinySprite = { },
            openSharePokemonToReceiverDialog = { },
            playCry = { },
            setPokemonAsWidget = {},
            navigateUp = { onEvent(PokemonDetailScreenOnEvent.NavigateUp) },
        )
    }
}

@Preview
@Composable
private fun PokemonDetailScreenPreview() {
    PokemonDetailScreen(
        PokemonDetailScreenUiState(
            isLoading = false,
            pokemonDetailModel = PokemonSampleData.singlePokemonDetailSampleData(),
            evolutionChain = PokemonSampleData.evolutionChainSampleData(),
        ),
        onEvent = { },
    )
}

@Preview
@Composable
private fun PokemonDetailScreenLoadingPreview() {
    PokemonDetailScreen(
        PokemonDetailScreenUiState(
            isLoading = true,
        ),
        onEvent = { },
    )
}

@Preview
@Composable
private fun PokemonDetailScreenErrorPreview() {
    PokemonDetailScreen(
        PokemonDetailScreenUiState(
            isLoading = false,
            isError = true,
        ),
        onEvent = { },
    )
}
