package hannah.bd.getitwrite.modals

import com.google.firebase.Timestamp

data class User(override var id: String, val data: Map<String, Any>) : UserGeneratedContent {
    val displayName = data.get("displayName") as String
    val bio = data.get("bio") as String
    val writing = data.get("writing") as String
    val critiqueStyle = data.get("critiqueStyle") as String
    val authors = data.get("authors") as MutableList<String>
    val writingGenres = data.get("writingGenres") as MutableList<String>
    val colour = 2//(data.get("colour") as Long).toInt()
    val blockedUserIds = data.get("blockedUserIds") as MutableList<String>
    val photoURL = data.get("photoURL") as String
    val rating = (data.get("rating") as Long).toInt()
    val lastCritique = data["lastCritique"] as Timestamp?
    val lastFiveCritiques = data["lastFiveCritiques"] as MutableList<Timestamp>?
    val frequencey = data.get("frequencey") as Double?
    val critiquerExpected = data.get("critiquerExpected") as String?
}