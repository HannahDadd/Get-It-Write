package com.example.getitwrite.views.proposals

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
import com.example.getitwrite.GlobalVariables
import com.example.getitwrite.modals.Proposal
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.CreateTagCloud
import com.example.getitwrite.views.components.ErrorText
import com.example.getitwrite.views.components.SelectTagCloud
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import java.util.UUID

@Composable
fun MakeProposalView(user: User, onSuccess: () -> Unit) {
    var errorString = remember { mutableStateOf("") }
    val title = remember { mutableStateOf("") }
    val wordCount = remember { mutableStateOf("") }
    val blurb = remember { mutableStateOf("") }
    val authorNotes = remember { mutableStateOf("") }
    val genreTags = ArrayList<String>()
    val triggerWarnings = ArrayList<String>()
    var projectType = ArrayList<String>()
    Column(modifier = Modifier
        .padding(20.dp)
        .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        OutlinedTextField(value = title.value,
            maxLines = 1,
            onValueChange = { title.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Title") }
        )
        OutlinedTextField(value = blurb.value,
            maxLines = 5,
            onValueChange = { blurb.value = it },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            label = { Text(text = "Blurb") }
        )
        SelectTagCloud(question = "Genres", answers = GlobalVariables.genres) {
            genreTags.add(it)
        }
        OutlinedTextField(value = authorNotes.value,
            maxLines = 5,
            onValueChange = { authorNotes.value = it },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            label = { Text(text = "Author's notes") }
        )
        SelectTagCloud(question = "Select project type", answers = GlobalVariables.typeOfProject) {
            projectType.add(it)
        }
        OutlinedTextField(value = wordCount.value,
            maxLines = 1,
            onValueChange = { wordCount.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Word Count") }
        )
        CreateTagCloud(question = "Trigger Warnings") {
            triggerWarnings.add(it)
        }
        ErrorText(error = errorString)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                wordCount.value.toInt()
                val id = UUID.randomUUID().toString()
                val p = Proposal(id = id, title = title.value, typeOfProject = projectType, blurb = blurb.value, triggerWarnings = triggerWarnings, genres = genreTags, timestamp = Timestamp.now(), authorNotes = authorNotes.value, wordCount = wordCount.value.toInt(), writerId = user.id, writerName = user.displayName)
                Firebase.firestore.collection("proposals")
                    .document(id)
                    .set(p)
                    .addOnSuccessListener {
                        onSuccess()
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