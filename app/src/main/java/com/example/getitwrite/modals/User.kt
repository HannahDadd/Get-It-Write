package com.example.getitwrite.modals

import com.google.firebase.Timestamp
import java.util.TreeMap

class User(
    override var id: String,
    val displayName: String = "",
    val bio: String = "",
    val writing: String = "",
    val critiqueStyle: String = "",
    val authors: MutableList<String> = mutableListOf(),
    val writingGenres: MutableList<String> = mutableListOf(),
    val colour: Int = 1,
    val blockedUserIds: MutableList<String> = mutableListOf(),
    var photoURL: String = "",
    var rating: Int = 3
) : UserGeneratedContent {
    constructor(id: String, data: Map<String, Any>) : this (
        id = id,
        displayName = data.get("displayName") as String,
        bio = data.get("bio") as String,
        writing = data.get("writing") as String,
        critiqueStyle = data.get("critiqueStyle") as String,
        authors = data.get("authors") as MutableList<String>,
        writingGenres = data.get("writingGenres") as MutableList<String>,
        colour = 2,//(data.get("colour") as Long).toInt(),
        blockedUserIds = data.get("blockedUserIds") as MutableList<String>,
        photoURL = data.get("photoURL") as String,
        rating = (data.get("rating") as Long).toInt()
    )
}