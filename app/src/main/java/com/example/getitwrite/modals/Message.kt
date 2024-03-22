package com.example.getitwrite.modals

import com.google.firebase.Timestamp

class Message(
    val content: String,
    val created: Timestamp,
    val senderID: String
) {
    constructor(data: Map<String, Any>) : this (
        content = data.get("content") as String,
        senderID = data.get("senderID") as String,
        created = data.get("created") as Timestamp
    )
}

class Chat(
    val users: MutableList<String>
) {
    constructor(data: Map<String, Any>) : this(
        users = data.get("users") as MutableList<String>
    )
}