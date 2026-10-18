package hannah.bd.getitwrite.views.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.patrykandpatryk.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatryk.vico.compose.axis.vertical.startAxis
import com.patrykandpatryk.vico.compose.chart.Chart
import com.patrykandpatryk.vico.compose.chart.column.columnChart
import com.patrykandpatryk.vico.compose.chart.line.lineChart
import com.patrykandpatryk.vico.core.entry.FloatEntry
import com.patrykandpatryk.vico.core.entry.entryModelOf
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.modals.AppDatabase
import hannah.bd.getitwrite.modals.Stat
import hannah.bd.getitwrite.modals.UserPreferences
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphForWriter(
    db: AppDatabase?,
    userPreferences: UserPreferences,
) {
    var stats by remember { mutableStateOf(listOf<Stat>()) }
    val wordsWritten by userPreferences
        .wordsWritten
        .collectAsState(initial = 0)

    LaunchedEffect(Unit) {

        db?.let {
            stats = db.statDao().getAll()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            item {
                Text(
                    "✨ Your Writing Stats ✨",
                    fontFamily = FontFamily(Font(R.font.dynapuff))
                )
                Text("Words written in the app: ${ wordsWritten }",
                    fontFamily = FontFamily(Font(R.font.bellefairregularfont)))
                Text(
                    "Library completes at 80k words.",
                    fontFamily = FontFamily(Font(R.font.bellefairregularfont)),
                )
            }
            item {
                Text("Library level: ${ ((wordsWritten / 5000) + 1).coerceIn(1, 20) } of 20",
                    fontFamily = FontFamily(Font(R.font.bellefairregularfont)))
            }
            item {
                Text(
                    "All Time Sprints Graphs",
                    fontFamily = FontFamily(Font(R.font.dynapuff))
                )
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {

                    var cumulative = 0
                    val entryModel = entryModelOf(stats.mapIndexed { index, stat ->
                        cumulative = cumulative + stat.wordsWritten
                        FloatEntry(index.toFloat(), cumulative.toFloat())
                    })

//                        val bestStat = stats.maxBy { it.wordsWritten }
//                        val formatter = SimpleDateFormat("dd MMM yyyy")
//                        val formatterHours = SimpleDateFormat("h:mm a")

                    Text(
                        "You've finished ${stats.size} writing sprints in the app. Look at that cumulative word count going up!",
                        fontFamily = FontFamily(Font(R.font.bellefairregularfont))
                    )
                    Chart(
                        chart = lineChart(),
                        model = entryModel,
                        startAxis = startAxis(),
                        bottomAxis = bottomAxis()
                    )
//                        Text("Your best sprint was on ${ formatter.format(bestStat.date) } at ${ formatterHours.format(bestStat.date) }. You wrote ${bestStat.wordsWritten} words.",
//                            fontFamily = FontFamily(Font(R.font.bellefairregularfont)))
                }
            }
            item {
                Text(
                    "Monthly Sprints",
                    fontFamily = FontFamily(Font(R.font.dynapuff))
                )
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    val oneMonthAgo = LocalDate.now(ZoneId.systemDefault()).minusMonths(1)
                    val oneMonthAgoDate = Date.from(oneMonthAgo.atStartOfDay(ZoneId.systemDefault()).toInstant())

                    val statsLastMonth = stats
                        .filter { it.date.after(oneMonthAgoDate) }

                    val bestStatMonth = statsLastMonth.maxByOrNull { it.wordsWritten }
                    val formatterMonth = SimpleDateFormat("dd MMM yyyy")
                    val formatterHoursMonth = SimpleDateFormat("h:mm a")

                    val entryModel = entryModelOf(statsLastMonth
                        .mapIndexed { index, stat ->
                            FloatEntry(index.toFloat(), stat.wordsWritten.toFloat())
                    })
                    Text("You've finished ${statsLastMonth.size} writing sprints in the app.",
                        fontFamily = FontFamily(Font(R.font.bellefairregularfont)))
                    Chart(
                        chart = columnChart(),
                        model = entryModel,
                        startAxis = startAxis(),
                        bottomAxis = bottomAxis(),
                    )

                    bestStatMonth?.let {
                    Text("Your best sprint in the last month was on ${ formatterMonth.format(it.date) } at ${ formatterHoursMonth.format(it.date) }. You wrote ${it.wordsWritten} words.",
                        fontFamily = FontFamily(Font(R.font.bellefairregularfont)))
                    }
                }
            }
            item {
                Text(
                    "Yearly Graphs",
                    fontFamily = FontFamily(Font(R.font.dynapuff))
                )
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    val cal: Calendar = Calendar.getInstance()
                    cal.add(Calendar.YEAR, -1)
                    val result: Date = cal.getTime()

                    val statsLastYear = stats
                        .filter { it.date.after(cal.getTime()) }

                    val bestStat = statsLastYear.maxByOrNull { it.wordsWritten }
                    val formatter = SimpleDateFormat("dd MMM yyyy")
                    val formatterHours = SimpleDateFormat("h:mm a")

                    val entryModel = entryModelOf(statsLastYear
                        .mapIndexed { index, stat ->
                            FloatEntry(index.toFloat(), stat.wordsWritten.toFloat())
                        })
                    Text("You've finished ${statsLastYear.size} writing sprints in the app. This is how much you wrote in them:",
                        fontFamily = FontFamily(Font(R.font.bellefairregularfont)))
                    Chart(
                        chart = lineChart(),
                        model = entryModel,
                        startAxis = startAxis(),
                        bottomAxis = bottomAxis()
                    )

                    bestStat?.let {
                        Text("Your best sprint in the last year was on ${ formatter.format(it.date) } at ${ formatterHours.format(it.date) }. You wrote ${it.wordsWritten} words.",
                            fontFamily = FontFamily(Font(R.font.bellefairregularfont)))
                    }
                }
            }
        }
    }
}
