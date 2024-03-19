package com.example.getitwrite.modals

import com.google.firebase.Timestamp

class Proposal (
    val id: String,
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
) {
    constructor(id: String, data: Map<String, Any>) : this(
        id = id,
        title = data.get("title") as String,
        typeOfProject = data.get("typeOfProject") as MutableList<String>,
        blurb = data.get("blurb") as String,
        genres = data.get("genres") as MutableList<String>,
        triggerWarnings = data.get("triggerWarnings") as MutableList<String>,
        timestamp = data.get("timestamp") as Timestamp,
        authorNotes = data.get("authorNotes") as String,
        wordCount = 2,//data.get("wordCount") as Int,
        writerId = data.get("writerId") as String,
        writerName = data.get("writerName") as String
    ) {

    }
}