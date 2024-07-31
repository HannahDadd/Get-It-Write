package hannah.bd.getitwrite.views.proposals

import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.DetailHeader
import hannah.bd.getitwrite.views.components.TagCloud
import hannah.bd.getitwrite.views.toCritique.CritiquedDetailedView

@Composable
fun GenreFeed(navController: NavHostController, hostNavController: NavHostController, proposals: List<Proposal>, genre: String) {
    Column {
        DetailHeader(title = genre, navigateUp = { hostNavController.navigateUp() })
        if (proposals.isEmpty()) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                Text(text = "Loading...")
            }
        } else {
            LazyColumn() {
                items(proposals) { proposal ->
                    ProposalView(proposal, {
                        navController.navigate("proposal/${it.id}")
                    })
                }
            }
        }
    }
}

@Composable
fun ProposalNavHost(tag: String, hostNavController: NavHostController, user: User) {
    val navController = rememberNavController()
    val proposals by ProposalsViewModel(genre = tag).proposalsFlow.collectAsState(initial = emptyList())
    NavHost(navController = navController, startDestination = "genreFeed") {
        composable("genreFeed") {
            GenreFeed(navController, hostNavController, proposals, tag)
        }
        composable(
            "proposal/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val id = arguments.getString("id")
            ProposalDetails(proposal = proposals.filter { it.id == id }.get(0),
                user = user,
                navController = navController
            )
        }
    }
}

@Composable
fun ProposalView(proposal: Proposal, selectProposal: (Proposal) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(10.dp)
            .clickable { selectProposal(proposal) }) {
        Text(proposal.title, fontWeight = FontWeight.Bold)
        Text(proposal.blurb)
        Text(proposal.typeOfProject.joinToString(", "), fontWeight = FontWeight.Light)
        TagCloud(tags = proposal.genres, action = null)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            Text(
                text = DateUtils.getRelativeTimeSpanString(
                    (proposal.timestamp.seconds * 1000),
                    System.currentTimeMillis(),
                    DateUtils.DAY_IN_MILLIS
                ).toString(),
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = "${proposal.wordCount} words",
                fontWeight = FontWeight.Light
            )
        }
        Divider()
    }
}