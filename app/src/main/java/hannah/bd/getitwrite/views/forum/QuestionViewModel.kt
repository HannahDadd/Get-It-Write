//package hannah.bd.getitwrite.views.forum
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.google.firebase.Firebase
//import com.google.firebase.firestore.Query
//import com.google.firebase.firestore.QuerySnapshot
//import com.google.firebase.firestore.firestore
//import hannah.bd.getitwrite.modals.Question
//import hannah.bd.getitwrite.modals.Reply
//
//class QuestionViewModel {
//    var replies: MutableLiveData<List<Reply>> = MutableLiveData()
//
//    fun getReplies(questionId: String): LiveData<List<Reply>> {
//        Firebase.firestore.collection("questions")
//            .orderBy("timestamp", Query.Direction.DESCENDING)
//            .get()
//            .addOnSuccessListener { documents ->
//                if (documents != null) {
//                    val items = documents.map { doc ->
//                        Question(doc.id, doc.data)
//                    }
//                    onSuccess(items)
//                } else {
//                    onError(Exception("Data not found"))
//                }
//            }
//            .addOnFailureListener { exception ->
//                onError(exception)
//            }
//        Firebase.firestore.collection("questions").document(questionId)
//            .collection("replies").addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
//                if (e != null) {
//                    replies.value = null
//                    return@EventListener
//                }
//
//                var savedLists: MutableList<Reply> = mutableListOf()
//                for (doc in value!!) {
//                    savedLists.add(Reply(doc.id, doc.data!!))
//                }
//                replies.value = savedLists
//            })
//
//        return replies
//    }
//}