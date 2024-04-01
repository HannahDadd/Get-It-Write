package com.example.getitwrite.views.feed

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.List
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
import com.example.getitwrite.Colours
import com.example.getitwrite.modals.Proposal
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.messages.ChatsFeed
import com.example.getitwrite.views.messages.ChatsViewModel
import com.example.getitwrite.views.messages.ShowMessages
import com.example.getitwrite.views.proposals.ProposalsFeed

@Composable
fun ShowFeed(user: User, proposals: List<Proposal>, selectProposal: (String) -> Unit, selectChat: (String) -> Unit) {
    val items = listOf(
        Screen.YourWork,
        Screen.ToCritique,
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
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.ToCritique.route, Modifier.padding(innerPadding)) {
            composable(Screen.YourWork.route) { ShowMessages() }
            composable(Screen.ToCritique.route) { ShowMessages() }
            composable(Screen.Messages.route) { ChatsFeed(user = user, chatsViewModel = ChatsViewModel(user), selectChat = selectChat) }
            composable(Screen.FindPartners.route) { ProposalsFeed(user = user, proposals = proposals, selectProposal = selectProposal) }
        }
    }
}

sealed class Screen(val route: String, val label: String, val resourceId: ImageVector) {
    object YourWork : Screen("yourWork", "Your Work", Icons.Default.List)
    object ToCritique : Screen("toCritique", "To Critique", Icons.Default.Edit)
    object Messages : Screen("ShowMessages", "Messages", Icons.Default.Email)
    object FindPartners : Screen("findPartners", "Find Partners", Icons.Default.Search)
}