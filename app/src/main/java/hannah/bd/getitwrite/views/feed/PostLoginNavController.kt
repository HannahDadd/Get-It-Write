package hannah.bd.getitwrite.views.feed

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hannah.bd.getitwrite.AppActions
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.MainView
import hannah.bd.getitwrite.views.forum.QuestionDetailView
import hannah.bd.getitwrite.views.forum.QuestionsViewModel
import hannah.bd.getitwrite.views.messages.ShowMessages
import hannah.bd.getitwrite.views.profile.EditProfileView
import hannah.bd.getitwrite.views.profile.ProfileView
import hannah.bd.getitwrite.views.settings.SettingsScreen
import hannah.bd.getitwrite.views.toCritique.CritiquedDetailedView
import hannah.bd.getitwrite.views.toCritique.CritiquedFeed
import hannah.bd.getitwrite.views.toCritique.CritiquedViewModel
import hannah.bd.getitwrite.views.toCritique.ToCritiqueDetailedView
import hannah.bd.getitwrite.views.toCritique.ToCritiqueViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@Composable
fun PostLoginNavController(logoutNavController: NavHostController, auth: FirebaseAuth) {
    val user by MainViewModel(auth).user.collectAsState(initial = User(id = "ERROR"))
    val navController = rememberNavController()
    val actions = remember(navController) { AppActions(navController) }
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
            FeedNavHost(user = user)
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
            emit(User(id = "ERROR"))
        }
    }
}