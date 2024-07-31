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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProposalsFeed(user: User, proposals: List<Proposal>, selectProposal: (Proposal) -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add your own!") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = { showBottomSheet = true }
            )
        }
    ) { innerPadding ->
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                MakeProposalView(user) {
                    showBottomSheet = false
                }
            }
        }
        if (proposals.isEmpty()) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                Text(text = "Loading...")
            }
        } else {
            LazyColumn(Modifier.padding(innerPadding)) {
                items(proposals) { proposal ->
                    ProposalView(proposal, selectProposal)
                }
            }
        }
    }
}

@Composable
fun GenreFeed(navController: NavHostController, proposals: List<Proposal>) {
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

@Composable
fun ProposalNavHost(tag: String) {
    val navController = rememberNavController()
    val proposals by ProposalsViewModel(genre = tag).proposalsFlow.collectAsState(initial = emptyList())
    NavHost(navController = navController, startDestination = "genreFeed") {
        composable("genreFeed") {
            GenreFeed(navController, proposals)
        }

        composable(
            "detail/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val id = arguments.getString("id")
            //CritiquedDetailedView(critiqued, actions.navigateUp)
            ProposalView(proposal = proposals.filter { it.id == id }.get(0)) {

            }
        }
    }
}

@Composable
fun ProposalView(proposal: Proposal, selectProposal: (Proposal) -> Unit) {
//    DetailHeader(title = proposal.title, navigateUp = navigateUp)
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