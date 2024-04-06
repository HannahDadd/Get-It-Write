package com.example.getitwrite.views.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.getitwrite.AppActions
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.MainView
import com.example.getitwrite.views.messages.ShowMessages
import com.example.getitwrite.views.profile.EditProfileView
import com.example.getitwrite.views.profile.ProfileView
import com.example.getitwrite.views.proposals.ProposalDetails
import com.example.getitwrite.views.proposals.ProposalsViewModel
import com.example.getitwrite.views.settings.SettingsScreen
import com.example.getitwrite.views.toCritique.ToCritiqueDetailedView
import com.example.getitwrite.views.toCritique.ToCritiqueViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@Composable
fun PostLoginNavController(viewModel: MainViewModel, logoutNavController: NavHostController) {
    val user by viewModel.user.collectAsState(initial = User())
    val navController = rememberNavController()
    val proposals by ProposalsViewModel().proposalsFlow.collectAsState(initial = emptyList())
    val toCritiques by ToCritiqueViewModel(user).toCritiques.collectAsState(initial = emptyList())
    val actions = remember(navController) { AppActions(navController) }
    NavHost(
        navController = navController,
        startDestination = "feed"
    ) {
        composable("feed") {
            MainView(logoutNavController, toCritiques = toCritiques, navController = navController, proposals = proposals,
                selectProposal = actions.selectedProposal, selectChat = actions.selectChat, user = user,
                selectCritiqueRequest = actions.selectCritiqueRequest)
        }
        composable("profile") {
            ProfileView(navController = navController, ownProfile = true, user = user, navigateUp = actions.navigateUp)
        }
        composable("editProfile") {
            EditProfileView(user = user, navigateUp = actions.navigateUp)
        }
        composable("settings") {
            SettingsScreen(logoutNavController, navigateUp = actions.navigateUp)
        }
        composable("resetEmail") {
            SettingsScreen(logoutNavController, navigateUp = actions.navigateUp)
        }
        composable(
            "chatDetails/{chat_id}/{otherUserName}/{otherUserId}",
            arguments = listOf(
                navArgument("chat_id") {
                    type = NavType.StringType
                },
                navArgument("otherUserName") {
                    type = NavType.StringType
                },
                navArgument("otherUserId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ShowMessages(
                chatId = arguments.getString("chat_id")!!,
                user = user,
                user2Name = arguments.getString("otherUserName")!!,
                user2Id = arguments.getString("otherUserId")!!,
                proposals = proposals,
                backStackEntry = backStackEntry,
                navigateUp = actions.navigateUp
            )
        }
        composable(
            "critiqueRequest/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val id = arguments.getString("id")
            val toCritique = toCritiques.filter { it.id == id }.get(0)
            ToCritiqueDetailedView(user, toCritique, actions.navigateUp)
//            ToCritiqueDetailedView(user, toCritique) {
//                toCritiques.minus(toCritique)
//                actions.navigateUp()
//            }
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
                user = user,
                navController = navController,
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
            emit(User())
        }
    }
}