package hannah.bd.getitwrite.views.pages

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hannah.bd.getitwrite.views.commitments.CommitmentCTA
import hannah.bd.getitwrite.views.components.HeadlineAndSubtitle
import hannah.bd.getitwrite.views.games.vocab.WordOfTheDayCard
import hannah.bd.getitwrite.views.sprints.SprintCTA
import hannah.bd.getitwrite.views.sprints.SprintCarousel
import hannah.bd.getitwrite.views.sprints.SprintDurations
import hannah.bd.getitwrite.views.streak.StreakCTA

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomepagePage(activity: Activity, navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    HeadlineAndSubtitle(
                        title = "Hey, future best selling author",
                        subtitle = "Let's get that manuscript written."
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    StreakCTA(onAddWords = { navController.navigate("streak") })
                }
            }
            item {
                SprintCarousel { sprintDurations ->
                    when(sprintDurations) {
                        SprintDurations.TWENTY_MINS -> navController.navigate("sprint20")
                        SprintDurations.FORTY_MINS -> navController.navigate("sprint40")
                        SprintDurations.ONE_HOUR -> navController.navigate("sprint60")
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    SprintCTA(onStartSprint = { navController.navigate("sprint20") })
                    CommitmentCTA(activity)
                    WordOfTheDayCard()
                }
            }
        }
    }
}