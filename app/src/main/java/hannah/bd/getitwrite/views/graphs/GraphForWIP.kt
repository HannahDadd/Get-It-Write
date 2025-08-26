package hannah.bd.getitwrite.views.graphs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import hannah.bd.getitwrite.modals.AppDatabase
import hannah.bd.getitwrite.modals.Stat
import hannah.bd.getitwrite.modals.WIP
import hannah.bd.getitwrite.views.wips.WIPView

@Composable
fun GraphForWIP(db: AppDatabase?, wip: WIP) {
    var stats by remember { mutableStateOf(listOf<Stat>()) }

    LaunchedEffect(Unit) {
        db?.let {
            stats = db.statDao().getStatsForWIPId(wip.id)
        }
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text("Your statistics for ${wip.title}", style = MaterialTheme.typography.headlineSmall)
        WIPView(wip) {}

        if (stats.size < 2) {
            Text("Not enough statistics to show for this project yet! Keep writing!")
        } else {
            val totalWords = stats.sumOf { it.wordsWritten }
            Text("You've written a total of $totalWords words across ${stats.size} sprints!", fontWeight = FontWeight.Bold)

            Box(Modifier.fillMaxSize()) {
                BarChart(
                    chartParameters = listOf(
                        BarParameters(
                            dataName = "Words Written",
                            data = stats.map { it.wordsWritten.toDouble() },
                            barColor = MaterialTheme.colorScheme.primary
                        ),
                    ),
                    gridColor = Color.DarkGray,
                    xAxisData = stats.map { it.date.toString() },
                    isShowGrid = true,
                    animateChart = true,
                    showGridWithSpacer = true,
                    yAxisStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                    ),
                    xAxisStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.W400
                    ),
                    yAxisRange = 15,
                    barWidth = 20.dp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(Modifier) {
                LineChart(
                    modifier = Modifier.fillMaxSize(),
                    linesParameters = listOf(
                        LineParameters(
                            label = "Words Written",
                            data = stats.map { it.wordsWritten.toDouble() },
                            lineColor = MaterialTheme.colorScheme.primary,
                            lineType = LineType.CURVED_LINE,
                            lineShadow = true,
                        ),
                    ),
                    isGrid = true,
                    gridColor = Color.Blue,
                    xAxisData = stats.map { it.date.toString() },
                    animateChart = true,
                    showGridWithSpacer = true,
                    yAxisStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray,
                    ),
                    xAxisStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.W400
                    ),
                    yAxisRange = 14,
                    oneLineChart = false,
                    gridOrientation = GridOrientation.VERTICAL
                )
            }
        }
    }
}
