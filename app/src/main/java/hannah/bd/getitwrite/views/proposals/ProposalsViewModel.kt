package hannah.bd.getitwrite.views.proposals

import androidx.lifecycle.ViewModel
import hannah.bd.getitwrite.modals.Proposal
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.modals.RequestPositivity
import hannah.bd.getitwrite.modals.User
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

fun getProposalsByUserId(id: String, onSuccess: (List<Proposal>) -> Unit,
                        onError: (Exception) -> Unit) {
    Firebase.firestore.collection("proposals")
        .whereEqualTo("writerId", id)
        .get()
        .addOnSuccessListener { documents ->
            if (documents != null) {
                val items = documents.map { doc ->
                    Proposal(doc.id, doc.data)
                }
                onSuccess(items)
            } else {
                onError(Exception("Data not found"))
            }
        }
        .addOnFailureListener { exception ->
            onError(exception)
        }
}

fun getProposalsByGenre(genre: String, onSuccess: (List<Proposal>) -> Unit,
                        onError: (Exception) -> Unit) {
    if (genre == "All") {
        Firebase.firestore.collection("proposals")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    val items = documents.map { doc ->
                        Proposal(doc.id, doc.data)
                    }
                    onSuccess(items)
                } else {
                    onError(Exception("Data not found"))
                }
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    } else {
        Firebase.firestore.collection("proposals")
            .whereArrayContains("genres", genre)
            .get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    val items = documents.map { doc ->
                        Proposal(doc.id, doc.data)
                    }
                    onSuccess(items)
                } else {
                    onError(Exception("Data not found"))
                }
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }
}

fun getProposalsByUser(user: User, onSuccess: (List<Proposal>) -> Unit,
                        onError: (Exception) -> Unit) {
    Firebase.firestore.collection("proposals")
        .whereEqualTo("writerId", user.id)
        .orderBy("timestamp", Query.Direction.DESCENDING)
        .get()
        .addOnSuccessListener { documents ->
            if (documents != null) {
                val items = documents.map { doc ->
                    Proposal(doc.id, doc.data)
                }
                onSuccess(items)
            } else {
                onError(Exception("Data not found"))
            }
        }
        .addOnFailureListener { exception ->
            onError(exception)
        }
}