package hannah.bd.getitwrite.modals

import com.google.firebase.Timestamp

data class Reply(override var id: String, val data: Map<String, Any>) : UserGeneratedContent {
    val reply = data.get("reply") as String
    val replierId = data.get("replierId") as String
    val replierName = data.get("replierName") as String
    val replierColour = (data.get("replierColour") as Long).toInt()
    val timestamp = data.get("timestamp") as Timestamp
}