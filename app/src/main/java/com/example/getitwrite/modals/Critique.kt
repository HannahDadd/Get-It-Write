package com.example.getitwrite.modals

import com.google.firebase.Timestamp
import java.util.TreeMap

class Critique (
    val id: String,
    val comments: Map<String, Any>,
    val overallFeedback: String,
    val critiquerId: String,
    val text: String,
    val title: String,
    val projectTitle: String,
    val critiquerName: String,
    val critiquerProfileColour: Int,
    val timestamp: Timestamp,
    val rated: Boolean
) {
    constructor(id: String, data: Map<String, Any>) : this (
    id = id,
    comments = data.get("comments") as Map<String, Any>,
    text = data.get("text") as String,
    projectTitle = data.get("projectTitle") as String,
    title = data.get("title") as String,
    overallFeedback = data.get("overallFeedback") as String,
    critiquerId = data.get("critiquerId") as String,
    critiquerName = data.get("critiquerName") as String,
    critiquerProfileColour = data.get("critiquerProfileColour") as Int,
    timestamp = data.get("timestamp") as Timestamp,
    rated = data.get("rated") as Boolean
    )
}
