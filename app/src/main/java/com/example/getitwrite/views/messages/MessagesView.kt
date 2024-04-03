package com.example.getitwrite.views.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getitwrite.modals.Chat
import com.example.getitwrite.modals.Message
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.DetailHeader
import com.example.getitwrite.views.feed.MainViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@Composable
fun ShowMessages(
    viewModel: MessagesViewModel,
    chatId: String,
    chats: List<Chat>,
    user: User,
    user2Name: String,
    navigateUp: () -> Unit
) {
    val viewModel = MessagesViewModel()
    viewModel.getMessages(chatId)
    Column {
        DetailHeader(title = user2Name, navigateUp = navigateUp)
        Column(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SingleMessage()
        }
    }
}

class MessagesViewModel : ViewModel() {
    var messages: MutableLiveData<List<Message>> = MutableLiveData()

    fun getMessages(chatId: String): LiveData<List<Message>> {
        Firebase.firestore.collection("chats").document(chatId)
            .collection("messages").addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    messages.value = null
                    return@EventListener
                }

                var savedAddressList: MutableList<Message> = mutableListOf()
                for (doc in value!!) {
                    savedAddressList.add(Message(doc.data!!))
                }
                messages.value = savedAddressList
            })

        return messages
    }
}

@Composable
fun SingleMessage() {
    Row(Modifier.height(IntrinsicSize.Max)) {
        Column(
            modifier = Modifier.background(
                color = Color.Green,
                shape = RoundedCornerShape(4.dp, 4.dp, 0.dp, 4.dp)
            )//.width(xxxx)
        ) {
            Text("Chat")
        }
        Column(
            modifier = Modifier
                .background(
                    color = Color.Black,
                    shape = TriangleEdgeShape(10)
                )
                .width(8.dp)
                .fillMaxHeight()
        ) {
        }
    }
}

class TriangleEdgeShape(val offset: Int) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            moveTo(x = 0f, y = size.height - offset)
            lineTo(x = 0f, y = size.height)
            lineTo(x = 0f + offset, y = size.height)
        }
        return Outline.Generic(path = trianglePath)
    }
}