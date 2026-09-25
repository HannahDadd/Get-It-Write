package hannah.bd.getitwrite.views.sprints

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.minutes

enum class SprintDurations {
    FIVE_SECS,
    TWENTY_MINS,
    FORTY_MINS,
    ONE_HOUR
}

@Composable
fun SprintCarousel(
    onAction: (SprintDurations) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Choose a writing sprint".uppercase(),
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.labelLarge
        )

//        StartSprintCard(
//            text = "5 Secs (debug only)",
//            onClick = {
//                onAction(SprintDurations.FIVE_SECS)
//            }
//        )

        StartSprintCard(
            text = "20 mins",
            onClick = {
                onAction(SprintDurations.TWENTY_MINS)
            }
        )

        StartSprintCard(
            text = "40 mins",
            onClick = { onAction(SprintDurations.FORTY_MINS) }
        )

        StartSprintCard(
            text = "1 hour",
            onClick = { onAction(SprintDurations.ONE_HOUR) }
                )
    }
}