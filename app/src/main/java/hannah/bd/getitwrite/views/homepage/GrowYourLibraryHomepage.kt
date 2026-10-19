package hannah.bd.getitwrite.views.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.modals.UserPreferences
import hannah.bd.getitwrite.views.settings.settingsView
import hannah.bd.getitwrite.views.sprints.SprintCarousel
import hannah.bd.getitwrite.views.sprints.SprintDurations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun growYourLibraryHomepage(
    navController: NavController,
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

    var showSettings by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            item {
                Column(modifier = Modifier.padding(16.dp)) {

                    Row {
                        Text(
                            "Welcome, Writer!",
                            fontFamily = FontFamily(Font(R.font.dynapuff)),
                            fontSize = 30.sp
                        )
                        Spacer(modifier = Modifier.weight(1.0f))
                        Image(
                            painter = painterResource(R.drawable.broom_btn),
                            contentDescription = "Menu",
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    showSettings = true
                                }
                        )
                    }
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
//            item {
//                Column(modifier = Modifier.padding(8.dp)) {
//                    DailyReminderButton()
//                }
//            }
        }


        if (showSettings) {

            ModalBottomSheet(
                onDismissRequest = {
                    showSettings = false
                }
            ) {
                settingsView(navController)
            }
        }
    }
}