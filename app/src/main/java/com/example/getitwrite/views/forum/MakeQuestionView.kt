package com.example.getitwrite.views.forum

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.getitwrite.modals.Question
import com.example.getitwrite.modals.Reply
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.ErrorText
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import java.util.UUID

@Composable
fun MakeQuestionView(user: User, onSuccess: (Question) -> Unit) {
    var errorString = remember { mutableStateOf("") }
    val wordCount = remember { mutableStateOf("") }
    val question = remember { mutableStateOf("") }
    Column(modifier = Modifier
        .padding(20.dp)
        .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        OutlinedTextField(value = question.value,
            maxLines = 5,
            onValueChange = { question.value = it },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            label = { Text(text = "Question") }
        )
        ErrorText(error = errorString)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                wordCount.value.toInt()
                val id = UUID.randomUUID().toString()
                val q = Question(id = id, question = question.value, questionerId = user.id, questionerColour = user.colour, questionerName = user.displayName, upVotes = 0, timestamp = Timestamp.now())
                Firebase.firestore.collection("questions")
                    .document(id)
                    .set(q)
                    .addOnSuccessListener {
                        onSuccess(q)
                    }
                    .addOnFailureListener { e ->
                        errorString.value = e.toString()
                    }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Colours.Dark_Readable, contentColor = Color.White)
        ) {
            Text("CREATE", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}