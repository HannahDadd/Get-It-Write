package hannah.bd.getitwrite.views.homepage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hannah.bd.getitwrite.AppMainPage
import hannah.bd.getitwrite.modals.AppDatabase
import hannah.bd.getitwrite.modals.UserPreferences
import hannah.bd.getitwrite.views.settings.HowItWorks
import hannah.bd.getitwrite.views.settings.MeetTheFamily
import hannah.bd.getitwrite.views.settings.OurOtherApps
import hannah.bd.getitwrite.views.settings.SupportUs
import hannah.bd.getitwrite.views.settings.TsAndCsView
import hannah.bd.getitwrite.views.settings.privacyPolicyView
import hannah.bd.getitwrite.views.sprints.SprintStack
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    object Main: Screen("main_screen")
    object HomePage: Screen("home_screen")
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
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    var preferences = UserPreferences(LocalContext.current)

    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(route = Screen.Main.route) {
            AppMainPage(
                navController = navController,
                preferences,
                db)
        }
        composable(route = Screen.HomePage.route) {
            NavigationPage(
                modifier,
                startTwentyMinsActivity,
                startFortyMinsActivity,
                startSixtyMinsActivity,
                db,
                preferences,
                { navController.navigate(Screen.Main.route) },
                navController
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
