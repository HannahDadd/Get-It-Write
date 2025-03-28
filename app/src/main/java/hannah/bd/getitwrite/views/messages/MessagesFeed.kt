package hannah.bd.getitwrite.views.messages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hannah.bd.getitwrite.modals.Chat
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.FindPartnersText
import hannah.bd.getitwrite.views.components.ProfileImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.theme.AppTypography
import hannah.bd.getitwrite.views.components.DetailHeader
import hannah.bd.getitwrite.views.components.ErrorText
import hannah.bd.getitwrite.views.proposals.GenreFeed
import hannah.bd.getitwrite.views.proposals.ProposalDetails
import hannah.bd.getitwrite.views.proposals.getProposalsByGenre
import hannah.bd.getitwrite.views.proposals.getProposalsByUser
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@Composable
fun ChatsFeed(navController: NavController, user: User, hostNavController: NavHostController, chats: List<Chat>) {
    Column {
        DetailHeader(title = "Messages", navigateUp = { hostNavController.navigateUp() })
        if (chats.isEmpty()) {
            Column(Modifier.padding(10.dp)) {
                Text("You have no chats.", fontWeight = FontWeight.Bold)
                FindPartnersText()
            }
        } else {
            LazyColumn(Modifier.padding(10.dp)) {
                items(chats) { chat ->
                    ChatView(chat, user, navController)
                }
            }
        }
    }
}

@Composable
fun ChatView(chat: Chat, user: User, navController: NavController) {
    var user2 = remember { mutableStateOf<User?>(null) }

    LaunchedEffect(Unit) {
        getUser(chat.users.filter { it != user.id }.first(),
            onSuccess = {
                user2.value = it
            },
            onError = { exception ->
            }
        )
    }
    user2.value?.let {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        navController.navigate("chatDetails/${chat.id}/${it.displayName}/${it.id}")
                    }, horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileImage(username = it.displayName, profileColour = it.colour)
                Text(it.displayName, style = AppTypography.titleLarge)
            }
            Divider()
        }
    }
}

@Composable
fun MessagesNavHost(hostNavController: NavHostController, user: User) {
    val navController = rememberNavController()
    var chats = remember { mutableStateOf<List<Chat>?>(null) }
    var errorString = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        getChats(user = user,
            onSuccess = {
                chats.value = it
            },
            onError = { exception ->
                errorString.value = exception.message
            }
        )
    }

    chats.value?.let {
        NavHost(navController = navController, startDestination = "messagesFeed") {
            composable("messagesFeed") {
                ChatsFeed(navController = navController, user, hostNavController, chats.value!!)
            }
            composable(
                "chatDetails/{chat_id}/{otherUserName}/{otherUserId}",
                arguments = listOf(
                    navArgument("chat_id") {
                        type = NavType.StringType
                    },
                    navArgument("otherUserName") {
                        type = NavType.StringType
                    },
                    navArgument("otherUserId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                ShowMessages(
                    chatId = arguments.getString("chat_id")!!,
                    user = user,
                    user2Name = arguments.getString("otherUserName")!!,
                    user2Id = arguments.getString("otherUserId")!!,
                    backStackEntry = backStackEntry,
                    navigateUp = { navController.navigateUp() }
                )
            }
        }
    }?: run {
        errorString.value?.let {
            ErrorText(error = it)
        }?: run {
            Text("Loading...", modifier = Modifier.padding(16.dp))
        }
    }
}