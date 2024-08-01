package hannah.bd.getitwrite.views.positivityCorner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.RequestPositivity
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.CheckInput
import hannah.bd.getitwrite.views.components.ErrorText
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID

@Composable
fun MakePositiveCorner(user: User, close: () -> Unit) {
    val text = remember { mutableStateOf("") }
    var errorString = remember { mutableStateOf("") }
    val ids by MakePositiveCornerViewModel().proposalsFlow.collectAsState(initial = emptyList())
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Note: there is a 500 word limit and no text with trigger warnings in positivity corner.")
        OutlinedTextField(value = text.value,
            maxLines = 10,
            onValueChange = { text.value = it },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            label = { Text(text = "Text") }
        )
        ErrorText(error = errorString)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                CheckInput.verify(text.value)?.let {
                    val id = UUID.randomUUID().toString()
                    val requestCritique = RequestPositivity(id = id, text = text.value, writerName = user.displayName, writerId = user.id, comments = mapOf())
                    Firebase.firestore.collection("positivityPeices").document(id).set(requestCritique)
                        .addOnSuccessListener {
                            val newIds = ids.plus(id)
                            Firebase.firestore.collection("positivityPeices").document("ids").set(mapOf("ids" to listOf(id)))
                                .addOnSuccessListener {
                                    println("Preferences successfully updated!")
                                }
                                .addOnFailureListener { e ->
                                    println("Error updating preferences: $e")
                                }
                            Firebase.firestore.collection("positivityPeices").document("ids").set(requestCritique)
                                .addOnSuccessListener {
                                    close()
                                }
                                .addOnFailureListener {
                                    errorString.value = it.message.toString()
                                }
                        }
                        .addOnFailureListener {
                            errorString.value = it.message.toString()
                        }
                } ?: run {
                    errorString.value = CheckInput.errorStringText
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary)
        ) {
            Text("Submit", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}

class MakePositiveCornerViewModel() : ViewModel() {
    val proposalsFlow = flow {
        val ids = Firebase.firestore.collection("positivityCorner")
            .document("ids").get().await()
        val idsArray = ids["ids"] as MutableList<String>
        emit(idsArray)
    }
}