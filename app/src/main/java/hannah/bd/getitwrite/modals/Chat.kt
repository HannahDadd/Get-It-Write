package hannah.bd.getitwrite.modals

import com.google.firebase.Timestamp

data class Chat(override var id: String, val data: Map<String, Any>) : UserGeneratedContent {
    val users = data["users"] as MutableList<String>
}