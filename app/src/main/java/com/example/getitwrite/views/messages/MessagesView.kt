package com.example.getitwrite.views.messages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.getitwrite.modals.Chat
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.DetailHeader
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@Composable
fun ShowMessages(
    chatId: String,
    chats: List<Chat>,
    user: User,
    user2Name: String,
    navigateUp: () -> Unit) {
    val chat: Chat? = remember(chatId) {
        chats.find { it.id == chatId }
    }
    if (chat != null) {
        Column {
            DetailHeader(title = user2Name, navigateUp = navigateUp)
            Column(
                modifier = Modifier.padding(10.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
            }
        }
    }
}

class MessagesViewModel(user: User) : ViewModel() {
    val messagesFlow = flow {
        val documents = Firebase.firestore.collection("chats")
//            .whereArrayContains("users", user.id)
            .get().await()
        val items = documents.map { doc ->
            Chat(doc.data)
        }
        emit(items)
    }
}