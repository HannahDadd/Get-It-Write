package com.example.getitwrite.modals

import com.google.firebase.Timestamp

class RequestCritique(
    val id: String,
    val title: String,
    val blurb: String,
    val genres: MutableList<String>,
    val triggerWarnings: MutableList<String>,
    val workTitle: String,
    val text: String,
    val timestamp: Timestamp,
    val writerId: String,
    val writerName: String
) {
    constructor(id: String, data: Map<String, Any>) : this(
        id = id,
        title = data.get("title") as String,
        blurb = data.get("blurb") as String,
        genres = data.get("genres") as MutableList<String>,
        triggerWarnings = data.get("triggerWarnings") as MutableList<String>,
        workTitle = data.get("workTitle") as String,
        text = data.get("text") as String,
        timestamp = data.get("timestamp") as Timestamp,
        writerId = data.get("writerId") as String,
        writerName = data.get("writerName") as String
    )
}