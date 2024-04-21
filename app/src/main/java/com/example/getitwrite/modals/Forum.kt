package com.example.getitwrite.modals

import com.google.firebase.Timestamp

class Question (
    val id: String,
    val question: String,
    val questionerId: String,
    val questionerColour: Int,
    val questionerName: String,
    val timestamp: Timestamp
) {
    constructor(id: String, data: Map<String, Any>) : this (
        id = id,
        question = data.get("question") as String,
        questionerId = data.get("questionerId") as String,
        questionerName = data.get("questionerName") as String,
        questionerColour = (data.get("questionerColour") as Long).toInt(),
        timestamp = data.get("timestamp") as Timestamp
    )
}

class Reply (
    val id: String,
    val reply: String,
    val replierId: String,
    val replierName: String,
    val replierColour: Int,
    val timestamp: Timestamp
) {
    constructor(id: String, data: Map<String, Any>) : this (
        id = id,
        reply = data.get("reply") as String,
        replierId = data.get("replierId") as String,
        replierName = data.get("replierName") as String,
        replierColour = (data.get("replierColour") as Long).toInt(),
        timestamp = data.get("timestamp") as Timestamp
    )
}