package hannah.bd.getitwrite.views.proposals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.GlobalVariables
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.CreateTagCloud
import hannah.bd.getitwrite.views.components.ErrorText
import hannah.bd.getitwrite.views.components.SelectTagCloud
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.views.components.CheckInput
import java.util.UUID

@Composable
fun MakeProposalView(user: User, onSuccess: () -> Unit) {
    var errorString = remember { mutableStateOf<String?>(null) }
    val title = remember { mutableStateOf("") }
    val wordCount = remember { mutableStateOf("") }
    val blurb = remember { mutableStateOf("") }
    val authorNotes = remember { mutableStateOf("") }
    val genreTags = remember { mutableStateOf(mutableListOf<String>()) }
    val triggerWarnings = remember { mutableStateOf(mutableListOf<String>()) }
    var projectType = remember { mutableStateOf(mutableListOf<String>()) }
    Column(modifier = Modifier
        .padding(20.dp)
        .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text("Tell our community a bit about your project to find your next critique partner.")
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
            genreTags.value.add(it)
        }
        OutlinedTextField(value = authorNotes.value,
            maxLines = 5,
            onValueChange = { authorNotes.value = it },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            label = { Text(text = "Author's notes") }
        )
        SelectTagCloud(question = "Select project type", answers = GlobalVariables.typeOfProject) {
            projectType.value.add(it)
        }
        OutlinedTextField(value = wordCount.value,
            maxLines = 1,
            onValueChange = { wordCount.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Word Count") }
        )
        CreateTagCloud(question = "Trigger Warnings") {
            triggerWarnings.value.add(it)
        }
        ErrorText(error = errorString)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val parsedInt = wordCount.value.toIntOrNull()
                if (parsedInt != null) {
                    if (title.value == "") {
                        errorString.value = "Your project needs a title!"
                    } else if (genreTags.value.isEmpty()) {
                        errorString.value = "You must choose at least one genre."
                    } else if (CheckInput.verifyCanBeEmpty(title.value) ||
                        CheckInput.verifyCanBeEmpty(wordCount.value) ||
                        CheckInput.verifyCanBeEmpty(blurb.value) ||
                        CheckInput.verifyCanBeEmpty(title.value) ||
                        CheckInput.verifyCanBeEmpty(authorNotes.value)) {

                        val id = UUID.randomUUID().toString()
                        val p = Proposal(id = id, title = title.value, typeOfProject = projectType.value, blurb = blurb.value, triggerWarnings = triggerWarnings.value, genres = genreTags.value, timestamp = Timestamp.now(), authorNotes = authorNotes.value, wordCount = parsedInt, writerId = user.id, writerName = user.displayName)
                        Firebase.firestore.collection("proposals")
                            .document(id)
                            .set(p)
                            .addOnSuccessListener {
                                onSuccess()
                            }
                            .addOnFailureListener { e ->
                                errorString.value = e.toString()
                            }
                    } else {
                        errorString.value = "Some of your values contain profanities. This is not allowed."
                    }
                } else {
                    errorString.value = "Word count needs to be a number. Characters like 'k' or ',' are not allowed."
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary)
        ) {
            Text("CREATE", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}