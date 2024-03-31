package com.example.getitwrite.views.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.getitwrite.AppActions
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.MainView
import com.example.getitwrite.views.proposals.ProposalDetails
import com.example.getitwrite.views.proposals.ProposalsViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@Composable
fun PostLoginNavController(viewModel: MainViewModel) {
    val user by viewModel.user.collectAsState(
        initial = User(
            id = "1",
            displayName = "",
            bio = "",
            writing = "",
            critiqueStyle = "",
            authors = ArrayList(),
            writingGenres = ArrayList(),
            colour = 1
        )
    )
    val navController = rememberNavController()
    val proposals by ProposalsViewModel().proposalsFlow.collectAsState(initial = emptyList())
    val actions = remember(navController) { AppActions(navController) }
    NavHost(
        navController = navController,
        startDestination = "feed"
    ) {
        composable("feed") {
            MainView(proposals = proposals, selectProposal = actions.selectedProposal, user = user)
        }
        composable(
            "details/{proposal_id}",
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

class MainViewModel(auth: FirebaseAuth) : ViewModel() {
    val user = flow {
        val doc = Firebase.firestore.collection("users")
            .document(auth.uid.toString())
            .get().await()
        doc.data?.let {
            emit(User(id = doc.id, data = it))
        } ?: run {
            emit(
                User(
                    id = "1",
                    displayName = "",
                    bio = "",
                    writing = "",
                    critiqueStyle = "",
                    authors = ArrayList(),
                    writingGenres = ArrayList(),
                    colour = 1
                )
            )
        }
    }
}