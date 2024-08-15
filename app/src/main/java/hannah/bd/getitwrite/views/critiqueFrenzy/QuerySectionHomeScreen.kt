package hannah.bd.getitwrite.views.critiqueFrenzy

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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.theme.AppTypography
import hannah.bd.getitwrite.views.components.HomePageTileButton
import hannah.bd.getitwrite.views.components.SquareTileButton
import hannah.bd.getitwrite.views.components.TitleAndSubText

@Composable
fun QuickQueryCritique(requests: MutableState<List<RequestCritique>?>, navController: NavController) {
    if (requests.value?.isNotEmpty() == true) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            TitleAndSubText(
                "Quick Query Critique",
                "Query letters critiques.",
                colorScheme.onSurface
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
//                item {
//                    SquareTileButton(
//                        modifier = Modifier.padding(start = 8.dp),
//                        title = "Add your own.",
//                        wordCount = "",
//                        backgroundColour = MaterialTheme.colorScheme.secondaryContainer,
//                        textColour = MaterialTheme.colorScheme.onSecondaryContainer,
//                        icon = Icons.Default.Add,
//                        size = 150.dp,
//                        onClick = onCreate
//                    )
//                }
                itemsIndexed(requests.value!!) { index, item ->
                    HomePageTileButton(
                        title = item.genres.joinToString(),
                        bubbleText = "Query",
                        icon = Icons.Default.Email,
                        isFirstItemInCarousel = index == 0,
                        onClick = { navController.navigate("queries/$index") }
                    )
                }
                item {
                    SquareTileButton(
                        modifier = Modifier.padding(end = 8.dp),
                        title = "View more.",
                        wordCount = "",
                        backgroundColour = colorScheme.background,
                        textColour = colorScheme.onBackground,
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