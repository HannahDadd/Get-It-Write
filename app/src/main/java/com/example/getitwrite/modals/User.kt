package com.example.getitwrite.modals

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
}