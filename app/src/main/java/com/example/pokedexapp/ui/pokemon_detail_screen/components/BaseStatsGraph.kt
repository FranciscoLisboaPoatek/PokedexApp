package com.example.pokedexapp.ui.pokemon_detail_screen.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.models.PokemonBaseStats
import com.example.pokedexapp.domain.models.PokemonDetailModel
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.ui.utils.BASE_STAT_PROGRESS_BAR_ANIMATION_LABEL_KEY

@Composable
fun PokemonBaseStatsGraph(
    modifier: Modifier = Modifier,
    pokemon: PokemonDetailModel,
    spaceBetweenHorizontally: Dp = 8.dp,
    spaceBetweenVertically: Dp = 12.dp,
    statProgressBarHeight: Dp = 20.dp,
    baseStatLabelTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    baseStatValueTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
) {
    BaseStatsGraph(
        spaceBetweenHorizontally = spaceBetweenHorizontally,
        spaceBetweenVertically = spaceBetweenVertically,
        baseStatsIndex = pokemon.baseStats.size,
        baseStatLabel = {
            BaseStatLabel(
                pokemonBaseStats = pokemon.baseStats[it],
                style = baseStatLabelTextStyle,
            )
        },
        baseStatBar = {
            BaseStatProgressBar(
                statValue = pokemon.baseStats[it].value.toFloat(),
                color = pokemon.baseStats[it].color,
                modifier = Modifier.height(statProgressBarHeight),
            )
        },
        baseStatValue = {
            BaseStatValue(
                pokemonBaseStats = pokemon.baseStats[it],
                style = baseStatValueTextStyle,
            )
        },
        modifier = modifier,
    )
}

@Composable
fun BaseStatsGraph(
    modifier: Modifier = Modifier,
    spaceBetweenVertically: Dp = 0.dp,
    spaceBetweenHorizontally: Dp = 0.dp,
    baseStatsIndex: Int,
    baseStatLabel: @Composable (index: Int) -> Unit,
    baseStatBar: @Composable (index: Int) -> Unit,
    baseStatValue: @Composable (index: Int) -> Unit,
) {
    val baseStatLabelRep = @Composable { repeat(baseStatsIndex) { baseStatLabel(it) } }
    val baseStatBarRep = @Composable { repeat(baseStatsIndex) { baseStatBar(it) } }
    val baseStatValueRep = @Composable { repeat(baseStatsIndex) { baseStatValue(it) } }

    Layout(
        contents = listOf(baseStatLabelRep, baseStatBarRep, baseStatValueRep),
        modifier = modifier,
    ) { (baseStatLabelMeasurable, baseStatBarMeasurable, baseStatValueMeasurable), constraints ->

        require(baseStatLabelMeasurable.size > 1)

        val spaceBetweenVerticallyPx = spaceBetweenVertically.roundToPx()
        val spaceBetweenHorizontallyPx = spaceBetweenHorizontally.roundToPx()

        val totalHeightOfComponents = MutableList<Int>(3) { 0 }
        val largestLabelWidth =
            baseStatLabelMeasurable.maxOf {
                it.minIntrinsicWidth(constraints.maxHeight)
            }

        val baseStatLabelPlaceable =
            baseStatLabelMeasurable.map { measurable ->
                val placeable = measurable.measure(constraints.copy(minWidth = largestLabelWidth))
                totalHeightOfComponents[0] += placeable.height
                placeable
            }

        val largestStatValue =
            baseStatValueMeasurable.maxOf { measurable ->
                measurable.minIntrinsicWidth(constraints.maxHeight)
            }

        val baseStatValuePlaceable =
            baseStatValueMeasurable.map { measurable ->
                val placeable = measurable.measure(constraints.copy(minWidth = largestStatValue))
                totalHeightOfComponents[1] += placeable.height
                placeable
            }

        val baseStatBarWidth =
            constraints.maxWidth - largestLabelWidth - largestStatValue - (2 * spaceBetweenHorizontallyPx)

        val baseStatBarPlaceable =
            baseStatBarMeasurable.map { measurable ->
                val placeable =
                    measurable.measure(
                        constraints.copy(
                            minWidth = baseStatBarWidth,
                            maxWidth = baseStatBarWidth,
                        ),
                    )
                totalHeightOfComponents[2] += placeable.height
                placeable
            }

        val highestComponent = totalHeightOfComponents.max() / baseStatsIndex
        val totalHeight =
            totalHeightOfComponents.max() + ((baseStatsIndex - 1) * spaceBetweenVerticallyPx)

        val statBarHeight = baseStatBarPlaceable.first().height
        val statLabelHeight = baseStatLabelPlaceable.first().height

        layout(constraints.maxWidth, totalHeight) {
            var yPosition =
                if (statBarHeight > statLabelHeight) (statBarHeight - statLabelHeight) / 2 else 0
            baseStatLabelPlaceable.forEachIndexed { index, placeable ->
                placeable.place(0, yPosition)
                val currentBaseStatBar = baseStatBarPlaceable[index]
                currentBaseStatBar.place(
                    largestLabelWidth + spaceBetweenHorizontallyPx,
                    yPosition + (placeable.height / 2) - (currentBaseStatBar.height / 2),
                )

                baseStatValuePlaceable[index].place(
                    largestLabelWidth + currentBaseStatBar.width + 2 * spaceBetweenHorizontallyPx,
                    yPosition,
                )
                yPosition += highestComponent + spaceBetweenVerticallyPx
            }
        }
    }
}

@Composable
private fun BaseStatLabel(
    pokemonBaseStats: PokemonBaseStats,
    style: TextStyle,
) {
    Text(
        text = stringResource(id = chooseBaseStatStringId(pokemonBaseStats)),
        style = style,
        textAlign = TextAlign.End,
        modifier = Modifier,
    )
}

@Composable
private fun BaseStatValue(
    pokemonBaseStats: PokemonBaseStats,
    style: TextStyle,
) {
    Text(
        text = pokemonBaseStats.value.toString(),
        style = style,
    )
}

private fun chooseBaseStatStringId(pokemonBaseStats: PokemonBaseStats): Int {
    return when (pokemonBaseStats) {
        is PokemonBaseStats.Hp -> R.string.hp_base_stat
        is PokemonBaseStats.Attack -> R.string.attack_base_stat
        is PokemonBaseStats.Defense -> R.string.defense_base_stat
        is PokemonBaseStats.SpecialAttack -> R.string.special_attack_base_stat
        is PokemonBaseStats.SpecialDefense -> R.string.special_defense_base_stat
        is PokemonBaseStats.Speed -> R.string.speed_base_stat
    }
}

@Composable
private fun BaseStatProgressBar(
    statValue: Float,
    color: Color,
    modifier: Modifier = Modifier,
) {
    var progress by remember { mutableStateOf(0f) }

    val progressAnimation: Float by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000),
        label = BASE_STAT_PROGRESS_BAR_ANIMATION_LABEL_KEY,
    )

    val maxStat = 200f
    Surface(
        shape = CircleShape,
        modifier = modifier,
    ) {
        LinearProgressIndicator(
            progress = { progressAnimation / maxStat },
            color = color,
            trackColor = Color.LightGray,
            strokeCap = StrokeCap.Round,
        )
    }

    LaunchedEffect(key1 = Unit) {
        progress = statValue
    }
}

@Preview
@Composable
private fun PokemonBaseStatPreview() {
    Surface(color = Color.White) { PokemonBaseStatsGraph(pokemon = PokemonSampleData.singlePokemonDetailSampleData()) }
}
