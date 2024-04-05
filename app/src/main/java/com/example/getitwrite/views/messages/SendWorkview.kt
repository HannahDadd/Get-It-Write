package com.example.getitwrite.views.messages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.getitwrite.Colours
import com.example.getitwrite.modals.Proposal
import com.example.getitwrite.modals.RequestCritique
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.ErrorText
import com.example.getitwrite.views.components.QuestionSection
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import java.util.UUID

@Composable
fun SendWorkView(user2Id: String, user: User, closeAction: () -> Unit) {
    var errorString = remember { mutableStateOf("") }
    val title = remember { mutableStateOf("") }
    val text = remember { mutableStateOf("") }
    val genreTags = ArrayList<String>()
    val triggerWarnings = ArrayList<String>()
    val proposal: Proposal? = null
    Column {
        OutlinedTextField(
            value = title.value,
            maxLines = 1,
            onValueChange = { title.value = it },
            label = {
                Box {
                    Text(text = "Title")
                }
            }
        )
        QuestionSection(response = text, question = "Text:")
        ErrorText(error = errorString)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                proposal?.let {
                    val id = UUID.randomUUID().toString()
                    val requestCritique = RequestCritique(id = id, title = it.title, blurb = it.blurb, genres = it.genres, triggerWarnings = it.triggerWarnings, workTitle = title.value, text = text.value, timestamp = Timestamp.now(), writerId = user.id, writerName = user.displayName)
                    Firebase.firestore.collection("users").document(user2Id)
                        .collection("requestCritiques").document(id).set(requestCritique)
                        .addOnSuccessListener {
                            closeAction()
                        }
                } ?: run {
                    errorString.value = "Choose proposal to request a critique for."
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Colours.Dark_Readable, contentColor = Color.White)
        ) {
            Text("Send critique", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}