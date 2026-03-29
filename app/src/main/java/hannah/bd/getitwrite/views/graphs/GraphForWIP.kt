package hannah.bd.getitwrite.views.graphs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.modals.AppDatabase
import hannah.bd.getitwrite.modals.Stat
import hannah.bd.getitwrite.modals.WIP
import hannah.bd.getitwrite.views.wips.WIPView

@Composable
fun GraphForWIP(db: AppDatabase?, wip: WIP) {
    var stats by remember { mutableStateOf(listOf<Stat>()) }

    LaunchedEffect(Unit) {
        db?.let {
            stats = db.statDao().getStatsForWIPId(wip.id)
        }
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text("Your statistics for ${wip.title}",
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = FontFamily(Font(R.font.bellefairregularfont)))
        WIPView(wip) {}

        if (stats.size < 2) {
            Text("Not enough statistics to show for this project yet! Keep writing!",
                fontFamily = FontFamily(Font(R.font.bellefairregularfont)))
        } else {
            val totalWords = stats.sumOf { it.wordsWritten }
            Text("You've written a total of $totalWords words across ${stats.size} sprints!",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.bellefairregularfont)))
        }
    }
}
