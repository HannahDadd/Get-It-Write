package com.example.getitwrite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.getitwrite.ui.theme.GetItWriteTheme
import com.example.getitwrite.views.MainView
import com.example.getitwrite.views.MainViewModel
import com.example.getitwrite.views.login.ShowCreateAccountView
import com.example.getitwrite.views.login.ShowLogin
import com.example.getitwrite.views.login.ShowSignUp
import com.example.getitwrite.views.proposals.ProposalDetails
import com.example.getitwrite.views.proposals.ProposalsViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private var destination = "login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            val navController = rememberNavController()
            GetItWriteTheme {
                NavigationComponent(navController)
            }
        }
    }

    @Composable
    fun NavigationComponent(navController: NavHostController) {
        val proposals by ProposalsViewModel().proposalsFlow.collectAsState(initial = emptyList())
        val actions = remember(navController) { AppActions(navController) }
        NavHost(
            navController = navController,
            startDestination = destination
        ) {
            composable("login") {
                ShowLogin(navController, auth)
            }
            composable("feed") {
                MainView(MainViewModel(auth), proposals = proposals, selectProposal = actions.selectedProposal)
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
            composable("signup") {
                ShowSignUp(navController = navController, auth)
            }
            composable("createAccount") {
                ShowCreateAccountView(navController, auth)
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            destination = "feed"
        }
    }
}

object Destinations {
    const val proposalsList = "proposals"
    const val proposalTwo = "proposalTwo"
    const val proposal_id = "proposal_id"
    const val chatsList = "chats"
    const val chat_id = "chat_id"
}

class AppActions(
    navController: NavHostController
) {
    val selectedProposal: (String) -> Unit = { id: String ->
        navController.navigate("details/${id}")
    }
    val selectChat: (String) -> Unit = { id: String ->
        navController.navigate("${Destinations.chatsList}/${id}")
    }
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}