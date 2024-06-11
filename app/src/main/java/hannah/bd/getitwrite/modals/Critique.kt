package hannah.bd.getitwrite.modals

import com.google.firebase.Timestamp

class Critique (
    override var id: String,
    val comments: Map<String, Long>,
    val overallFeedback: String,
    val critiquerId: String,
    val text: String,
    val title: String,
    val projectTitle: String,
    val critiquerName: String,
    val critiquerProfileColour: Int,
    val timestamp: Timestamp,
    val rated: Boolean
) : UserGeneratedContent {
    constructor(id: String, data: Map<String, Any>) : this (
    id = id,
    comments = data["comments"] as Map<String, Long>,
    text = data.get("text") as String,
    projectTitle = data.get("projectTitle") as String,
    title = data.get("title") as String,
    overallFeedback = data.get("overallFeedback") as String,
    critiquerId = data.get("critiquerId") as String,
    critiquerName = data.get("critiquerName") as String,
    critiquerProfileColour = 2,//(data.get("colour") as Long).toInt(),
    timestamp = data.get("timestamp") as Timestamp,
    rated = data.get("rated") as Boolean
    )
}
