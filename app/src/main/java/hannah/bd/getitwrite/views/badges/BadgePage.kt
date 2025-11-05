package hannah.bd.getitwrite.views.badges

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.modals.BadgeTitles
import hannah.bd.getitwrite.modals.popUpButton
import hannah.bd.getitwrite.modals.popUpText
import hannah.bd.getitwrite.views.components.HeadlineAndSubtitle

@Composable
fun BadgePage() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        HeadlineAndSubtitle(
            title = "Celebrate your Wins",
            subtitle = "Writing games to keep you on top form."
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            item {
                BadgeSection(
                    title = "Messing",
                    badges = listOf(
                        BadgeTitles.PromptsUsed,
                        BadgeTitles.WordsLearnt
                    ),
                    context = context
                )
            }
            item {
                BadgeSection(
                    title = "Writing",
                    badges = listOf(BadgeTitles.Projects),
                    context = context
                )
            }
            item {
                BadgeSection(
                    title = "Querying",
                    badges = listOf(
                        BadgeTitles.QueriesSent,
                        BadgeTitles.FullRequest
                    ),
                    context = context
                )
            }
            item {
                BadgeSection(
                    title = "Authoring",
                    badges = listOf(BadgeTitles.BooksPublished),
                    context = context
                )
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(25.dp)
                            .background(MaterialTheme.colorScheme.secondary, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("tap to increment, double tap to reset")
                }
            }
        }
    }
}

@Composable
private fun BadgeSection(title: String, badges: List<BadgeTitles>, context: Context) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            badges.forEach { badgeTitle ->
                val text = popUpText(badgeTitle)
                val showPopup = popUpButton(badgeTitle)
                BadgeView(
                    title = badgeTitle.rawValue,
                    onTapText = text,
                    shouldShowPopup = showPopup,
                    context = context
                )
            }
        }
    }
}
