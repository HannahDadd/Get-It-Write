package hannah.bd.getitwrite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.theme.GetItWriteTheme
import hannah.bd.getitwrite.views.feed.PostLoginNavController
import hannah.bd.getitwrite.views.login.ShowCreateAccountView
import hannah.bd.getitwrite.views.login.ShowLogin
import hannah.bd.getitwrite.views.login.ShowSignUp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import hannah.bd.getitwrite.views.login.OnBoardingPageOne
import hannah.bd.getitwrite.views.login.OnBoardingPageTwo
import hannah.bd.getitwrite.views.login.ShowOpeningPage
import hannah.bd.getitwrite.views.toCritique.ToCritiqueDetailedView

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private var destination = "login"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            val navController = rememberNavController()
            GetItWriteTheme {
                Surface(tonalElevation = 5.dp) {
                    NavigationComponent(navController)
                }
            }
        }
    }

    @Composable
    fun NavigationComponent(navController: NavHostController) {
        NavHost(
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
            navController = navController,
            startDestination = destination,
            modifier = Modifier.fillMaxSize()
        ) {
            composable("login") {
                ShowLogin(navController, auth)
            }
            composable("feed") {
                PostLoginNavController(navController, auth)
            }
            composable("signup") {
                ShowSignUp(navController = navController, auth)
            }
            composable("createAccount") {
                ShowCreateAccountView(navController, auth)
            }
            composable(
                "onboardingPageOne/{name}",
                arguments = listOf(navArgument("name") { type = NavType.StringType })
            ) { backStackEntry ->
                requireNotNull(backStackEntry.arguments).getString("name")?.let {
                    OnBoardingPageOne(navController, it, auth)
                }
            }
            composable("onboardingPageTwo") {
                    OnBoardingPageTwo(navController)
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
    val selectedProposal: (Proposal) -> Unit = { proposal: Proposal ->
        navController.navigate("details/${proposal.id}")
    }
    val selectCritiqueRequest: (String) -> Unit = {
        navController.navigate("critiqueRequest/${it}")
    }
    val selectCritiqued: (String) -> Unit = {
        navController.navigate("critiqued/${it}")
    }
    val selectQuestion: (String) -> Unit = {
        navController.navigate("questions/${it}")
    }
    val selectFrenzy: (String) -> Unit = {
        navController.navigate("critiqueFrenzy/${it}")
    }
    val selectChat: (String, String, String) -> Unit = { id: String, user2Name: String, user2Id: String ->
        navController.navigate("chatDetails/${id}/${user2Name}/${user2Id}")
    }
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}