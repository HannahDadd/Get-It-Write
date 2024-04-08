package com.example.getitwrite.views.forum

import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.getitwrite.Colours
import com.example.getitwrite.modals.Critique
import com.example.getitwrite.modals.Proposal
import com.example.getitwrite.modals.Question
import com.example.getitwrite.modals.Reply
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.DetailHeader
import com.example.getitwrite.views.components.ProfileImage
import com.example.getitwrite.views.components.TagCloud
import com.example.getitwrite.views.proposals.ProposalsViewModel
import com.example.getitwrite.views.toCritique.CritiqueView
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID

@Composable
fun QuestionDetailView(question: Question, navigateUp: () -> Unit) {
    val replies by RepliedViewModel(question).replies.collectAsState(initial = emptyList())
    val reply = remember { mutableStateOf("") }
    Column {
        DetailHeader(title = "", navigateUp = navigateUp)
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ForumView(question, {})
                replies.forEach {
                    ReplyView(it)
                }
            }
            Spacer(modifier = Modifier.weight(1.0f))
            OutlinedTextField(value = reply.value,
                maxLines = 5,
                onValueChange = { reply.value = it },
                modifier = Modifier.fillMaxWidth().height(150.dp),
                label = { Text(text = "Question") }
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colours.Dark_Readable,
                    contentColor = Color.White
                )
            ) {
                Text("Reply", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ReplyView(reply: Reply) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp),
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
                    DateUtils.DAY_IN_MILLIS
                ).toString(),
                fontWeight = FontWeight.Light
            )
        }
        Divider()
    }
}

class RepliedViewModel(question: Question) : ViewModel() {
    val replies = flow {
        val documents = Firebase.firestore.collection("questions/${question.id}/replies")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get().await()
        val items = documents.map { doc ->
            Reply(doc.id, doc.data)
        }
        emit(items)
    }
}