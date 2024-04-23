package com.example.getitwrite.modals

import com.google.firebase.Timestamp

class Proposal (
    override var id: String,
    val title: String,
    val typeOfProject: MutableList<String>,
    val blurb: String,
    val genres: MutableList<String>,
    val triggerWarnings: MutableList<String>,
    val timestamp: Timestamp,
    val authorNotes: String,
    val wordCount: Int,
    val writerId: String,
    val writerName: String
) : UserGeneratedContent {
    constructor(id: String, data: Map<String, Any>) : this(
        id = id,
        title = data["title"] as String,
        typeOfProject = data["typeOfProject"] as MutableList<String>,
        blurb = data["blurb"] as String,
        genres = data["genres"] as MutableList<String>,
        triggerWarnings = data["triggerWarnings"] as MutableList<String>,
        timestamp = data["timestamp"] as Timestamp,
        authorNotes = data["authorNotes"] as String,
        wordCount = (data["wordCount"] as Long).toInt(),
        writerId = data["writerId"] as String,
        writerName = data["writerName"] as String
    ) {}
}