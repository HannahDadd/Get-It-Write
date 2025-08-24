package hannah.bd.getitwrite.views.sprints

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import hannah.bd.getitwrite.modals.AppDatabase
import hannah.bd.getitwrite.modals.Stat
import hannah.bd.getitwrite.modals.WIP
import hannah.bd.getitwrite.views.components.NumberInput
import hannah.bd.getitwrite.views.components.SprintTimePicker
import hannah.bd.getitwrite.views.graphs.GraphForWIP
import hannah.bd.getitwrite.views.wips.WIPView
import java.util.Date
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun SprintStack(db: AppDatabase, onFinish: () -> Unit) {
    var sprintState by remember { mutableStateOf(SprintState.START) }
    var selectedWip by remember { mutableStateOf<WIP?>(null) }
    var startWordCount by remember { mutableStateOf(0) }
    var endWordCount by remember { mutableStateOf(0) }
    var selectedTime by remember { mutableStateOf(20) }
    var showWipSelector by remember { mutableStateOf(false) }

    when (sprintState) {
        SprintState.START -> {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Let's Sprint!", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(16.dp))
                selectedWip?.let {
                    Text("Selected project", style = MaterialTheme.typography.titleMedium)
                    WIPView(wip = it) {}
                    Button(onClick = { showWipSelector = true }) {
                        Text("Change the WIP you're working on.")
                    }
                } ?: Button(onClick = { showWipSelector = true }) {
                    Text("Select the project you're working on.")
                }

                NumberInput(label = "Start Word Count", value = startWordCount) {
                    startWordCount = it
                }

                SprintTimePicker(onConfirm = {
                    selectedTime = (it.hour * 60 + it.minute)
                }, onDismiss = {})

                Spacer(Modifier.weight(1f))
                Button(onClick = { sprintState = SprintState.SPRINT }) {
                    Text("Start")
                }
            }

            if (showWipSelector) {
                Dialog(onDismissRequest = { showWipSelector = false }) {
                    SelectWip(onWipSelected = {
                        selectedWip = it
                        startWordCount = it.count
                        showWipSelector = false
                    })
                }
            }
        }

        SprintState.SPRINT -> {
            Sprint(
                initialTime = selectedTime,
                onEnd = { sprintState = SprintState.END }
            )
        }

        SprintState.END -> {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Sprint Finished!", style = MaterialTheme.typography.headlineMedium)
                selectedWip?.let {
                    Text("Selected project:", style = MaterialTheme.typography.titleMedium)
                    WIPView(wip = it) {}
                }
                Text("Start word count: $startWordCount words", fontWeight = FontWeight.Bold)
                NumberInput(label = "End Word Count", value = endWordCount) {
                    endWordCount = it
                }

                Spacer(Modifier.weight(1f))
                Button(onClick = {
                    val wordsWritten = endWordCount - startWordCount
                    val stat = Stat(
                        id = Random.nextInt(),
                        wordsWritten = wordsWritten,
                        date = Date(),
                        wipId = selectedWip?.id,
                        minutes = selectedTime
                    )
                    db.statDao().insertAll(arrayOf(stat))
                    selectedWip?.let { w ->
                        db.wipDao().delete(w)
                        val newWip = WIP(
                            id = w.id,
                            title = w.title,
                            count = w.count + wordsWritten,
                            goal = w.goal
                        )
                        db.wipDao().insertAll(arrayOf(newWip))
                    }
                    sprintState = SprintState.SHOW_RESULTS
                }) {
                    Text("Finish")
                }
            }
        }

        SprintState.SHOW_RESULTS -> {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("You're one step closer to hitting that writing goal!", style = MaterialTheme.typography.headlineMedium)
                Text("You wrote ${endWordCount - startWordCount} words in ${selectedTime} minutes.")
                selectedWip?.let {
                    Text("Selected project:", style = MaterialTheme.typography.titleMedium)
                    WIPView(wip = it) {}
                    GraphForWIP(wip = it)
                }
                Button(onClick = onFinish) {
                    Text("Back To Home Page")
                }
            }
        }
    }
}

enum class SprintState(val label: String) {
    START("Start"),
    SPRINT("Sprint"),
    END("End"),
    SHOW_RESULTS("Show Results")
}
