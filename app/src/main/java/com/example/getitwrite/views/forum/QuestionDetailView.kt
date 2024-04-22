package com.example.getitwrite.views.forum

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import com.example.getitwrite.Colours
import com.example.getitwrite.modals.Question
import com.example.getitwrite.modals.Reply
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.DetailHeader
import com.example.getitwrite.views.components.ErrorText
import com.example.getitwrite.views.components.ProfileImage
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import java.util.UUID

@Composable
fun QuestionDetailView(question: Question, user: User,
                       backStackEntry: NavBackStackEntry, navigateUp: () -> Unit) {
    var errorString = remember { mutableStateOf("") }
    var replies = remember { mutableStateListOf<Reply>() }
    RepliesViewModel().getReplies(question.id).observe(backStackEntry) {
        var ids = replies.map { it.id }
        it.forEach {
            if (!ids.contains(it.id)) {
                replies.add(it)
            }
        }
    }
    val reply = remember { mutableStateOf("") }
    Column {
        DetailHeader(title = "", navigateUp = navigateUp)
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState(0)),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ForumView(question, {})
                replies.forEach {
                    ReplyView(it)
                }
                ErrorText(error = errorString)
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = reply.value,
                        maxLines = 1,
                        onValueChange = { reply.value = it },
                        label = {
                            Box {
                                Text(text = "Text Message")
                            }
                        }
                    )
                    Button(
                        onClick = {
                            if (reply.value != "") {
                                val id = UUID.randomUUID().toString()
                                val r = Reply(id = id, reply = reply.value, replierId = user.id, replierName = user.displayName, replierColour = user.colour, timestamp = Timestamp.now())
                                Firebase.firestore.collection("questions").document(question.id)
                                    .collection("replies").document(id).set(r)
                                    .addOnSuccessListener {
                                        reply.value = ""
                                    }
                                    .addOnFailureListener {
                                        errorString.value = it.message.toString()
                                    }
                            } else {
                                errorString.value = "Reply cannot be empty"
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

@Composable
fun ReplyView(reply: Reply) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(username = reply.replierName, profileColour = reply.replierColour)
            Text(reply.replierName, fontSize = 20.sp)
        }
        Text(reply.reply)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = DateUtils.getRelativeTimeSpanString(
                    (reply.timestamp.seconds * 1000),
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS,
                    DateUtils.WEEK_IN_MILLIS.toInt()
                ).toString(),
                fontWeight = FontWeight.Light
            )
        }
        Divider()
    }
}

class RepliesViewModel : ViewModel() {
    var replies: MutableLiveData<List<Reply>> = MutableLiveData()

    fun getReplies(questionId: String): LiveData<List<Reply>> {
        Firebase.firestore.collection("questions").document(questionId)
            .collection("replies").addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    replies.value = null
                    return@EventListener
                }

                var savedLists: MutableList<Reply> = mutableListOf()
                for (doc in value!!) {
                    savedLists.add(Reply(doc.id, doc.data!!))
                }
                replies.value = savedLists
            })

        return replies
    }
}