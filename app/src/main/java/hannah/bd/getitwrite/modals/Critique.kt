package hannah.bd.getitwrite.modals

import com.google.firebase.Timestamp

//class Critique (
//    override var id: String,
//    val comments: Map<String, Long>,
//    val overallFeedback: String,
//    val critiquerId: String,
//    val text: String,
//    val title: String,
//    val projectTitle: String,
//    val critiquerName: String,
//    val critiquerProfileColour: Int,
//    val timestamp: Timestamp,
//    val rated: Boolean
//) : UserGeneratedContent {
//    constructor(id: String, data: Map<String, Any>) : this (
//    id = id,
//    comments = data["comments"] as Map<String, Long>,
//    text = data.get("text") as String,
//    projectTitle = data.get("projectTitle") as String,
//    title = data.get("title") as String,
//    overallFeedback = data.get("overallFeedback") as String,
//    critiquerId = data.get("critiquerId") as String,
//    critiquerName = data.get("critiquerName") as String,
//    critiquerProfileColour = 2,//(data.get("colour") as Long).toInt(),
//    timestamp = data.get("timestamp") as Timestamp,
//    rated = data.get("rated") as Boolean
//    )
//}

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

//data class Thing(
//    override var id: String,
//    val data: Map<String, Any>,
//    val comments: Map<String, Long> = data["comments"] as Map<String, Long>,
//    val text: String = data.get("text") as String,
//    val projectTitle: String = data.get("projectTitle") as String,
//    val title: String = data.get("title") as String,
//    val overallFeedback: String = data.get("overallFeedback") as String,
//    val critiquerId: String = data.get("critiquerId") as String,
//    val critiquerName: String = data.get("critiquerName") as String,
//    val critiquerProfileColour: Int = 2, //(data.get("colour") as Long).toInt(),
//    val timestamp: Timestamp = data.get("timestamp") as Timestamp,
//    val rated: Boolean = data.get("rated") as Boolean
//) : UserGeneratedContent
