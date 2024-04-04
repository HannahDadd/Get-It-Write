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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager.BackStackEntry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.getitwrite.modals.Message
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.DetailHeader
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

@Composable
fun ShowMessages(
    chatId: String,
    user: User,
    user2Name: String,
    backStackEntry: NavBackStackEntry,
    navigateUp: () -> Unit
) {
    var messages = listOf<Message>()
    MessagesViewModel().getMessages(chatId).observe(backStackEntry, Observer {
        messages = it
    })
    Column {
        DetailHeader(title = user2Name, navigateUp = navigateUp)
        Column(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = messages.size.toString())
            messages.forEach {
                SingleMessage(it.content)
            }
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

                var savedLists: MutableList<Message> = mutableListOf()
                for (doc in value!!) {
//                    savedLists.add(Message(doc.data!!))
                    savedLists.add(Message(content = "Hello", created = Timestamp.now(), senderID = ""))
                }
                messages.value = savedLists
            })

        return messages
    }
}

@Composable
fun SingleMessage(text: String) {
    Row(Modifier.height(IntrinsicSize.Max)) {
        Column(
            modifier = Modifier.background(
                color = Color.Green,
                shape = RoundedCornerShape(4.dp, 4.dp, 0.dp, 4.dp)
            )//.width(xxxx)
        ) {
            Text(text)
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