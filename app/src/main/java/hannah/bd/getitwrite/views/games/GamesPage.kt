package hannah.bd.getitwrite.views.games

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import hannah.bd.getitwrite.views.components.HeadlineAndSubtitle

@Composable
fun GamesPage(navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            HeadlineAndSubtitle(
                title = "Writing Games",
                subtitle = "Writing games to keep you on top form."
            )
        }
        item { PromptsCTA() }
        item {
            VocabCTA(onClick = { navController.navigate("vocabGame") })
        }
        item {
            EditingGameCTA(onClick = { navController.navigate("editingGame") })
        }
    }
}
