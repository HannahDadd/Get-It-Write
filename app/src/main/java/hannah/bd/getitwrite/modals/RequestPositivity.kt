package hannah.bd.getitwrite.modals

import com.google.firebase.Timestamp

data class RequestPositivity(override var id: String, val data: Map<String, Any>) : UserGeneratedContent {
    val text = data["text"] as String
    val writerId = data["writerId"] as String
    val writerName = data["writerName"] as String
    val comments = data["comments"] as Map<String, String>
}