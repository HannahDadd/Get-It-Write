package hannah.bd.getitwrite.views.homepage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hannah.bd.getitwrite.AppMainPage
import hannah.bd.getitwrite.modals.AppDatabase
import hannah.bd.getitwrite.modals.UserPreferences

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
                preferences)
        }
    }
}
