package com.example.getitwrite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.getitwrite.ui.theme.GetItWriteTheme
import com.example.getitwrite.views.feed.MainViewModel
import com.example.getitwrite.views.feed.PostLoginNavController
import com.example.getitwrite.views.login.ShowCreateAccountView
import com.example.getitwrite.views.login.ShowLogin
import com.example.getitwrite.views.login.ShowSignUp
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
        NavHost(
            navController = navController,
            startDestination = destination
        ) {
            composable("login") {
                ShowLogin(navController, auth)
            }
            composable("feed") {
                PostLoginNavController(auth, MainViewModel(auth), navController)
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

class AppActions(
    navController: NavHostController
) {
    val selectedProposal: (String) -> Unit = { id: String ->
        navController.navigate("details/${id}")
    }
    val selectChat: (String) -> Unit = { id: String ->
        navController.navigate("chatDetails/${id}")
    }
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}