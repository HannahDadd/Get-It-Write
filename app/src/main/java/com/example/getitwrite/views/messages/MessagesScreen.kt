package com.example.getitwrite.views.messages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.getitwrite.AppActions
import com.example.getitwrite.Destinations
import com.example.getitwrite.modals.Chat
import com.example.getitwrite.modals.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@Composable
fun ChatsScreen(user: User, chatsViewModel: ChatsViewModel) {
    val chats by chatsViewModel.chatsFlow.collectAsState(initial = emptyList())
    val errorString = remember { mutableStateOf("") }
    val navController = rememberNavController()
    val actions = remember(navController) { AppActions(navController) }

    NavHost(
        navController = navController,
        startDestination = Destinations.chatsList
    ) {
        composable(Destinations.chatsList) {
            ChatsFeed(user = user, chats = chats, selectChat = actions.selectChat)
        }
        composable(
            "${Destinations.chatsList}/${Destinations.chat_id}",
            arguments = listOf(
                navArgument(Destinations.chat_id) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)

        }
    }
}

class ChatsViewModel(user: User) : ViewModel() {
    val chatsFlow = flow {
        val documents = Firebase.firestore.collection("chats")
//            .whereArrayContains("users", user.id)
            .get().await()
        val items = documents.map { doc ->
            Chat(doc.data)
        }
        emit(items)
    }
}