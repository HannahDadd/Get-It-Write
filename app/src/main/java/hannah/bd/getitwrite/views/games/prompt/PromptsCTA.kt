package hannah.bd.getitwrite.views.games.prompt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import hannah.bd.getitwrite.GlobalVariables

@Composable
fun PromptsCTA(navController: NavController) {
    val prompt = remember { GlobalVariables.writingPrompts.randomOrNull().orEmpty() }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        TitleAndSubtitle(title = "Writing Prompt of the Day")
        PromptCard(question = prompt, navController = navController)
    }
}
