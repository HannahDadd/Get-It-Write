package hannah.bd.getitwrite.views.games.vocab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.GlobalVariables
import hannah.bd.getitwrite.views.components.StretchedButton

@Composable
fun VocabGame(onDone: () -> Unit) {
    var word by remember { mutableStateOf(GlobalVariables.vocabMap.entries.shuffled().firstOrNull()) }
    var fakeWords by remember {
        mutableStateOf(
            GlobalVariables.vocabMap.values.shuffled().take(3)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VocabQuestion(
            word = word?.key.orEmpty(),
            definition = word?.value.orEmpty(),
            options = fakeWords, {
            word = GlobalVariables.vocabMap.entries.shuffled().firstOrNull()
            fakeWords = GlobalVariables.vocabMap.values.shuffled().take(3)
        })

        Spacer(modifier = Modifier.weight(1f))

        StretchedButton(text = "Done", onClick = onDone)
    }
}
