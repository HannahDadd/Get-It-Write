package com.example.getitwrite.views.critiqueFrenzy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.getitwrite.Colours
import com.example.getitwrite.modals.Message
import com.example.getitwrite.modals.Proposal
import com.example.getitwrite.modals.Question
import com.example.getitwrite.modals.RequestCritique
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.ErrorText
import com.example.getitwrite.views.messages.SelectProposalView
import com.example.getitwrite.views.proposals.ProposalView
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeFrenzyView(user: User, proposals: List<Proposal>, onSuccess: (RequestCritique) -> Unit) {
    var errorString = remember { mutableStateOf("") }
    val title = remember { mutableStateOf("") }
    val text = remember { mutableStateOf("") }
    var proposal = remember { mutableStateOf<Proposal?>(null) }
    val sheetState = rememberModalBottomSheetState()
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
                SelectProposalView(proposals = proposals, user) {
                    proposal.value = it
                    showBottomSheet = false
                }
            }
        }
        TextButton(onClick = { showBottomSheet = true }) {
            Text(modifier = Modifier.align(Alignment.Bottom),
                text = "Choose proposal", color = Colours.Dark_Readable, fontWeight = FontWeight.Bold)
        }
        proposal.value?.let {
            ProposalView(it) {
                proposal.value = null
            }
        }
        OutlinedTextField(value = title.value, maxLines = 1, onValueChange = { title.value = it },
            label = {
                Box {
                    Text(text = "Critique Title e.g. Chaper 1")
                }
            }
        )
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
                proposal.value?.let {
                    val id = UUID.randomUUID().toString()
                    val requestCritique = RequestCritique(id = id, title = it.title, blurb = it.blurb, genres = it.genres, triggerWarnings = it.triggerWarnings, workTitle = title.value, text = text.value, timestamp = Timestamp.now(), writerId = user.id, writerName = user.displayName)
                    Firebase.firestore.collection("frenzy").document(id).set(requestCritique)
                        .addOnSuccessListener {
                            onSuccess(requestCritique)
                        }
                        .addOnFailureListener {
                            errorString.value = it.message.toString()
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