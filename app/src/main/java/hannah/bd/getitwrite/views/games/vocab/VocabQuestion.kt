package hannah.bd.getitwrite.views.games.vocab

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.modals.BadgeTitles
import hannah.bd.getitwrite.views.badges.BadgeView

@Composable
fun VocabQuestion(
    word: String,
    definition: String,
    options: List<String>,
    nextCard: () -> Unit,
    context: Context = LocalContext.current
) {
    var showError by remember { mutableStateOf(false) }
    var showCorrect by remember { mutableStateOf(false) }

    // Shuffle + include correct answer
    val shuffledOptions = remember(word, definition) {
        (options + definition).shuffled()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text("What does $word mean?", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(12.dp))

        shuffledOptions.forEach { option ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(vertical = 4.dp)
                    .clickable {
                        if (option == definition) {
                            showCorrect = true
                            BadgeView.incrementBadge(1, BadgeTitles.WordsLearnt, context)
                        } else {
                            showError = true
                        }
                    },
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        option,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        if (showError) {
            AlertDialog(
                onDismissRequest = { showError = false },
                title = { Text("Not quite!") },
                text = { Text("Try again") },
                confirmButton = {
                    TextButton(onClick = { showError = false }) { Text("Close") }
                }
            )
        }

        if (showCorrect) {
            AlertDialog(
                onDismissRequest = { showCorrect = false },
                title = { Text("You got it!") },
                confirmButton = {
                    TextButton(onClick = {
                        showCorrect = false
                        nextCard()
                    }) { Text("Close") }
                }
            )
        }
    }
}
