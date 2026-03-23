package hannah.bd.getitwrite.views.badges

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.modals.Badge

@Composable
fun BadgePage() {
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {

        item {
            Text(
                text = "Achievements",
                fontSize = 34.sp,
                fontFamily = FontFamily(Font(R.font.abrilfatfaceregular)),
                modifier = Modifier.padding(16.dp)
            )
        }

        // Finish Book Section
        item {
            BadgeSectionHeader("Finish a book")
            FinishBookPromo(badge = Badge.BOOK_GOAL)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Sprint Section
        item {
            BadgeSectionHeader("Sprints completed")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SprintBadge(Badge.TWENTY_SPRINT, screenWidth * 0.29f)
                SprintBadge(Badge.FORTY_SPRINT, screenWidth * 0.29f)
                SprintBadge(Badge.HOUR_SPRINT, screenWidth * 0.29f)
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Quick Words
        item {
            BadgeSectionHeader("Quick words")
        }
        items(listOf(
            Badge.QUICK_WORDS_250,
            Badge.QUICK_WORDS_500,
            Badge.QUICK_WORDS_1000,
            Badge.QUICK_WORDS_2000,
            Badge.QUICK_WORDS_5000
        )) {
            BadgePromo(it)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Word Nerd
        item {
            BadgeSectionHeader("Word nerd")
        }
        items(listOf(
            Badge.WORD_NERD_200,
            Badge.WORD_NERD_500,
            Badge.WORD_NERD_1000,
            Badge.WORD_NERD_10000,
            Badge.WORD_NERD_20000,
            Badge.WORD_NERD_50000
        )) {
            BadgePromo(it)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Streak Freak
        item {
            BadgeSectionHeader("Streak freak")
        }
        items(listOf(
            Badge.STREAK_FREAK_2,
            Badge.STREAK_FREAK_7,
            Badge.STREAK_FREAK_14,
            Badge.STREAK_FREAK_31,
            Badge.STREAK_FREAK_100
        )) {
            BadgePromo(it)
        }
    }
}