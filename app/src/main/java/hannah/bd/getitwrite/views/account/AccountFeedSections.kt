package hannah.bd.getitwrite.views.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.views.components.HomePageTileButton
import hannah.bd.getitwrite.views.components.SquareTileButton
import hannah.bd.getitwrite.views.components.TitleAndSubText

@Composable
fun PositiveCritiqued(critiques: MutableState<List<Critique>?>, title: String, subTitle: String,
                      dbName: String, navController: NavController, onCreate: () -> Unit) {
    critiques.value?.let {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            TitleAndSubText(
                title = title,
                subTitle,
                MaterialTheme.colorScheme.onSurface
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    SquareTileButton(
                        modifier = Modifier.padding(start = 8.dp),
                        title = "Add.",
                        wordCount = "",
                        backgroundColour = MaterialTheme.colorScheme.background,
                        textColour = MaterialTheme.colorScheme.onBackground,
                        icon = Icons.Default.Add,
                        size = 150.dp,
                        onClick = onCreate
                    )
                }
                if (it.size > 3) {
                    itemsIndexed(it.subList(0, 3)) {index, item ->
                        HomePageTileButton(
                            title = item.title,
                            bubbleText = "${item.comments.size} comments",
                            icon = Icons.Default.Edit,
                            isFirstItemInCarousel = false,
                            onClick = { navController.navigate("$dbName/$index") })
                    }
                    item {
                        SquareTileButton(
                            modifier = Modifier.padding(end = 8.dp),
                            title = "View more.",
                            wordCount = "",
                            backgroundColour = MaterialTheme.colorScheme.background,
                            textColour = MaterialTheme.colorScheme.onBackground,
                            icon = Icons.Default.ArrowForward,
                            size = 150.dp,
                            onClick = { navController.navigate("$dbName-Feed") }
                        )
                    }
                } else {
                    itemsIndexed(it) {index, item ->
                        HomePageTileButton(
                            title = item.title,
                            bubbleText = "${item.comments.size} comments",
                            icon = Icons.Default.Edit,
                            isFirstItemInCarousel = false,
                            onClick = {navController.navigate("$dbName/$index")})
                    }
                }
            }
        }
    } ?: run {
        Text("Loading...", modifier = Modifier.padding(16.dp))
    }
}