package hannah.bd.shelfify.views.homepage

import android.R.attr.type
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import hannah.bd.getitwrite.views.sprints.SprintStack
import hannah.bd.shelfify.AppMainPage
import hannah.bd.shelfify.modals.AppDatabase
import hannah.bd.shelfify.modals.UserPreferences
import hannah.bd.shelfify.views.graphs.GraphForWriter
import hannah.bd.shelfify.views.settings.HowItWorks
import hannah.bd.shelfify.views.settings.MeetTheFamily
import hannah.bd.shelfify.views.settings.OurOtherApps
import hannah.bd.shelfify.views.settings.SupportUs
import hannah.bd.shelfify.views.settings.TsAndCsView
import hannah.bd.shelfify.views.settings.privacyPolicyView
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    object Main: Screen("main_screen")
    object Grow: Screen("grow_screen")
    object Stats: Screen("stats_screen")
    object Final: Screen("final_screen")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationStack(
    modifier: Modifier,
//    hasPermission: Boolean,
//    canPostPromoted: Boolean,
//    onRequestPermission: () -> Unit,
    startTwentyMinsActivity: () -> Unit,
    startFortyMinsActivity: () -> Unit,
    startSixtyMinsActivity: () -> Unit,
    db: AppDatabase?,
    ) {
    val navController = rememberNavController()
    var preferences = UserPreferences(LocalContext.current)
    val scope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(route = Screen.Main.route) {
            AppMainPage(
                navController = navController,
                preferences,
                db)
        }
        composable(
            route = Screen.Grow.route
        ) {
            growYourLibraryHomepage(
                navController,
                { navController.popBackStack() },
                userPreferences = preferences,
//                hasPermission = hasPermission,
//                canPostPromoted = canPostPromoted,
                startTwentyMinsActivity = startTwentyMinsActivity,
                startFortyMinsActivity = startFortyMinsActivity,
                startSixtyMinsActivity = startSixtyMinsActivity,
            )
        }
        composable(
            route = Screen.Final.route
        ) {
            crystalBallLibrary({navController.popBackStack()})
        }
        composable(
            route = Screen.Stats.route
        ) {
            GraphForWriter(
                navigateBack = { navController.popBackStack() },
                db,
                userPreferences = preferences,
            )
        }
        composable("sprint5") {
            SprintStack(
                db,
                onFinish = {
                    scope.launch {
                        preferences.updateWordCount(it)
                    }
                    navController.popBackStack()
                }, (5)
            )
        }
        composable("sprint20") {
            SprintStack(
                db,
                onFinish = {
                    scope.launch {
                        preferences.updateWordCount(it)
                    }
                    navController.popBackStack()
                           }, (20*60)
            )
        }
        composable("sprint40") {
            SprintStack(
                db,
                onFinish = {
                    scope.launch {
                        preferences.updateWordCount(it)
                    }
                    navController.popBackStack()
                }, (40*60)
            )
        }
        composable("sprint60") {
            SprintStack(
                db,
                onFinish = {
                    scope.launch {
                        preferences.updateWordCount(it)
                    }
                    navController.popBackStack()
                }, (60*60)
            )
        }
        composable("meetTheFamily") {
            MeetTheFamily() { navController.popBackStack() }
        }
        composable("tsAndCs") {
            TsAndCsView() { navController.popBackStack() }
        }
        composable("privacyPolicy") {
            privacyPolicyView() { navController.popBackStack() }
        }
        composable("supportUs") {
            SupportUs { navController.popBackStack() }
        }
        composable("howItWorks") {
            HowItWorks() { navController.popBackStack() }
        }
        composable("otherApps") {
            OurOtherApps() { navController.popBackStack() }
        }
    }
}
