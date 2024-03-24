package com.example.getitwrite.modals

import com.google.firebase.Timestamp
import java.util.TreeMap

class User(
    val id: String,
    val displayName: String,
    val bio: String,
    val writing: String,
    val critiqueStyle: String,
    val authors: MutableList<String>,
    val writingGenres: MutableList<String>,
    val colour: Int
) {
    var photoURL = String
    var rating = Int
    var blockedUserIds = mutableListOf <String>()

    constructor(id: String, data: Map<String, Any>) : this (
        id = id,
        displayName = data.get("displayName") as String,
        bio = data.get("bio") as String,
        writing = data.get("writing") as String,
        critiqueStyle = data.get("critiqueStyle") as String,
        authors = data.get("authors") as MutableList<String>,
        writingGenres = data.get("writingGenres") as MutableList<String>,
        colour = data.get("colour") as Int
    )
}