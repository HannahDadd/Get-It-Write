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
import hannah.bd.getitwrite.views.streak.StreakCTA

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomepagePage(activity: Activity, navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()
        .padding(16.dp)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            item {
                HeadlineAndSubtitle(
                    title = "Hey, future best selling author",
                    subtitle = "Let's get that manuscript written."
                )
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                    StreakCTA(onAddWords = { navController.navigate("streak") })
                    CommitmentCTA(activity)
                    SprintCTA(onStartSprint = { navController.navigate("sprint") })
                    WordOfTheDayCard()
                }
            }
        }
    }
}