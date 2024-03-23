package com.example.getitwrite.views.messages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.getitwrite.modals.Chat

@Composable
fun ChatsFeed(chats: List<Chat>, selectChat: (String) -> Unit) {
    LazyColumn {
        items(chats) { chat ->
            ChatView(chat, selectChat)
        }
    }
}

@Composable
fun ChatView(chat: Chat, selectProposal: (String) -> Unit) {
    Card(Modifier.clickable { selectProposal(chat.id) }) {
        Row {
//            Text(text = chat.users.)
        }
    }
}