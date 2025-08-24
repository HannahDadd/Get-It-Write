package hannah.bd.getitwrite.views.wips

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.modals.WIP
import kotlin.random.Random

@Composable
fun NewWIP(
    existingWips: List<WIP>,
    onWipAdded: (List<WIP>) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var currentWordCount by remember { mutableStateOf(0) }
    var targetWordCount by remember { mutableStateOf(0) }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        Text("New Work in Progress (WIP)", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        NumberInput(label = "Current Word Count", value = currentWordCount) {
            currentWordCount = it
        }

        NumberInput(label = "Target Word Count", value = targetWordCount) {
            targetWordCount = it
        }

        Spacer(Modifier.weight(1f))

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = Color.Red)
        }

        Button(
            onClick = {
                val newWip = WIP(
                    id = Random.nextInt(),
                    title = title,
                    count = currentWordCount,
                    goal = targetWordCount
                )
                val updatedList = listOf(newWip) + existingWips
                val json = Gson().toJson(updatedList)
                val prefs = context.getSharedPreferences("user_defaults", Context.MODE_PRIVATE)
                prefs.edit().putString("wips", json).apply()
                onWipAdded(updatedList)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add WIP")
        }
    }
}
