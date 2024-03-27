package com.example.getitwrite.views.proposals

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.getitwrite.AppActions
import com.example.getitwrite.Destinations
import com.example.getitwrite.modals.Proposal

@Composable
fun ProposalsScreen(proposalViewModel: ProposalsViewModel = viewModel()) {
    val proposals by proposalViewModel.proposalsFlow.collectAsState(initial = emptyList())
    val errorString = remember { mutableStateOf("") }
    val navController = rememberNavController()
    val actions = remember(navController) { AppActions(navController) }

    NavHost(
        navController = navController,
        startDestination = Destinations.proposalsList
    ) {
        composable(Destinations.proposalsList) {
            ProposalsFeed(proposals = proposals, selectProposal = actions.selectedProposal)
        }
        composable(
            "details/{proposal_id}",
//            "proposalTwo?proposalId={ErBRWcB1cqF8U2GaJQ0U}",
            arguments = listOf(
                navArgument("proposal_id") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ProposalDetails(
                proposalId = arguments.getString("proposal_id")!!,
                proposals = proposals,
                navigateUp = actions.navigateUp
            )
        }
    }
}