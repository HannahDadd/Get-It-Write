package hannah.bd.getitwrite.views.account

import android.text.format.DateUtils
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.DetailHeader
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun StatsSection(user: User) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        user.lastCritique?.let {
            Text(
                text = "Last critique: ${
                    DateUtils.getRelativeTimeSpanString(
                        (it.seconds * 1000),
                        System.currentTimeMillis(),
                        DateUtils.DAY_IN_MILLIS
                    )
                }", style = MaterialTheme.typography.titleLarge
            )
        }
        user.frequencey?.let { timeInSeconds ->
            val days = TimeUnit.SECONDS.toDays(timeInSeconds.toLong())
            val hours = TimeUnit.SECONDS.toHours(timeInSeconds.toLong()) % 24
            val minutes = TimeUnit.SECONDS.toMinutes(timeInSeconds.toLong()) % 60
            val seconds = timeInSeconds % 60

            val humanReadableTime = buildString {
                if (days > 0) append("$days days ")
                if (hours > 0) append("$hours hours ")
                if (minutes > 0) append("$minutes minutes ")
            }.trim()

            Text(text = "Critique average is ${humanReadableTime}")
        }
    }
}

@Composable
fun StatsView(user: User, navController: NavHostController) {
    Column {
        DetailHeader(title = "Stats on how often you use Get It Write", navigateUp = { navController.navigateUp() })
        Column(
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            user.lastCritique?.let {
                Text(
                    text = "Last critique: ${
                        DateUtils.getRelativeTimeSpanString(
                            (it.seconds * 1000),
                            System.currentTimeMillis(),
                            DateUtils.DAY_IN_MILLIS
                        )
                    }", style = MaterialTheme.typography.titleLarge
                )
            }
            user.lastFiveCritiques?.let {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val map = it.map {
                    dateFormat.format(it.toDate())
                }
                val frequencyMap = map.groupingBy { it }.eachCount()
                BarGraph(data = frequencyMap)
            } ?: run {
                Text(text = "Not enough data to create a graph.")
            }
            user.frequencey?.let { timeInSeconds ->
                val days = TimeUnit.SECONDS.toDays(timeInSeconds.toLong())
                val hours = TimeUnit.SECONDS.toHours(timeInSeconds.toLong()) % 24
                val minutes = TimeUnit.SECONDS.toMinutes(timeInSeconds.toLong()) % 60
                val seconds = timeInSeconds % 60

                val humanReadableTime = buildString {
                    if (days > 0) append("$days days ")
                    if (hours > 0) append("$hours hours ")
                    if (minutes > 0) append("$minutes minutes ")
                    if (seconds > 0 || isEmpty()) append("$seconds seconds")
                }.trim()

                Text(text = "On average, you critique every ${humanReadableTime}")
            }
        }
    }
}
@Composable
fun BarGraph(data: Map<String, Int>) {
    val sortedData = data.toList().sortedBy { it.first }
    val maxValue = sortedData.maxOf { it.second }

    val barWidth = 50.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Y-axis labels
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            sortedData.forEach { (date, value) ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Bars
                    Canvas(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(barWidth)
                    ) {
                        val barHeight = (value.toFloat() / maxValue) * size.height
                        drawRect(
                            color = Color.Blue,
                            size = androidx.compose.ui.geometry.Size(
                                width = barWidth.toPx(),
                                height = barHeight
                            ),
                            topLeft = androidx.compose.ui.geometry.Offset(
                                x = 0f,
                                y = size.height - barHeight
                            )
                        )
                    }

                    // Date labels
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicText(
                        text = date,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}