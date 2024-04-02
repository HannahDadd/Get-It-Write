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
import com.example.getitwrite.modals.Chat
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.DetailHeader

@Composable
fun ShowMessages(
    chatId: String,
    chats: List<Chat>,
    user: User,
    navigateUp: () -> Unit) {
    val chat: Chat? = remember(chatId) {
        chats.find { it.id == chatId }
    }
    if (chat != null) {
        Column {
            DetailHeader(title = "", navigateUp = navigateUp)
            Column(
                modifier = Modifier.padding(10.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
            }
        }
    }
}