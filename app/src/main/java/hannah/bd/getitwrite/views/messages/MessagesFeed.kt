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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import hannah.bd.getitwrite.modals.Chat
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.FindPartnersText
import hannah.bd.getitwrite.views.components.ProfileImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@Composable
fun ChatsFeed(user: User, chatsViewModel: ChatsViewModel, selectChat: (String, String, String) -> Unit) {
    val chats by chatsViewModel.chatsFlow.collectAsState(initial = emptyList())
    if (chats.isEmpty()) {
        Column(Modifier.padding(10.dp)) {
            Text("You have no chats.", fontWeight = FontWeight.Bold)
            FindPartnersText()
        }
    } else {
        LazyColumn(Modifier.padding(10.dp)) {
            items(chats) { chat ->
                val user2 = chat.users.filter { it != user.id }
                ChatView(ChatViewViewModel(user2.get(0)), chat, selectChat)
            }
        }
    }
}

@Composable
fun ChatView(viewModel: ChatViewViewModel, chat: Chat, select: (String, String, String) -> Unit) {
    val user2 by viewModel.user2Data.collectAsState(initial = User(id = "ERROR"))
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable {
                    select(chat.id, user2.displayName, user2.id)
                }, horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(username = user2.displayName, profileColour = user2.colour)
            Text(user2.displayName, fontSize = 20.sp)
        }
        Divider()
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
            emit(User(id = "ERROR"))
        }
    }
}

class ChatsViewModel(user: User) : ViewModel() {
    val chatsFlow = flow {
        val documents = Firebase.firestore.collection("chats")
            .whereArrayContains("users", user.id)
            .get().await()
        val items = documents.map { doc ->
            Chat(doc.id, doc.data)
        }
        emit(items)
    }
}