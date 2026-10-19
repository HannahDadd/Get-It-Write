package hannah.bd.getitwrite.views.homepage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hannah.bd.getitwrite.modals.AppDatabase
import hannah.bd.getitwrite.modals.UserPreferences
import hannah.bd.getitwrite.views.graphs.GraphForWriter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationPage(modifier: Modifier = Modifier,
                   startTwentyMinsActivity: () -> Unit,
                   startFortyMinsActivity: () -> Unit,
                   startSixtyMinsActivity: () -> Unit,
                   db: AppDatabase?,
                   preferences: UserPreferences,
                   backToLibrary: () -> Unit,
                   hostNavController: NavController) {
    val navController = rememberNavController()
    val startDestination = Destination.HOME
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                Destination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.route)
                            selectedDestination = index
                        },
                        icon = {
                            Icon(
                                destination.icon,
                                contentDescription = destination.contentDescription
                            )
                        },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { contentPadding ->
        AppNavHost(
            navController,
            startDestination,
            modifier = Modifier.padding(contentPadding),
            startTwentyMinsActivity = startTwentyMinsActivity,
            startFortyMinsActivity = startFortyMinsActivity,
            startSixtyMinsActivity = startSixtyMinsActivity,
            db,
            preferences,
            backToLibrary,
            hostNavController
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier,
    startTwentyMinsActivity: () -> Unit,
    startFortyMinsActivity: () -> Unit,
    startSixtyMinsActivity: () -> Unit,
    db: AppDatabase?,
    preferences: UserPreferences,
    backToLibrary: () -> Unit,
    hostNavController: NavController
) {

    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.HOME ->
                        growYourLibraryHomepage(
                            hostNavController,
                            userPreferences = preferences,
                            startTwentyMinsActivity = startTwentyMinsActivity,
                            startFortyMinsActivity = startFortyMinsActivity,
                            startSixtyMinsActivity = startSixtyMinsActivity,
                        )
                    Destination.STATS ->
                        GraphForWriter(
                            db,
                            userPreferences = preferences,
                        )
                    Destination.BACK ->
                    { backToLibrary() }
                }
            }
        }
    }
}

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    HOME("grow_screen", "Home", Icons.Default.Home, "grow_screen"),
    STATS("stats", "Stats", Icons.Default.Star, "Stats"),
    BACK("back", "Back to Library", Icons.Default.ArrowBack, "Back to Library")
}