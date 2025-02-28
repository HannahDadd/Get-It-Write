package hannah.bd.getitwrite.views.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.theme.AppTypography
import hannah.bd.getitwrite.views.forum.RectangleTileButtonNoDate

@Composable
fun CritiquedWord(navController: NavController, critiqued: MutableState<List<Critique>?>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
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
                                RectangleTileButtonWithBubble(
                                    title = c.projectTitle,
                                    backgroundColour = MaterialTheme.colorScheme.background,
                                    textColour = MaterialTheme.colorScheme.onBackground,
                                    bubbleText = "${c.comments.size} comments",
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
                                RectangleTileButtonWithBubble(
                                    title = c.projectTitle,
                                    backgroundColour = MaterialTheme.colorScheme.background,
                                    textColour = MaterialTheme.colorScheme.onBackground,
                                    bubbleText = "${c.comments.size} comments",
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



@Composable
fun RectangleTileButtonWithBubble(
    title: String,
    backgroundColour: Color,
    textColour: Color,
    bubbleText: String,
    onClick: () -> Unit
) {
    val primary = MaterialTheme.colorScheme.primary
    Column(
        modifier = Modifier
            .height(90.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = backgroundColour)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = title, maxLines = 1, overflow = TextOverflow.Ellipsis,
            color = textColour
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Row {
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                modifier = Modifier
                    .background(
                        color = primary,
                        shape = RoundedCornerShape(8.dp)
                    ).padding(4.dp),
                text = bubbleText,
                style = AppTypography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}