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
import com.example.getitwrite.views.login.showCreateAccountView
import com.example.getitwrite.views.login.showLogin
import com.example.getitwrite.views.login.showSignUp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        FirebaseSession session = FirebaseSession()
//        auth = FirebaseAuth.getInstance()
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
            startDestination = "login"
        ) {
            composable("login") {
                showLogin(navController)
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
        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
    }
}