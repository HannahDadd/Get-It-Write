package com.example.getitwrite.modals

import com.google.firebase.Timestamp
import java.util.UUID

class Message(
    val content: String,
    val created: Timestamp,
    val senderId: String,
    val id: String
) {
    constructor(id: String, data: Map<String, Any>) : this (
        id = id,
        content = data["content"] as String,
        senderId = data["senderId"] as String,
        created = data["created"] as Timestamp
    )
}

class Chat(
    val users: MutableList<String>,
    val id: String
) {
    constructor(id: String, data: Map<String, Any>) : this(
        id = id,
        users = data["users"] as MutableList<String>
    )
}