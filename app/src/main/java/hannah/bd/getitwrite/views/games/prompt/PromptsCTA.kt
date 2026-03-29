package hannah.bd.getitwrite.views.games.prompt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hannah.bd.getitwrite.GlobalVariables
import hannah.bd.getitwrite.R

@Composable
fun PromptsCTA(navController: NavController) {
    val prompt = remember { GlobalVariables.writingPrompts.randomOrNull().orEmpty() }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Writing Prompt",
            fontFamily = FontFamily(Font(R.font.abrilfatfaceregular)))
        PromptCard(question = prompt, navController = navController)
    }
}
