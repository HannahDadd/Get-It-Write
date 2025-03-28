package hannah.bd.getitwrite.modals

import com.google.firebase.Timestamp

data class Question(override var id: String, val data: Map<String, Any>) : UserGeneratedContent {
    val question = data.get("question") as String
    val questionerId = data.get("questionerId") as String
    val questionerName = data.get("questionerName") as String
    val questionerColour = (data.get("questionerColour") as Long).toInt()
    val timestamp = data.get("timestamp") as Timestamp
}