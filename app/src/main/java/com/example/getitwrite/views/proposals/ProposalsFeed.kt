package com.example.getitwrite.views.proposals

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import com.example.getitwrite.modals.Proposal
import com.example.getitwrite.views.components.ErrorText
import com.example.getitwrite.views.components.TagCloud
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun ProposalsFeed() {
    val proposals = remember { mutableStateOf(ArrayList<Proposal>()) }
    val errorString = remember { mutableStateOf("") }
    Firebase.firestore.collection("proposals")
        .orderBy("timestamp")
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                proposals.value.add(Proposal(id = document.id, data = document.data))
            }
        }
        .addOnFailureListener { exception ->
            errorString.value = exception.toString()
        }
    LazyColumn {
        items(proposals.value) { proposal ->
            ProposalView(proposal)
        }
    }
}

@Composable
fun ProposalView(proposal: Proposal) {
    println("XXXX we got here")
    Column {
        Text(proposal.title, fontWeight = FontWeight.Bold)
        Text(proposal.blurb)
        Text(proposal.typeOfProject.joinToString(", "), fontWeight = FontWeight.Light)
        TagCloud(tags = proposal.genres, action = null)
        Row {
            Text(
                text = DateUtils.getRelativeTimeSpanString(
                    (proposal.timestamp.seconds * 1000),
                    System.currentTimeMillis(),
                    DateUtils.DAY_IN_MILLIS
                ).toString(),
                fontWeight = FontWeight.Light
            )
            Text(
                text = "${proposal.wordCount} words",
                fontWeight = FontWeight.Light
            )
        }
        Text(proposal.title, fontWeight = FontWeight.Light)
    }
}

@Composable
fun ExpandedProposalView(proposal: Proposal) {
    val proposals = remember { mutableStateOf(ArrayList<Proposal>()) }
    val errorString = remember { mutableStateOf("") }
    Firebase.firestore.collection("proposals")
        .orderBy("timestamp")
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                proposals.value.add(Proposal(id = document.id, data = document.data))
            }
        }
        .addOnFailureListener { exception ->
            errorString.value = exception.toString()
        }
    LazyColumn {
        items(proposals.value) { proposal ->
            ProposalView(proposal)
        }
    }
}