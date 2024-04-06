package com.example.getitwrite.modals

import com.google.firebase.Timestamp

class Forum (
    val id: String,
    val question: String,
    val questionerId: String,
    val questionerColour: Int,
    val questionerName: String,
    val upVotes: Int,
    val replies: List<Reply> = listOf<Reply>(),
    val timestamp: Timestamp
) {
    constructor(id: String, data: Map<String, Any>) : this (
        id = id,
        question = data.get("question") as String,
        questionerId = data.get("questionerId") as String,
        questionerName = data.get("questionerName") as String,
        upVotes = (data.get("upVotes") as Long).toInt(),
        questionerColour = (data.get("questionerColour") as Long).toInt(),
        timestamp = data.get("timestamp") as Timestamp
    )
}

class Reply (
    val id: String,
    val reply: String,
    val replyId: String,
    val replyName: String,
    val timestamp: Timestamp
) {
    constructor(id: String, data: Map<String, Any>) : this (
        id = id,
        reply = data.get("reply") as String,
        replyId = data.get("replyId") as String,
        replyName = data.get("replyName") as String,
        timestamp = data.get("timestamp") as Timestamp
    )
}