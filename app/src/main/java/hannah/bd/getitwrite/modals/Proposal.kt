package hannah.bd.getitwrite.modals

import com.google.firebase.Timestamp

data class Proposal(override var id: String, val data: Map<String, Any>) : UserGeneratedContent {
    val title = data["title"] as String
    val typeOfProject = data["typeOfProject"] as MutableList<String>
    val blurb = data["blurb"] as String
    val genres = data["genres"] as MutableList<String>
    val triggerWarnings = data["triggerWarnings"] as MutableList<String>
    val timestamp = data["timestamp"] as Timestamp
    val authorNotes = data["authorNotes"] as String
    val wordCount = (data["wordCount"] as Long).toInt()
    val writerId = data["writerId"] as String
    val writerName = data["writerName"] as String
}