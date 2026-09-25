package hannah.bd.getitwrite.views.sprints

import android.R.attr.fontFamily
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hannah.bd.getitwrite.GlobalVariables
import hannah.bd.getitwrite.GlobalVariables.inspiringQuotes
import hannah.bd.getitwrite.R
import hannah.bd.shelfify.modals.Stat
import hannah.bd.shelfify.views.sprints.SprintBackGroundView
import kotlinx.coroutines.delay
import java.util.Date
import kotlin.math.floor
import kotlin.random.Random

@Composable
fun Sprint(
    initialTime: Int,
    onEnd: () -> Unit
) {
    var timeRemaining by remember { mutableStateOf(initialTime) }
    var quoteNumber by remember { mutableStateOf(0) }

    LaunchedEffect(timeRemaining) {
        if (timeRemaining > 0) {
            delay(1000L)
            if (timeRemaining % 60 == 0) {
                quoteNumber = (1 until GlobalVariables.inspiringQuotes.size - 1).random()
            }
            timeRemaining--
        } else {
            onEnd()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SprintBackGroundView()
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${ String.format("%02d:%02d", timeRemaining / 60, timeRemaining % 60) }",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.dynapuff)),
                fontSize = 120.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = inspiringQuotes[quoteNumber],
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.dynapuff)),
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                onEnd()
            }) {
                Text("End Sprint Early")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
