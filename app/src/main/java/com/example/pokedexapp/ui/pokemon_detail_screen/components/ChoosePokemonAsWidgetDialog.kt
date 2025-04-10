package com.example.pokedexapp.ui.pokemon_detail_screen.components

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.pokedexapp.domain.models.ChoosePokemonWidgetModel
import com.example.pokedexapp.domain.models.PokemonTypes
import com.example.pokedexapp.ui.theme.TopBarBlueColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChoosePokemonAsWidgetDialog(
    modifier: Modifier = Modifier,
    pokemonId: String,
    pokemonFrontSpriteImageUrl: String?,
    pokemonFrontShinySpriteImageUrl: String?,
    pokemonBackSpriteImageUrl: String?,
    pokemonBackShinySpriteImageUrl: String?,
    pokemonPrimaryType: PokemonTypes,
    onDismiss: () -> Unit,
    onConfirm: (ChoosePokemonWidgetModel) -> Unit,
) {
    var selectedImage by remember {
        mutableStateOf(pokemonFrontSpriteImageUrl)
    }

    var selectedColor by remember {
        mutableStateOf(pokemonPrimaryType.color)
    }

    var selectedType by remember {
        mutableStateOf(pokemonPrimaryType)
    }

    Dialog(
        onDismiss,
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(
                modifier = modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Set this Pokemon to be in your Widget",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                )

                Box(
                    Modifier.size(120.dp).background(selectedColor, RoundedCornerShape(12.dp)),
                ) {
                    PokemonImageWrapper(
                        image = selectedImage,
                        imageSize = null,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                Spacer(Modifier.height(24.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    PokemonTypes.entries.forEachIndexed { index, type ->
                        Box(
                            modifier =
                                Modifier
                                    .clip(CircleShape)
                                    .size(36.dp)
                                    .background(type.color)
                                    .clickable {
                                        selectedColor = type.color
                                        selectedType = type
                                    }
                                    .conditional(
                                        selectedType == type,
                                        {
                                            border(
                                                1.dp,
                                                MaterialTheme.colorScheme.onSurface,
                                                CircleShape,
                                            )
                                        },
                                    ),
                        )
                    }
                }

                Button(
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = TopBarBlueColor,
                        ),
                    onClick = {
                        onConfirm(
                            ChoosePokemonWidgetModel(
                                id = pokemonId,
                                imageUrl = selectedImage,
                                color = selectedColor.toArgb(),
                            ),
                        )
                    },
                ) {
                    Text("Confirm")
                }
            }
        }
    }
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
