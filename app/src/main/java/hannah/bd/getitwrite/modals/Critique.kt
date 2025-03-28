package hannah.bd.getitwrite.modals

import com.google.firebase.Timestamp

data class Critique(override var id: String, val data: Map<String, Any>) : UserGeneratedContent {
    val comments = data["comments"] as Map<String, Long>
    val text = data.get("text") as String
    val projectTitle = data.get("projectTitle") as String
    val title = data.get("title") as String
    val overallFeedback = data.get("overallFeedback") as String
    val critiquerId = data.get("critiquerId") as String
    val critiquerName = data.get("critiquerName") as String
    val critiquerProfileColour = 2 //(data.get("colour") as Long).toInt(),
    val timestamp = data.get("timestamp") as Timestamp
    val rated = data.get("rated") as Boolean
}