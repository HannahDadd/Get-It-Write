package hannah.bd.getitwrite.modals

import com.google.firebase.Timestamp

class User(
    override var id: String,
    val displayName: String = "",
    val bio: String = "",
    val writing: String = "",
    val critiqueStyle: String = "",
    val authors: MutableList<String> = mutableListOf(),
    val writingGenres: MutableList<String> = mutableListOf(),
    val colour: Int = 1,
    val blockedUserIds: MutableList<String> = mutableListOf(),
    var photoURL: String = "",
    var rating: Int = 3,
    var lastCritique: Timestamp? = null,
    var lastFiveCritiques: MutableList<Timestamp>? = null,
    var frequencey: Long? = null,
) : UserGeneratedContent {
    constructor(id: String, data: Map<String, Any>) : this (
        id = id,
        displayName = data.get("displayName") as String,
        bio = data.get("bio") as String,
        writing = data.get("writing") as String,
        critiqueStyle = data.get("critiqueStyle") as String,
        authors = data.get("authors") as MutableList<String>,
        writingGenres = data.get("writingGenres") as MutableList<String>,
        colour = 2,//(data.get("colour") as Long).toInt(),
        blockedUserIds = data.get("blockedUserIds") as MutableList<String>,
        photoURL = data.get("photoURL") as String,
        rating = (data.get("rating") as Long).toInt(),
        lastCritique = data["lastCritique"] as Timestamp?,
        lastFiveCritiques = data["lastFiveCritiques"] as MutableList<Timestamp>?,
        frequencey = data.get("frequencey") as Long?,
    )
}