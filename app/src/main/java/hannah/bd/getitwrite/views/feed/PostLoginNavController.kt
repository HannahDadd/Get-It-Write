package hannah.bd.getitwrite.views.feed

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hannah.bd.getitwrite.AppActions
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.profile.EditProfileView
import hannah.bd.getitwrite.views.profile.ProfileView
import hannah.bd.getitwrite.views.settings.SettingsScreen
import com.google.firebase.auth.FirebaseAuth
import hannah.bd.getitwrite.theme.AppTypography
import hannah.bd.getitwrite.views.critiqueFrenzy.getCritiques
import hannah.bd.getitwrite.views.messages.getUser

@Composable
fun PostLoginNavController(logoutNavController: NavHostController, auth: FirebaseAuth) {
    var user = remember { mutableStateOf<User?>(null) }
    val navController = rememberNavController()
    val actions = remember(navController) { AppActions(navController) }

    LaunchedEffect(Unit) {
        getUser(auth.uid.toString(),
            onSuccess = { user.value = it },
            onError = { exception -> }
        )
    }
    NavHost(
        navController = navController,
        startDestination = "feed",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
        modifier = Modifier.fillMaxSize()
    ) {
        composable("feed") {
            user.value?.let {
                FeedNavHost(user = it, logoutNavController, navController)
            } ?: run {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                ) {
                    Text(
                        "You'll get there,\n one word at a time.",
                        textAlign = TextAlign.Center,
                        style = AppTypography.titleLarge
                    )
                }
            }
        }
        composable("profile") {
            user.value?.let {
                ProfileView(navController = navController, ownProfile = true,
                    user = it, navigateUp = actions.navigateUp)
            }
        }
        composable("editProfile") {
            user.value?.let {
                EditProfileView(user = it, navigateUp = actions.navigateUp)
            }
        }
        composable("settings") {
            SettingsScreen(logoutNavController, navigateUp = actions.navigateUp)
        }
        composable("resetEmail") {
            SettingsScreen(logoutNavController, navigateUp = actions.navigateUp)
        }
    }
}