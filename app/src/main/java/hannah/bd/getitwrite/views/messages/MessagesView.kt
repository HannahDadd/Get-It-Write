package hannah.bd.getitwrite.views.messages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import hannah.bd.getitwrite.Colours
import hannah.bd.getitwrite.modals.Message
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.DetailHeader
import hannah.bd.getitwrite.views.components.ErrorText
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowMessages(
    chatId: String,
    user: User,
    user2Name: String,
    user2Id: String,
    proposals: List<Proposal>,
    backStackEntry: NavBackStackEntry,
    navigateUp: () -> Unit
) {
    var errorString = remember { mutableStateOf("") }
    var message = remember { mutableStateOf("") }
    var messages = remember { mutableStateListOf<Message>() }
    MessagesViewModel().getMessages(chatId).observe(backStackEntry) {
        var ids = messages.map { it.id }
        it.forEach {
            if (!ids.contains(it.id)) {
                messages.add(it)
            }
        }
    }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    Column {
        DetailHeader(title = user2Name, navigateUp = navigateUp)
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                SendWorkView(user2Id, user = user, proposals = proposals, chatID = chatId) {
                    showBottomSheet = false
                }
            }
        }
        val lazyListState = rememberLazyListState()
        LaunchedEffect(key1 = messages.size) {
            lazyListState.animateScrollToItem(messages.size + 1)
        }
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxHeight()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages) { it ->
                if (it.senderId == user.id) {
                    SingleOwnMessage(it.content, it.created)
                } else {
                    SingleOtherMessage(user = user, message = it, chatId = chatId)
                }
            }
            item {
                ErrorText(error = errorString)
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { showBottomSheet = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Colours.Dark_Readable, contentColor = Color.White)
                ) {
                    Text("Send work to $user2Name", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = message.value,
                        maxLines = 1,
                        onValueChange = { message.value = it },
                        label = {
                            Box {
                                Text(text = "Text Message")
                            }
                        }
                    )
                    Button(
                        onClick = {
                            if (message.value != "") {
                                val id = UUID.randomUUID().toString()
                                val m = Message(content = message.value, created = Timestamp.now(), senderId = user.id, id = id)
                                Firebase.firestore.collection("chats").document(chatId)
                                    .collection("messages").document(id).set(m)
                                    .addOnSuccessListener {
                                        message.value = ""
                                    }
                                    .addOnFailureListener {
                                        errorString.value = it.message.toString()
                                    }
                            }
                        },
                        shape = CircleShape,
                        modifier = Modifier.size(40.dp),
                        contentPadding = PaddingValues(1.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

class MessagesViewModel : ViewModel() {
    var messages: MutableLiveData<List<Message>> = MutableLiveData()

    fun getMessages(chatId: String): LiveData<List<Message>> {
        Firebase.firestore.collection("chats").document(chatId)
            .collection("messages")
            .orderBy("created", Query.Direction.ASCENDING)
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    messages.value = null
                    return@EventListener
                }

                var savedLists: MutableList<Message> = mutableListOf()
                for (doc in value!!) {
                    savedLists.add(Message(doc.id, doc.data!!))
                }
                messages.value = savedLists
            })

        return messages
    }
}