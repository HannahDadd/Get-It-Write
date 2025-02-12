package hannah.bd.getitwrite.views.messages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.modals.Message
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.ErrorText
import hannah.bd.getitwrite.views.proposals.ProposalView
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.modals.Question
import hannah.bd.getitwrite.views.components.CheckInput
import hannah.bd.getitwrite.views.proposals.getProposalsByUser
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendWorkView(user2Id: String, user: User, chatID: String, closeAction: () -> Unit) {

    var errorString = remember { mutableStateOf<String?>(null) }
    val title = remember { mutableStateOf("") }
    val text = remember { mutableStateOf("") }
    var proposal = remember { mutableStateOf<Proposal?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                SelectProposalView(user) {
                    proposal.value = it
                    showBottomSheet = false
                }
            }
        }
        TextButton(onClick = { showBottomSheet = true }) {
            Text(modifier = Modifier.align(Alignment.Bottom),
                text = "Choose proposal", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }
        proposal.value?.let {
            ProposalView(it) {
                proposal.value = null
            }
        }
        OutlinedTextField(value = title.value, maxLines = 1, onValueChange = { title.value = it },
            label = {
                Box {
                    Text(text = "Critique Title e.g. Chapter 1")
                }
            }
        )
        OutlinedTextField(value = text.value,
            maxLines = 10,
            onValueChange = { text.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            label = { Text(text = "Text") }
        )
        ErrorText(error = errorString)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                proposal.value?.let {
                    if (text.value == "") {
                        errorString.value = "Paste or type your project above."
                    } else if (title.value == "") {
                        errorString.value = "Please include a title."
                    } else if (!CheckInput.isStringGood(text.value, 20)) {
                        errorString.value = "Title contains profanities or exceeds word limit of 20."
                    } else if (!CheckInput.isStringGood(text.value, 5000)) {
                        errorString.value = "Text contains profanities or exceeds word limit of 5000."
                    } else {
                        val id = UUID.randomUUID().toString()
                        val requestCritique = RequestCritique(id = id, title = it.title, blurb = it.blurb, genres = it.genres, triggerWarnings = it.triggerWarnings, workTitle = title.value, text = text.value, timestamp = Timestamp.now(), writerId = user.id, writerName = user.displayName)
                        Firebase.firestore.collection("users").document(user2Id)
                            .collection("requestCritiques").document(id).set(requestCritique)
                            .addOnSuccessListener {
                                val id = UUID.randomUUID().toString()
                                val message = Message(content = "WORK SENT! ${user.displayName} sent their work entitled '${requestCritique.title}'", created = Timestamp.now(), senderId = user.id, id = id)
                                Firebase.firestore.collection("chats").document(chatID)
                                    .collection("messages").document(id).set(message)
                                closeAction()
                            }
                            .addOnFailureListener {
                                errorString.value = it.message.toString()
                            }
                    }
                } ?: run {
                    errorString.value = "Choose proposal to request a critique for."
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary)
        ) {
            Text("Send critique", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SelectProposalView(user: User, selectProposal: (Proposal) -> Unit) {
    var proposals = remember { mutableStateOf<List<Proposal>?>(null) }
    var errorString = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        getProposalsByUser(user = user,
            onSuccess = {
                proposals.value = it
            },
            onError = { exception ->
                errorString.value = exception.message
            }
        )
    }

    proposals.value?.let {
        if (it.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = "You have no book proposals. Create one on the 'search' screen to swap work with other users.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(it) { proposal ->
                    ProposalView(proposal) {
                        selectProposal(it)
                    }
                }
            }
        }
    } ?: run {
        Text(text = "Loading...")
    }
}