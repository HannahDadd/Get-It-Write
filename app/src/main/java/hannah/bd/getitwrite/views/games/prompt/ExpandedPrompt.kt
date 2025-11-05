package hannah.bd.getitwrite.views.games.prompt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.modals.BadgeTitles
import hannah.bd.getitwrite.views.badges.BadgeView

@Composable
fun ExpandedPrompt(question: String) {
    var btnPressed by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(question, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.weight(1f))

        Text(if (btnPressed) "" else "Did you do this prompt? Tell us!")

        StretchedButton(
            text = if (btnPressed) "Congrats!" else "I did the prompt!",
            onClick = {
                // Increment prompt badge
                BadgeView.incrementBadge(1, BadgeTitles.PromptsUsed, context)
                btnPressed = true
            }
        )
    }
}
