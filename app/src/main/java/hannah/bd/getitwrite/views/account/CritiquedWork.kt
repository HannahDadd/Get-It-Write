package hannah.bd.getitwrite.views.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.views.components.SquareTileButton
import hannah.bd.getitwrite.views.feed.RectangleTileButton
import hannah.bd.getitwrite.views.feed.RectangleTileButtonNoDate

@Composable
fun CritiquedWord(navController: NavController, critiqued: MutableState<List<Critique>?>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            RectangleTileButtonNoDate(
                title = "Send work to your critique partners.",
                backgroundColour = MaterialTheme.colorScheme.background,
                textColour = MaterialTheme.colorScheme.onBackground,
                padding = 16.dp,
                icon = Icons.Default.Send,
                onClick = { navController.navigate("messages") }
            )
        }
        critiqued.value?.let {
            if (it.isNotEmpty() == true) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Your work, critiqued by your writing friends",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        if (it.size > 3) {
                            it.subList(0, 3).forEachIndexed { index, c ->
                                RectangleTileButton(
                                    title = c.title,
                                    backgroundColour = MaterialTheme.colorScheme.background,
                                    textColour = MaterialTheme.colorScheme.onBackground,
                                    onClick = { navController.navigate("critiqued/$index") }
                                )
                            }
                            RectangleTileButtonNoDate(
                                title = "View more",
                                backgroundColour = MaterialTheme.colorScheme.background,
                                textColour = MaterialTheme.colorScheme.onBackground,
                                padding = 16.dp,
                                onClick = { navController.navigate("critiquedFeed") }
                            )
                        } else {
                            it.forEachIndexed { index, c ->
                                RectangleTileButton(
                                    title = c.title,
                                    backgroundColour = MaterialTheme.colorScheme.background,
                                    textColour = MaterialTheme.colorScheme.onBackground,
                                    onClick = { navController.navigate("critiqued/$index") }
                                )
                            }
                        }
                    }
                }
            } else {
                Text("You haven't received any critiques yet. Try finding critique partners through the search page.",
                    modifier = Modifier.padding(16.dp))
            }
        } ?: run {
        Text("Loading...",
            modifier = Modifier.padding(16.dp))
        }
    }
}