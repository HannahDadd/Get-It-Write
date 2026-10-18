package hannah.bd.getitwrite.views.homepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.modals.UserPreferences
import hannah.bd.getitwrite.views.notifications.DailyReminderButton
import hannah.bd.getitwrite.views.sprints.SprintCarousel
import hannah.bd.getitwrite.views.sprints.SprintDurations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun growYourLibraryHomepage(
    navController: NavController,
    toLibrary: () -> Unit,
    userPreferences: UserPreferences,
//    hasPermission: Boolean,
//    canPostPromoted: Boolean,
    startTwentyMinsActivity: () -> Unit,
    startFortyMinsActivity: () -> Unit,
    startSixtyMinsActivity: () -> Unit,
) {
    val wordsWritten by userPreferences
        .wordsWritten
        .collectAsState(initial = 0)

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            item {
                Row {
                    Spacer(modifier = Modifier.weight(1.0f))
                    Button(onClick = {
                        toLibrary()
                    }) {
                        Text("Back to Library")
                    }
                }
            }
            item {
                Text(
                    "Welcome, Writer!",
                    fontFamily = FontFamily(Font(R.font.dynapuff))
                )
            }
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Words written in the app: ${ wordsWritten }",
                            fontFamily = FontFamily(Font(R.font.bellefairregularfont)))
                    Text("Library completes at 80k words.",
                        fontFamily = FontFamily(Font(R.font.bellefairregularfont)),)
                }
            }
            item {
                SprintCarousel { sprintDurations ->
                    when (sprintDurations) {
                        SprintDurations.FIVE_SECS -> {
                            navController.navigate("sprint5")
                        }
                        SprintDurations.TWENTY_MINS -> {
                            startTwentyMinsActivity()
                            navController.navigate("sprint20")
                        }
                        SprintDurations.FORTY_MINS -> {
                            startFortyMinsActivity
                            navController.navigate("sprint40")
                        }
                        SprintDurations.ONE_HOUR -> {
                            startSixtyMinsActivity()
                            navController.navigate("sprint60")
                        }
                    }
                }
            }
            item {
                Column(modifier = Modifier.padding(8.dp)) {
                    DailyReminderButton()
                }
            }
        }
    }
}