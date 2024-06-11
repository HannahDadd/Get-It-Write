package hannah.bd.getitwrite.views.feed

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hannah.bd.getitwrite.Colours
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.Question
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.critiqueFrenzy.FrenzyFeed
import hannah.bd.getitwrite.views.forum.ForumFeed
import hannah.bd.getitwrite.views.messages.ChatsFeed
import hannah.bd.getitwrite.views.messages.ChatsViewModel
import hannah.bd.getitwrite.views.proposals.ProposalsFeed
import hannah.bd.getitwrite.views.toCritique.ToCritiqueFeed

@Composable
fun ShowFeed(user: User, questions: List<Question>, critiqueFrenzy: List<RequestCritique>, toCritiques: List<RequestCritique>,
             proposals: List<Proposal>, selectProposal: (Proposal) -> Unit,
             selectChat: (String, String, String) -> Unit, selectCritiqueRequest: (String) -> Unit,
             selectQuestion: (String) -> Unit, selectFrenzy: (String) -> Unit) {
    val items = listOf(
        Screen.CritiqueFrenzy,
        Screen.ToCritique,
        Screen.Forum,
        Screen.Messages,
        Screen.FindPartners
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(backgroundColor = Colours.bold) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(
                            imageVector = screen.resourceId,
                            contentDescription = screen.label,
                            modifier = Modifier.size(20.dp)
                        ) },
                        label = { Text(screen.label, fontWeight = FontWeight.Light, fontSize = 8.sp) },
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
            startDestination = Screen.Forum.route,
            modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            composable(Screen.CritiqueFrenzy.route) { FrenzyFeed(user, proposals, critiqueFrenzy, selectFrenzy) }
            composable(Screen.ToCritique.route) { ToCritiqueFeed(toCritiques, selectCritiqueRequest) }
            composable(Screen.Forum.route) { ForumFeed(user = user, questions, selectQuestion) }
            composable(Screen.Messages.route) { ChatsFeed(user = user, chatsViewModel = ChatsViewModel(user), selectChat = selectChat) }
            composable(Screen.FindPartners.route) { ProposalsFeed(user = user, proposals = proposals, selectProposal = selectProposal) }
        }
    }
}

sealed class Screen(val route: String, val label: String, val resourceId: ImageVector) {
    object ToCritique : Screen("toCritique", "Critique Swap", Icons.Default.Edit)
    object CritiqueFrenzy : Screen("frenzy", "All Critiques", Icons.AutoMirrored.Filled.List)
    object Forum : Screen("forum", "Forum", Icons.Default.Home)
    object Messages : Screen("ShowMessages", "Messages", Icons.Default.Email)
    object FindPartners : Screen("findPartners", "Find Partners", Icons.Default.Search)
}