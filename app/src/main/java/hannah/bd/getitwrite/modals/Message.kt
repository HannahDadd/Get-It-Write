package hannah.bd.getitwrite.modals

import com.google.firebase.Timestamp

class Message(
    override var id: String,
    val content: String,
    val created: Timestamp,
    val senderId: String
) : UserGeneratedContent {
    constructor(id: String, data: Map<String, Any>) : this (
        id = id,
        content = data["content"] as String,
        senderId = data["senderId"] as String,
        created = data["created"] as Timestamp
    )
}

class Chat(
    override var id: String,
    val users: MutableList<String>
) : UserGeneratedContent {
    constructor(id: String, data: Map<String, Any>) : this(
        id = id,
        users = data["users"] as MutableList<String>
    )
}