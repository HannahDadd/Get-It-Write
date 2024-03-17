package com.example.getitwrite.views.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
@Composable
fun TagCloud(
    modifier: Modifier = Modifier,
    verticalPadding: Dp = 24.dp,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each children
            // Set minHeight = 0 for weight = 1f variant
            measurable.measure(constraints.copy(minHeight = 0))
        }

        // Set the size of the layout as big as it can
        layout(constraints.minWidth, constraints.maxHeight) {
            // Track the y co-ord we have placed children up to
            var xPosition = 0
            var yPosition = 0

            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = xPosition, y = yPosition)

                xPosition += placeable.width
                if (xPosition >= constraints.maxWidth) {
                    yPosition += placeable.height + verticalPadding.value.toInt()
                    xPosition = 0
                }
            }
        }
    }
}