package com.example.getitwrite.views.proposals

import androidx.lifecycle.ViewModel
import com.example.getitwrite.modals.Proposal
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ProposalsViewModel : ViewModel() {
    val proposalsFlow = flow {
        val documents = Firebase.firestore.collection("proposals")
            .orderBy("timestamp")
            .get().await()
        val items = documents.map { doc ->
            Proposal(doc.id, doc.data)
        }
        emit(items)
    }
}