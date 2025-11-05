package hannah.bd.getitwrite.views.streak

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.modals.AppDatabase
import hannah.bd.getitwrite.modals.WIP
import hannah.bd.getitwrite.views.components.NumberInput
import hannah.bd.getitwrite.views.components.StretchedButton
import hannah.bd.getitwrite.views.wips.SelectWip
import hannah.bd.getitwrite.views.wips.WIPView

@Composable
fun ExtendStreak(db: AppDatabase?, onDone: () -> Unit, context: Context = LocalContext.current) {
    val prefs = remember { context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE) }
    var streak by remember { mutableStateOf(prefs.getInt("streak", 0)) }
    var project by remember { mutableStateOf<WIP?>(null) }
    var endWordCount by remember { mutableStateOf(0) }
    var showSelectWip by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(30.dp)) {
        Text("Great job today!", style = MaterialTheme.typography.headlineSmall)
        project?.let { wip ->
            Text("Selected project", style = MaterialTheme.typography.titleMedium)
            WIPView(wip, onClick = {})
            Button(onClick = { showSelectWip = true }) {
                Text("Change WIP")
            }
        } ?: Button(onClick = { showSelectWip = true }) {
            Text("Select the project you worked on.")
        }

        NumberInput("Words written today:", endWordCount) { endWordCount = it }

        Spacer(modifier = Modifier.weight(1f))

        StretchedButton("Add words!", {
            //updateStatsAndProject(context, project, endWordCount)
            streak += endWordCount
            prefs.edit().putInt("streak", streak).apply()
            onDone()
        })
    }

    if (showSelectWip) {
        SelectWip(
            db,
            onWipSelected = { wip ->
                project = wip
                showSelectWip = false
            }
        )
    }
}
