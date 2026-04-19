package hannah.bd.getitwrite.views.sprints

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class SprintDurations {
    TWENTY_MINS,
    FORTY_MINS,
    ONE_HOUR
}

@Composable
fun SprintCarousel(
    onAction: (SprintDurations) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Choose a writing sprint".uppercase(),
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.labelLarge
        )

        LazyRow(
//            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            item {
                StartSprintCard(
                    text = "20 mins",
                    onClick = { onAction(SprintDurations.TWENTY_MINS) }
                )
            }
            item {
                StartSprintCard(
                    text = "40 mins",
                    onClick = { onAction(SprintDurations.FORTY_MINS) }
                )
            }
            item {
                StartSprintCard(
                    text = "1 hour",
                    onClick = { onAction(SprintDurations.ONE_HOUR) }
                )
            }
        }
    }
}