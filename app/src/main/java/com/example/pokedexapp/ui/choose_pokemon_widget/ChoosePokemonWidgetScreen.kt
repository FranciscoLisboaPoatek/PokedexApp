package com.example.pokedexapp.ui.choose_pokemon_widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.models.ChoosePokemonWidgetModel
import com.example.pokedexapp.domain.models.PokemonSprite
import com.example.pokedexapp.domain.models.PokemonTypes
import com.example.pokedexapp.ui.components.BasicAppBar
import com.example.pokedexapp.ui.components.NavigateUpIconButton
import com.example.pokedexapp.ui.components.PokeballLoadingAnimation
import com.example.pokedexapp.ui.pokemon_detail_screen.components.PokemonImageWrapper
import com.example.pokedexapp.ui.theme.TopBarBlueColor

@Composable
fun ChoosePokemonWidgetScreen(
    modifier: Modifier = Modifier,
    state: ChoosePokemonAsWidgetUiState,
    onEvent: (ChoosePokemonAsWidgetScreenOnEvent) -> Unit
) {
    if (state.isLoading) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Transparent),
        ) {
            PokeballLoadingAnimation(
                modifier =
                    Modifier
                        .align(Alignment.Center),
            )
        }
    } else {
        state.choosePokemonAsWidgetScreenModel?.let {
            Content(
                modifier = modifier,
                pokemonAsWidgetScreenModel = it,
                onEvent = onEvent
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Content(
    modifier: Modifier = Modifier,
    pokemonAsWidgetScreenModel: ChoosePokemonAsWidgetScreenModel,
    onEvent: (ChoosePokemonAsWidgetScreenOnEvent) -> Unit,
) {
    var selectedImage by remember {
        mutableStateOf<PokemonSprite>(pokemonAsWidgetScreenModel.pokemonFrontSpriteImageUrl)
    }

    var selectedColor by remember {
        mutableStateOf(pokemonAsWidgetScreenModel.pokemonPrimaryType.color)
    }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            BasicAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.topbar_configure_widget),
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigateUpIcon = {
                    NavigateUpIconButton(
                        { onEvent(ChoosePokemonAsWidgetScreenOnEvent.onCancel) },
                        Color.White
                    )
                }
            )
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(Modifier.height(24.dp))

            Box(
                Modifier
                    .sizeIn(maxHeight = 200.dp, maxWidth = 200.dp)
                    .background(selectedColor, RoundedCornerShape(12.dp)),
            ) {
                PokemonImageWrapper(
                    image = selectedImage.spriteUrl,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                )
            }

            Spacer(Modifier.height(24.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SelectablePokemonImage(
                        selectedImage == pokemonAsWidgetScreenModel.pokemonFrontSpriteImageUrl,
                        pokemonAsWidgetScreenModel.pokemonFrontSpriteImageUrl
                    ) {
                        selectedImage =
                            pokemonAsWidgetScreenModel.pokemonFrontSpriteImageUrl
                    }

                    SelectablePokemonImage(
                        selectedImage == pokemonAsWidgetScreenModel.pokemonFrontShinySpriteImageUrl,
                        pokemonAsWidgetScreenModel.pokemonFrontShinySpriteImageUrl
                    ) {
                        selectedImage =
                            pokemonAsWidgetScreenModel.pokemonFrontShinySpriteImageUrl
                    }

                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SelectablePokemonImage(
                        selectedImage == pokemonAsWidgetScreenModel.pokemonBackSpriteImageUrl,
                        pokemonAsWidgetScreenModel.pokemonBackSpriteImageUrl
                    ) {
                        selectedImage =
                            pokemonAsWidgetScreenModel.pokemonBackSpriteImageUrl
                    }
                    SelectablePokemonImage(
                        selectedImage == pokemonAsWidgetScreenModel.pokemonBackShinySpriteImageUrl,
                        pokemonAsWidgetScreenModel.pokemonBackShinySpriteImageUrl
                    ) {
                        selectedImage =
                            pokemonAsWidgetScreenModel.pokemonBackShinySpriteImageUrl
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                PokemonTypes.entries.forEachIndexed { index, type ->
                    Box(
                        modifier =
                            Modifier.colorPick(
                                selectedColor,
                                type.color,
                            ) {
                                selectedColor = type.color
                            }
                    )
                }
                Box(
                    modifier =
                        Modifier
                            .colorPick(selectedColor, Color.White, Color.Black) {
                                selectedColor = Color.White
                            }
                )

                Box(
                    modifier =
                        Modifier
                            .colorPick(selectedColor, Color.Black, Color.White) {
                                selectedColor = Color.Black
                            }
                )

                Icon(
                    modifier =
                        Modifier
                            .colorPick(selectedColor, Color.Transparent) {
                                selectedColor = Color.Transparent
                            },
                    painter = painterResource(R.drawable.ic_baseline_format_color_reset_24),
                    tint = Color.Gray,
                    contentDescription = null
                )
            }

            Spacer(Modifier.height(24.dp))

            Row {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TopBarBlueColor,
                    ),
                    shape = ButtonDefaults.outlinedShape,
                    onClick = {
                        onEvent(
                            ChoosePokemonAsWidgetScreenOnEvent.onChoosePokemonWidgetModel(
                                ChoosePokemonWidgetModel(
                                    id = pokemonAsWidgetScreenModel.pokemonId,
                                    imageUrl = selectedImage.spriteUrl,
                                    color = selectedColor.toArgb(),
                                ),
                                context
                            )
                        )
                    },
                ) {
                    Text(stringResource(R.string.cancel))
                }

                Spacer(Modifier.width(12.dp))

                Button(
                    modifier = Modifier.weight(1f),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = TopBarBlueColor,
                        ),
                    onClick = {
                        onEvent( 
                            ChoosePokemonAsWidgetScreenOnEvent.onChoosePokemonWidgetModel(
                                ChoosePokemonWidgetModel(
                                    id = pokemonAsWidgetScreenModel.pokemonId,
                                    imageUrl = selectedImage.spriteUrl,
                                    color = selectedColor.toArgb(),
                                ),
                                context
                            )
                        )
                    },
                ) {
                    Text(stringResource(R.string.confirm))
                }
            }

            Spacer(Modifier.height(12.dp))

        }
    }
}

@Composable
private fun SelectablePokemonImage(
    selected: Boolean,
    pokemonSprite: PokemonSprite,
    onClick: () -> Unit
) {
    PokemonImageWrapper(
        image = pokemonSprite.spriteUrl,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .sizeIn(maxHeight = 120.dp, maxWidth = 120.dp)
            .aspectRatio(1f)
            .conditional(
                selected,
                {
                    border(
                        1.dp,
                        MaterialTheme.colorScheme.onSurface,
                        RoundedCornerShape(16.dp),
                    )
                },
            )
            .clickable(pokemonSprite.spriteUrl != null) {
                onClick()
            }
    )
}

inline fun Modifier.conditional(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier,
    ifFalse: Modifier.() -> Modifier = { this },
): Modifier =
    if (condition) {
        then(ifTrue(Modifier))
    } else {
        then(ifFalse(Modifier))
    }

@Composable
private inline fun Modifier.colorPick(
    selectedColor: Color,
    color: Color,
    selectedBorderColor: Color = MaterialTheme.colorScheme.onSurface,
    crossinline onClick: () -> Unit
) =
    clip(CircleShape)
        .size(36.dp)
        .background(color)
        .clickable {
            onClick()
        }
        .conditional(
            selectedColor == color,
            {
                border(
                    2.dp,
                    selectedBorderColor,
                    CircleShape,
                )
            },
        )
