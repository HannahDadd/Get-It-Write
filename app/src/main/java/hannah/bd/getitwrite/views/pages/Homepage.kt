package hannah.bd.getitwrite.views.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import hannah.bd.getitwrite.modals.AppDatabase
import hannah.bd.getitwrite.views.commitments.CommitmentCTA
import hannah.bd.getitwrite.views.graphs.GraphForWriter
import hannah.bd.getitwrite.views.sprints.SprintCTA
import hannah.bd.getitwrite.views.wips.WIPsCTA

@Composable
fun HomepagePage(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                HeadlineAndSubtitle(
                    title = "Hey, future best selling author",
                    subtitle = "Let's get that manuscript written."
                )
            }
            item {
                StreakCTA(onClick = { navController.navigate("streak") })
                CommitmentCTA()
                SprintCTA(onClick = { navController.navigate("sprint") })
                WordOfTheDayCard()
            }
        }
    }
}


//@Composable
//fun VerticalContent(navController: NavHostController, paddingValues: PaddingValues, db: AppDatabase) {
//
//    Column(
//        modifier = Modifier.padding(paddingValues),
//        verticalArrangement = Arrangement.spacedBy(20.dp)
//    ) {
//        HorizontalDivider()
//        SprintCTA {
//            navController.navigate("sprint")
//        }
//        HorizontalDivider()
//        //CommitmentCTA()
//        HorizontalDivider()
//        WIPsCTA(db)
//        HorizontalDivider()
//        GraphForWriter(db)
//    }
//}

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun NavigationGraph(navController: NavHostController, paddingValues: PaddingValues) {
//    NavHost(navController, startDestination = "main") {
//        composable("main") { VerticalContent(navController, paddingValues) }
//        composable("sprint") { SprintStack(db) { navController.popBackStack() } }
//    }
//}