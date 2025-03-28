package hannah.bd.getitwrite.modals

import com.google.firebase.Timestamp

data class Message(override var id: String, val data: Map<String, Any>) : UserGeneratedContent {
    val content = data["content"] as String
    val senderId = data["senderId"] as String
    val created = data["created"] as Timestamp
}
