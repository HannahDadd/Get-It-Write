package com.example.getitwrite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.getitwrite.ui.theme.GetItWriteTheme
import com.example.getitwrite.views.feed.ShowFeed
import com.example.getitwrite.views.login.ShowLogin
import com.example.getitwrite.views.login.showCreateAccountView
import com.example.getitwrite.views.login.showSignUp
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
                ShowFeed()
            }
            composable("signup") {
                showSignUp(navController = navController)
            }
            composable("createAccount") {
                showCreateAccountView()
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