package hannah.bd.getitwrite.views.games.editing

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.GlobalVariables

@Composable
fun EditingQuestion(
    back: () -> Unit
) {
    val context = LocalContext.current
    val sentence = remember { GlobalVariables.badSentences.random() }
    var response by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text("Rewrite the sentence below to practice your editing skills.")
        Text(sentence, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = response,
            onValueChange = { response = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
            label = { Text("Your edit") }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "I got this sentence on the Get It Write app: $sentence\nThis is my edit: $response"
                    )
                }
                context.startActivity(Intent.createChooser(sendIntent, "Thoughts?"))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Share")
        }

        StretchedButton(
            text = "Done",
            onClick = back
        )
    }
}
