package hannah.bd.getitwrite.networking

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.modals.Chat
import hannah.bd.getitwrite.modals.User

fun FirebaseRepository.getUser(
    userID: String,
    onSuccess: (User) -> Unit,
    onError: (Exception) -> Unit
) {
    Firebase.firestore.collection("users")
        .document(userID)
        .get()
        .addOnSuccessListener { doc ->
            if (doc != null) {
                onSuccess(User(id = doc.id, data = doc.data!!))
            } else {
                onError(Exception("Data not found"))
            }
        }
        .addOnFailureListener { exception ->
            onError(exception)
        }
}

fun FirebaseRepository.getChats(
    user: User,
    onSuccess: (List<Chat>) -> Unit,
    onError: (Exception) -> Unit
) {
    Firebase.firestore.collection("chats")
        .whereArrayContains("users", user.id)
        .get()
        .addOnSuccessListener { documents ->
            if (documents != null) {
                val items = documents.map { doc ->
                    Chat(doc.id, doc.data)
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

