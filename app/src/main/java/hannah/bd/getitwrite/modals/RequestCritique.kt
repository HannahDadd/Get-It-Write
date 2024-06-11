package hannah.bd.getitwrite.modals

import com.google.firebase.Timestamp

class RequestCritique(
    override var id: String,
    val title: String,
    val blurb: String,
    val genres: MutableList<String>,
    val triggerWarnings: MutableList<String>,
    val workTitle: String,
    val text: String,
    val timestamp: Timestamp,
    val writerId: String,
    val writerName: String
) : UserGeneratedContent {
    constructor(id: String, data: Map<String, Any>) : this(
        id = id,
        title = data["title"] as String,
        blurb = data["blurb"] as String,
        genres = data["genres"] as MutableList<String>,
        triggerWarnings = data["triggerWarnings"] as MutableList<String>,
        workTitle = data["workTitle"] as String,
        text = data["text"] as String,
        timestamp = data["timestamp"] as Timestamp,
        writerId = data["writerId"] as String,
        writerName = data["writerName"] as String
    )
}