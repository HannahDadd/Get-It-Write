package hannah.bd.getitwrite.views.forum

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
import hannah.bd.getitwrite.modals.Question
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.ErrorText
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.views.components.CheckInput
import java.util.UUID

@Composable
fun MakeQuestionView(user: User, onSuccess: (Question) -> Unit) {
    var errorString = remember { mutableStateOf<String?>(null) }
    val question = remember { mutableStateOf("") }
    Column(modifier = Modifier
        .padding(20.dp)
        .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text("Get to know the community! No followers, just friends. Ask anything about writing, books, tips or advice.")
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
                if(CheckInput.isStringGood(question.value, 200)) {
                    val id = UUID.randomUUID().toString()
                    val q = Question(id = id, question = question.value, questionerId = user.id, questionerColour = user.colour, questionerName = user.displayName, timestamp = Timestamp.now())
                    Firebase.firestore.collection("questions")
                        .document(id)
                        .set(q)
                        .addOnSuccessListener {
                            onSuccess(q)
                        }
                        .addOnFailureListener { e ->
                            errorString.value = e.toString()
                        }
                } else {
                    errorString.value = CheckInput.errorStringText
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