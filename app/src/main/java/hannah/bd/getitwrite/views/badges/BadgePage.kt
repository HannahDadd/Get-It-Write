package hannah.bd.getitwrite.views.badges

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hannah.bd.getitwrite.views.components.HeadlineAndSubtitle

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
                fontFamily = FontFamily(Font(R.font.abril_fatface_regular)),
                modifier = Modifier.padding(16.dp)
            )
        }

        // Finish Book Section
        item {
            SectionHeader("FINISH A BOOK")
            FinishBookPromo(badge = Badge.BookGoal)
        }

        // Sprint Section
        item {
            SectionHeader("SPRINTS COMPLETED")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SprintBadge(Badge.TwentySprint, screenWidth * 0.29f)
                SprintBadge(Badge.FortySprint, screenWidth * 0.29f)
                SprintBadge(Badge.HourSprint, screenWidth * 0.29f)
            }
        }

        // Quick Words
        item {
            SectionHeader("QUICK WORDS")
        }
        items(listOf(
            Badge.QuickWords250,
            Badge.QuickWords500,
            Badge.QuickWords1000,
            Badge.QuickWords2000,
            Badge.QuickWords5000
        )) {
            BadgePromo(it)
        }

        // Word Nerd
        item {
            SectionHeader("WORD NERD")
        }
        items(listOf(
            Badge.WordNerd200,
            Badge.WordNerd500,
            Badge.WordNerd1000,
            Badge.WordNerd10000,
            Badge.WordNerd20000,
            Badge.WordNerd50000
        )) {
            BadgePromo(it)
        }

        // Streak Freak
        item {
            SectionHeader("STREAK FREAK")
        }
        items(listOf(
            Badge.StreakFreak2,
            Badge.StreakFreak7,
            Badge.StreakFreak14,
            Badge.StreakFreak31,
            Badge.StreakFreak100
        )) {
            BadgePromo(it)
        }
    }
}