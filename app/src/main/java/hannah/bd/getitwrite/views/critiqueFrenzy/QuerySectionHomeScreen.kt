package hannah.bd.getitwrite.views.critiqueFrenzy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.views.components.SquareTileButton

@Composable
fun QuickQueryCritique(requests: MutableState<List<RequestCritique>?>, navController: NavController, onCreate: () -> Unit) {
    if (requests.value?.isNotEmpty() == true) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Text(
                text = "Quick query critique",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.padding(16.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                item {
                    SquareTileButton(
                        title = "Add your own.",
                        wordCount = "",
                        backgroundColour = MaterialTheme.colorScheme.secondaryContainer,
                        textColour = MaterialTheme.colorScheme.onSecondaryContainer,
                        icon = Icons.Default.Add,
                        size = 150.dp,
                        onClick = onCreate
                    )
                }
                itemsIndexed(requests.value!!) { index, item ->
                    SquareTileButton(
                        title = item.genres.joinToString(),
                        wordCount = "Query",
                        backgroundColour = MaterialTheme.colorScheme.primaryContainer,
                        textColour = MaterialTheme.colorScheme.onPrimaryContainer,
                        icon = Icons.Default.Email,
                        size = 150.dp,
                        onClick = { navController.navigate("frenzy/$index") }
                    )
                }
                item {
                    SquareTileButton(
                        title = "View more.",
                        wordCount = "",
                        backgroundColour = MaterialTheme.colorScheme.tertiaryContainer,
                        textColour = MaterialTheme.colorScheme.onTertiaryContainer,
                        icon = Icons.Default.ArrowForward,
                        size = 150.dp,
                        onClick = { navController.navigate("queryFeed") }
                    )
                }
            }
        }
    } else {
        Text("Loading...", modifier = Modifier.padding(16.dp))
    }
}