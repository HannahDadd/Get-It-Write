package hannah.bd.getitwrite.networking

import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.modals.DatabaseNames
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.Question
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.RequestPositivity
import hannah.bd.getitwrite.modals.User

fun FirebaseRepository.loadRequestCritiques(
    onSuccess: (List<RequestCritique>) -> Unit,
    onError: (Exception) -> Unit
) {
    Firebase.firestore.collection("users/${user.id}/${DatabaseNames.requestCritiques.toString()}")
        .orderBy("timestamp", Query.Direction.DESCENDING)
        .get()
        .addOnSuccessListener { documents ->
            if (documents != null) {
                val items = documents.map { doc ->
                    RequestCritique(doc.id, doc.data)
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

fun FirebaseRepository.getQuestions(
    onSuccess: (List<Question>) -> Unit,
    onError: (Exception) -> Unit
) {
    Firebase.firestore.collection("questions")
        .orderBy("timestamp", Query.Direction.DESCENDING)
        .get()
        .addOnSuccessListener { documents ->
            if (documents != null) {
                val items = documents.map { doc ->
                    Question(doc.id, doc.data)
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

fun FirebaseRepository.getToCritiques(
    user: User,
    onSuccess: (List<RequestCritique>) -> Unit,
    onError: (Exception) -> Unit
) {
    Firebase.firestore.collection("users/${user.id}/requestCritiques")
        .orderBy("timestamp", Query.Direction.DESCENDING)
        .get()
        .addOnSuccessListener { documents ->
            if (documents != null) {
                val items = documents.map { doc ->
                    RequestCritique(doc.id, doc.data)
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

fun FirebaseRepository.getPositivities(
    onSuccess: (List<RequestPositivity>) -> Unit,
    onError: (Exception) -> Unit
) {
    Firebase.firestore.collection("users/${user.id}/${DatabaseNames.positivityPeices.toString()}")
        .get()
        .addOnSuccessListener { documents ->
            if (documents != null) {
                val items = documents.map { doc ->
                    RequestPositivity(doc.id, doc.data)
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

fun FirebaseRepository.getCritiqued(
    onSuccess: (List<Critique>) -> Unit,
    onError: (Exception) -> Unit
) {
    Firebase.firestore.collection("users/${user.id}/${DatabaseNames.critiques.toString()}")
        .orderBy("timestamp", Query.Direction.DESCENDING)
        .get()
        .addOnSuccessListener { documents ->
            if (documents != null) {
                val items = documents.map { doc ->
                    Critique(doc.id, doc.data)
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

fun FirebaseRepository.getProposals(
    onSuccess: (List<Proposal>) -> Unit,
    onError: (Exception) -> Unit
) {
    Firebase.firestore.collection("proposals")
        .whereEqualTo("writerId", user.id)
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

fun FirebaseRepository.getRecs(user: User,
                               onSuccess: (List<User>) -> Unit,
                               onError: (Exception) -> Unit) {
    Firebase.firestore.collection("users")
        //.orderBy("lastCritique", Query.Direction.ASCENDING)
        .limit(10).get()
        .addOnSuccessListener { documents ->
            if (documents != null) {
                val items = documents.map { doc ->
                    User(doc.id, doc.data)
                }
                user.frequencey?.let {freq ->
                    val recs = items.filter { it.frequencey != null }
                        .sortedBy { kotlin.math.abs(it.frequencey!! - freq) }
                    if (recs.size < 4) {
                        onSuccess(items.subList(0, 3))
                    } else {
                        onSuccess(recs.subList(0, 3))
                    }
                } ?: {
                    onSuccess(items.subList(0, 3))
                }
            } else {
                onError(Exception("Data not found"))
            }
        }
        .addOnFailureListener { exception ->
            onError(exception)
        }
}