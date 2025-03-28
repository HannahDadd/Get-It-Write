package hannah.bd.getitwrite.modals

import com.google.firebase.Timestamp

data class RequestCritique(override var id: String, val data: Map<String, Any>) : UserGeneratedContent {
    val title = data["title"] as String
    val blurb = data["blurb"] as String
    val genres = data["genres"] as MutableList<String>
    val triggerWarnings = data["triggerWarnings"] as MutableList<String>
    val workTitle = data["workTitle"] as String
    val text = data["text"] as String
    val timestamp = data["timestamp"] as Timestamp
    val writerId = data["writerId"] as String
    val writerName = data["writerName"] as String
}