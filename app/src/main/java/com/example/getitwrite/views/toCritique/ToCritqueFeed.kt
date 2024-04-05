package com.example.getitwrite.views.toCritique

import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.getitwrite.modals.Critique
import com.example.getitwrite.modals.Proposal
import com.example.getitwrite.modals.RequestCritique
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.FindPartnersText
import com.example.getitwrite.views.components.TagCloud
import com.example.getitwrite.views.messages.ChatView
import com.example.getitwrite.views.messages.ChatViewViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@Composable
fun ToCritiqueFeed(user: User, viewModel: ToCritiqueViewModel, selectChat: (String, String) -> Unit) {
    val toCritiques by viewModel.chatsFlow.collectAsState(initial = emptyList())
    if (toCritiques.isEmpty()) {
        Column(Modifier.padding(10.dp)) {
            Text("You have nothing to critique.", fontWeight = FontWeight.Bold)
            FindPartnersText()
        }
    } else {
        LazyColumn(Modifier.padding(10.dp)) {
            items(toCritiques) { work ->
            }
        }
    }
}

@Composable
fun ToCritiqueView(requestCritique: RequestCritique, selectProposal: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(10.dp)
            .clickable { selectProposal(requestCritique.id) }) {
        Text(requestCritique.title, fontWeight = FontWeight.Bold)
        Text(requestCritique.blurb)
//        Text(proposal.typeOfProject.joinToString(", "), fontWeight = FontWeight.Light)
//        TagCloud(tags = proposal.genres, action = null)
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding()
//        ) {
//            Text(
//                text = DateUtils.getRelativeTimeSpanString(
//                    (proposal.timestamp.seconds * 1000),
//                    System.currentTimeMillis(),
//                    DateUtils.DAY_IN_MILLIS
//                ).toString(),
//                fontWeight = FontWeight.Light
//            )
//            Spacer(modifier = Modifier.weight(1.0f))
//            Text(
//                text = "${proposal.wordCount} words",
//                fontWeight = FontWeight.Light
//            )
//        }
//        Divider()
    }
}

class ToCritiqueViewModel(user: User) : ViewModel() {
    val chatsFlow = flow {
        val documents = Firebase.firestore.collection("users")
            .document(user.id).collection("requestCritiques")
            .orderBy("timestamp", Query.Direction.DESCENDING).get().await()
        val items = documents.map { doc ->
            RequestCritique(doc.id, doc.data)
        }
        emit(items)
    }
}