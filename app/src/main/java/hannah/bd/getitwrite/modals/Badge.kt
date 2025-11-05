package hannah.bd.getitwrite.modals

data class Badge(val id: Int, val score: Int, val title: String)

enum class BadgeTitles(val rawValue: String) {
    PromptsUsed("prompts used"),
    WordsLearnt("words learnt"),
    Projects("projects"),
    QueriesSent("queries sent"),
    FullRequest("full request"),
    BooksPublished("books published")
}

fun popUpText(badgeName: BadgeTitles): String {
    return when (badgeName) {
        BadgeTitles.PromptsUsed -> "Use the writing prompts to create short stories."
        BadgeTitles.WordsLearnt -> "Learn new words in the vocab checker."
        BadgeTitles.Projects -> "Create WIPs in the app to track them."
        BadgeTitles.QueriesSent -> "Sent off some queries? Congrats! Add them here."
        BadgeTitles.FullRequest -> "Received a full request? What a pro!"
        BadgeTitles.BooksPublished -> "You're on to a winner with that one."
    }
}

fun popUpButton(badgeName: BadgeTitles): Boolean {
    return when (badgeName) {
        BadgeTitles.PromptsUsed,
        BadgeTitles.WordsLearnt,
        BadgeTitles.Projects -> true
        else -> false
    }
}
