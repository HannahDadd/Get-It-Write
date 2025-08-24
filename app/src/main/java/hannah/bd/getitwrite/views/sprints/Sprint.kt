package hannah.bd.getitwrite.views.sprints

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hannah.bd.getitwrite.GlobalVariables
import hannah.bd.getitwrite.GlobalVariables.inspiringQuotes
import kotlinx.coroutines.delay

@Composable
fun Sprint(
    initialTime: Int,
    onEnd: () -> Unit
) {
    var timeRemaining by remember { mutableStateOf(initialTime) }
    var quoteNumber by remember { mutableStateOf(0) }

    // Timer logic
    LaunchedEffect(timeRemaining) {
        if (timeRemaining > 0) {
            delay(1000L)
            if (timeRemaining % 10 == 0) {
                quoteNumber = (1 until GlobalVariables.inspiringQuotes.size - 1).random()
            }
            timeRemaining--
        } else {
            onEnd()
        }
    }

    // UI layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = inspiringQuotes[quoteNumber],
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sprint Time Remaining (minutes):",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp
            )

            Text(
                text = "$timeRemaining",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 32.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
