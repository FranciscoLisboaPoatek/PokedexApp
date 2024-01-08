package com.example.pokedexapp.ui.pokemon_detail_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun BaseStatsGraph(
    spaceBetweenVertically: Dp = 0.dp,
    spaceBetweenHorizontally: Dp = 0.dp,
    baseStatsIndex: Int,
    baseStatLabel: @Composable (index: Int) -> Unit,
    baseStatBar: @Composable (index: Int) -> Unit,
    baseStatValue: @Composable (index: Int) -> Unit,
    modifier: Modifier = Modifier

) {
    val baseStatLabel = @Composable { repeat(baseStatsIndex) { baseStatLabel(it) } }
    val baseStatBar = @Composable { repeat(baseStatsIndex) { baseStatBar(it) } }
    val baseStatValue = @Composable { repeat(baseStatsIndex) { baseStatValue(it) } }

    Layout(
        contents = listOf(baseStatLabel, baseStatBar, baseStatValue),
        modifier = modifier

    ) { (baseStatLabelMeasurable, baseStatBarMeasurable, baseStatValueMeasurable), constraints ->

        require(baseStatLabelMeasurable.size > 1)

        val spaceBetweenVerticallyPx = spaceBetweenVertically.roundToPx()
        val spaceBetweenHorizontallyPx = spaceBetweenHorizontally.roundToPx()

        val totalHeightOfComponents = MutableList<Int>(3) { 0 }
        val largestLabelWidth = baseStatLabelMeasurable.maxOf {
            it.minIntrinsicWidth(constraints.maxHeight)
        }

        val baseStatLabelPlaceable = baseStatLabelMeasurable.map { measurable ->
            val placeable = measurable.measure(constraints.copy(minWidth = largestLabelWidth))
            totalHeightOfComponents[0] += placeable.height
            placeable
        }

        val largestStatValue = baseStatValueMeasurable.maxOf { measurable ->
            measurable.minIntrinsicWidth(constraints.maxHeight)
        }

        val baseStatValuePlaceable = baseStatValueMeasurable.map { measurable ->
            val placeable = measurable.measure(constraints.copy(minWidth = largestStatValue))
            totalHeightOfComponents[1] += placeable.height
            placeable
        }

        val baseStatBarWidth = constraints.maxWidth - largestLabelWidth - largestStatValue - (2 * spaceBetweenHorizontallyPx)

        val baseStatBarPlaceable = baseStatBarMeasurable.map { measurable ->
            val placeable = measurable.measure(
                constraints.copy(
                    minWidth = baseStatBarWidth,
                    maxWidth = baseStatBarWidth
                )
            )
            totalHeightOfComponents[2] += placeable.height
            placeable
        }

        val highestComponent = totalHeightOfComponents.max() / baseStatsIndex
        val totalHeight =
            totalHeightOfComponents.max() + ((baseStatsIndex - 1) * spaceBetweenVerticallyPx)

        val statBarHeight = baseStatBarPlaceable.first().height
        val statLabelHeight = baseStatLabelPlaceable.first().height

        layout(constraints.maxWidth, totalHeight)
        {
            var yPosition =
                if (statBarHeight > statLabelHeight) (statBarHeight - statLabelHeight) / 2 else 0
            baseStatLabelPlaceable.forEachIndexed { index, placeable ->
                placeable.place(0, yPosition)
                val currentBaseStatBar = baseStatBarPlaceable[index]
                currentBaseStatBar.place(
                    largestLabelWidth + spaceBetweenHorizontallyPx,
                    yPosition + (placeable.height / 2) - (currentBaseStatBar.height / 2)
                )

                baseStatValuePlaceable[index].place(
                    largestLabelWidth + currentBaseStatBar.width + 2 * spaceBetweenHorizontallyPx,
                    yPosition
                )
                yPosition += highestComponent + spaceBetweenVerticallyPx
            }

        }
    }

}