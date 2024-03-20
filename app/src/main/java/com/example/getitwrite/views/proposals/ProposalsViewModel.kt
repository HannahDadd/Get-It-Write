package com.example.getitwrite.views.proposals

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getitwrite.modals.Proposal
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await

//sealed class ProposalsUiState {
//    data class Success(val news: List<Proposal>): ProposalsUiState()
//    data class Error(val exception: Throwable): ProposalsUiState()
//}
//class FirebaseRepository() {
//    fun getProposals(): StateFlow<ProposalsUiState> {
//        val proposals = MutableStateFlow(ProposalsUiState.Success(emptyList()))
//        Firebase.firestore.collection("proposals")
//            .orderBy("timestamp")
//            .get()
//            .addOnSuccessListener { documents ->
//                proposals.value = ProposalsUiState.Success(documents.map { Proposal(id = it.id, it.data) })
//            }
//            .addOnFailureListener { exception ->
////                proposals.value = ProposalsUiState.Error(exception)
//            }
//        return proposals
//    }
//}
//private object StateHoistingSnippets4 {
//    @Composable
//    private fun ProposalList(propsalsState: ProposalsUiState) {
//    }
//    class ProposalsViewModel(
//        firebaseRepository: FirebaseRepository = FirebaseRepository()
//    ) : ViewModel() {
//
//        val proposals = firebaseRepository
//            .getProposals()
//            .stateIn()
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5_000),
//                intialValue = MutableStateFlow(ProposalsUiState.Success(emptyList()))
//            )
//    }
//}

class ProposalsViewModel : ViewModel() {
    private val db = Firebase.firestore
    val proposalsFlow = flow {
        val documents = db.collection("proposals").get().await()
        val items = documents.map { doc ->
            Proposal(doc.id, doc.data)
        }
        emit(items)
    }
}