package com.example.getitwrite.views.proposals

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
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.getitwrite.modals.Proposal
import com.example.getitwrite.views.components.TagCloud
import com.example.getitwrite.views.proposals.ProposalsDestinations.proposal_id

object ProposalsDestinations {
    const val proposalsList = "proposals"
    const val proposalDetail = "proposalId"
    const val proposal_id = "proposal_id"
}

@Composable
fun ProposalsScreen(proposalViewModel: ProposalsViewModel = viewModel()) {
    val proposals by proposalViewModel.proposalsFlow.collectAsState(initial = emptyList())
    val errorString = remember { mutableStateOf("") }
    val navController = rememberNavController()
    val actions = remember(navController) { AppActions(navController) }

    NavHost(
        navController = navController,
        startDestination = ProposalsDestinations.proposalsList
    ) {
        composable(ProposalsDestinations.proposalsList) {
            ProposalsFeed(proposals = proposals, selectProposal = actions.selected)
        }
        composable(
            "${ProposalsDestinations.proposalDetail}/${proposal_id}",
            arguments = listOf(
                navArgument(proposal_id) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ProposalDetails(
                proposalId = arguments.getString(proposal_id)!!,
                proposals = proposals,
                navigateUp = actions.navigateUp
            )
        }
    }
}

private class AppActions(
    navController: NavHostController
) {
    val selected: (String) -> Unit = { proposal_id: String ->
        navController.navigate("${ProposalsDestinations.proposalDetail}/${proposal_id}")
    }
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}

@Composable
fun ProposalsFeed(proposals: List<Proposal>, selectProposal: (String) -> Unit) {
    Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Add your own!") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                    onClick = {
                    }
                )
            }
    ) { innerPadding ->
        LazyColumn(Modifier.padding(innerPadding)) {
            items(proposals) { proposal ->
                ProposalView(proposal, selectProposal)
            }
        }
    }
}

@Composable
fun ProposalView(proposal: Proposal, selectProposal: (String) -> Unit) {
    Card(Modifier.clickable { selectProposal(proposal.id) }) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(10.dp)) {
            Text(proposal.title, fontWeight = FontWeight.Bold)
            Text(proposal.blurb)
            Text(proposal.typeOfProject.joinToString(", "), fontWeight = FontWeight.Light)
            TagCloud(tags = proposal.genres, action = null)
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding()) {
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
}