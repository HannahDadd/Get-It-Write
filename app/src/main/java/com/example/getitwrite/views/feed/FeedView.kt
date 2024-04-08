package com.example.getitwrite.views.feed

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
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
import com.example.getitwrite.modals.Critique
import com.example.getitwrite.modals.Proposal
import com.example.getitwrite.modals.Question
import com.example.getitwrite.modals.RequestCritique
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.forum.ForumFeed
import com.example.getitwrite.views.messages.ChatsFeed
import com.example.getitwrite.views.messages.ChatsViewModel
import com.example.getitwrite.views.messages.ShowMessages
import com.example.getitwrite.views.proposals.ProposalsFeed
import com.example.getitwrite.views.toCritique.CritiquedFeed
import com.example.getitwrite.views.toCritique.ToCritiqueFeed
import com.example.getitwrite.views.toCritique.ToCritiqueViewModel

@Composable
fun ShowFeed(user: User, questions: List<Question>, toCritiques: List<RequestCritique>, proposals: List<Proposal>, selectProposal: (Proposal) -> Unit, selectChat: (String, String, String) -> Unit, selectCritiqueRequest: (String) -> Unit, selectQuestion: (String) -> Unit) {
    val items = listOf(
        Screen.ToCritique,
        Screen.CritiqueFrenzy,
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
        NavHost(navController, startDestination = Screen.Forum.route, Modifier.padding(innerPadding)) {
            composable(Screen.ToCritique.route) { ToCritiqueFeed(user = user, toCritiques, selectCritiqueRequest) }
            composable(Screen.CritiqueFrenzy.route) { ToCritiqueFeed(user = user, toCritiques, selectCritiqueRequest) }
            composable(Screen.Forum.route) { ForumFeed(user = user, questions, selectQuestion) }
            composable(Screen.Messages.route) { ChatsFeed(user = user, chatsViewModel = ChatsViewModel(user), selectChat = selectChat) }
            composable(Screen.FindPartners.route) { ProposalsFeed(user = user, proposals = proposals, selectProposal = selectProposal) }
        }
    }
}

sealed class Screen(val route: String, val label: String, val resourceId: ImageVector) {
    object ToCritique : Screen("toCritique", "To Critique", Icons.Default.Edit)
    object CritiqueFrenzy : Screen("frenzy", "Critique Frenzy", Icons.Default.)
    object Forum : Screen("forum", "Forum", Icons.Default.Home)
    object Messages : Screen("ShowMessages", "Messages", Icons.Default.Email)
    object FindPartners : Screen("findPartners", "Find Partners", Icons.Default.Search)
}