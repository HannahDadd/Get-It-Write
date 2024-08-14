package hannah.bd.getitwrite.views.feed

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.modals.Question
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.RequestPositivity
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.account.AccountView
import hannah.bd.getitwrite.views.proposals.SearchNavHost

@Composable
fun ShowBottomNav(user: User, questions: MutableState<List<Question>?>,
                  toCritiques: MutableState<List<RequestCritique>?>,
                  hostNavController: NavHostController,
                  frenzies: MutableState<List<RequestCritique>?>,
                  queries: MutableState<List<RequestCritique>?>,
                  critiqued: MutableState<List<Critique>?>,
                  queryCritiques: MutableState<List<Critique>?>,
                  positiveCritiques: MutableState<List<RequestPositivity>?>,
                  frenzy: MutableState<List<Critique>?>
) {
    val items = listOf(
        Screen.Home,
        Screen.FindPartners,
        Screen.Messages,
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(backgroundColor = MaterialTheme.colorScheme.background) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(
                            imageVector = screen.resourceId,
                            contentDescription = null
                        ) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()) {
            composable(Screen.Home.route) {
                HomeFeed(user = user, questions = questions, toCritiques = toCritiques, hostNavController,
                    frenzies, queries)
            }
            composable(Screen.FindPartners.route) { SearchNavHost(user) }
            composable(Screen.Messages.route) {
                AccountView(user = user, hostNavController, critiqued = critiqued,
                    queryCritiques = queryCritiques, positiveCritiques = positiveCritiques, frenzy = frenzy)
            }
        }
    }
}

sealed class Screen(val route: String, val label: String, val resourceId: ImageVector) {
    object Home : Screen("homeFeed", "", Icons.Default.Home)
    object Messages : Screen("ShowMessages", "Messages", Icons.Default.Edit)
    object FindPartners : Screen("findPartners", "Find Partners", Icons.Default.Search)
}