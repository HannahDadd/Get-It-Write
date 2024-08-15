package hannah.bd.getitwrite.views.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.modals.RequestPositivity
import hannah.bd.getitwrite.theme.AppTypography
import hannah.bd.getitwrite.views.components.HomePageTileButton
import hannah.bd.getitwrite.views.components.SquareTileButton
import hannah.bd.getitwrite.views.components.TitleAndSubText

@Composable
fun CritiquedSection(critiques: MutableState<List<Critique>?>, title: String, subTitle: String,
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
                        title = "Add",
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
                            title = item.projectTitle,
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
                            title = item.projectTitle,
                            bubbleText = "${item.comments.size} comments",
                            icon = Icons.Default.Edit,
                            isFirstItemInCarousel = false,
                            isLastItemInCarousel = index == (it.size-1),
                            onClick = {navController.navigate("$dbName/$index")})
                    }
                }
            }
        }
    } ?: run {
        Text("Loading...", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun PosistivitySection(critiques: MutableState<List<RequestPositivity>?>, title: String, subTitle: String,
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
                        title = "Add",
                        wordCount = "",
                        backgroundColour = MaterialTheme.colorScheme.background,
                        textColour = MaterialTheme.colorScheme.onBackground,
                        icon = Icons.Default.Add,
                        size = 80.dp,
                        onClick = onCreate
                    )
                }
                itemsIndexed(it) {index, item ->
                    PositivityTileButton(
                        bubbleText = "${item.comments.size} comments",
                        isLastItemInCarousel = index == (it.size-1),
                        onClick = { navController.navigate("$dbName/$index") })
                }
            }
        }
    } ?: run {
        Text("Loading...", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun PositivityTileButton(
    bubbleText: String,
    isLastItemInCarousel: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(end = if (isLastItemInCarousel) 8.dp else 0.dp)
            .size(200.dp, 80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color(0xFFf6f2ca))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Just Positive Vibes", maxLines = 2, overflow = TextOverflow.Ellipsis,
            color = Color.Black
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Row {
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = bubbleText,
                style = AppTypography.labelSmall,
                color = Color.Black
            )
        }
    }
}