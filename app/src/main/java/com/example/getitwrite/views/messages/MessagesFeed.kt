package com.example.getitwrite.views.messages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModel
import com.example.getitwrite.modals.Chat
import com.example.getitwrite.modals.Proposal
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.MainViewModel
import com.example.getitwrite.views.components.FindPartnersText
import com.example.getitwrite.views.components.ProfileImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@Composable
fun ChatsFeed(user: User, chats: List<Chat>, selectChat: (String) -> Unit) {
    if (chats.count() == 0) {
        Column(Modifier.padding()) {
            Text("You have no chats.", fontWeight = FontWeight.Bold)
            FindPartnersText()
        }
    } else {
        LazyColumn(Modifier.padding()) {
            items(chats) { chat ->
                val user2 = chat.users.filter { it != user.id }
                Text(user2[0])
//            ChatView(ChatViewViewModel(user2.get(0)), chat, selectChat)
            }
        }
    }
}

@Composable
fun ChatView(viewModel: ChatViewViewModel, chat: Chat, selectProposal: (String) -> Unit) {
    val user2 by viewModel.user2Data.collectAsState(initial = User(id = "1", displayName = "", bio = "", writing = "", critiqueStyle = "", authors = ArrayList(), writingGenres = ArrayList(), colour = 1))
    Card(Modifier.clickable { selectProposal(chat.id) }) {
        Row {
            ProfileImage(username = user2.displayName, profileColour = user2.colour)
            Text(user2.displayName)
        }
    }
}

class ChatViewViewModel(userID: String) : ViewModel() {
    val user2Data = flow {
        val doc = Firebase.firestore.collection("users")
            .document(userID)
            .get().await()
        doc.data?.let {
            emit(User(id = doc.id, data = it))
        } ?: run {
            emit(User(id = "1", displayName = "", bio = "", writing = "", critiqueStyle = "", authors = ArrayList(), writingGenres = ArrayList(), colour = 1))
        }
    }
}