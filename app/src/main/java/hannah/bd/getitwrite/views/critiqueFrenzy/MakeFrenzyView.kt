package hannah.bd.getitwrite.views.critiqueFrenzy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.Colours
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.ErrorText
import hannah.bd.getitwrite.views.messages.SelectProposalView
import hannah.bd.getitwrite.views.proposals.ProposalView
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.GlobalVariables
import hannah.bd.getitwrite.views.components.CheckInput
import hannah.bd.getitwrite.views.components.SelectTagCloud
import java.util.UUID

@Composable
fun MakeFrenzyView(user: User, dbName: String, placeHolder: String, onSuccess: (RequestCritique) -> Unit) {
    var errorString = remember { mutableStateOf<String?>(null) }
    val genreTags = remember { mutableStateOf(mutableListOf<String>()) }
    val text = remember { mutableStateOf("") }
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("1,000 word limit without any trigger warnings.")
        SelectTagCloud(question = "Select genres", answers = GlobalVariables.genres) {
            genreTags.value.add(it)
        }
        OutlinedTextField(value = text.value,
            maxLines = 10,
            onValueChange = { text.value = it },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            label = { Text(text = placeHolder) }
        )
        ErrorText(error = errorString)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (genreTags.value.isEmpty()) {
                    errorString.value = "You must select at least one genre"
                } else if (CheckInput.isStringGood(text.value, 1000)) {
                    val id = UUID.randomUUID().toString()
                    val workTitle = if (dbName == "frenzy") "Critique Frenzy" else "Query"
                    val request = RequestCritique(id = id, title = "Critique Frenzy", blurb = "", genres = genreTags.value,
                        triggerWarnings = mutableListOf(), workTitle = workTitle, text = text.value, timestamp = Timestamp.now(), writerId = user.id, writerName = user.displayName)
                    Firebase.firestore.collection(dbName).document(id).set(request)
                        .addOnSuccessListener {
                            onSuccess(request)
                        }
                        .addOnFailureListener {
                            errorString.value = it.message.toString()
                        }
                } else {
                    errorString.value = CheckInput.errorStringText
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )        ) {
            Text("Submit", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}