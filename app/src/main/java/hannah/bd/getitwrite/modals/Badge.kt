package hannah.bd.getitwrite.modals

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.vector.ImageVector

enum class Badge(val key: String) {

    TWENTY_SPRINT("twentySprint"),
    FORTY_SPRINT("fortySprint"),
    HOUR_SPRINT("hourSprint"),

    WORD_NERD_200("wordNerd200"),
    WORD_NERD_500("wordNerd500"),
    WORD_NERD_1000("wordNerd1000"),
    WORD_NERD_10000("wordNerd10000"),
    WORD_NERD_20000("wordNerd20000"),
    WORD_NERD_50000("wordNerd50000"),

    STREAK_FREAK_2("streakFreak2"),
    STREAK_FREAK_7("streakFreak7"),
    STREAK_FREAK_14("streakFreak14"),
    STREAK_FREAK_31("streakFreak31"),
    STREAK_FREAK_100("streakFreak100"),

    QUICK_WORDS_250("quickWords250"),
    QUICK_WORDS_500("quickWords500"),
    QUICK_WORDS_1000("quickWords1000"),
    QUICK_WORDS_2000("quickWords2000"),
    QUICK_WORDS_5000("quickWords5000"),

    BOOK_GOAL("bookGoal");

    fun getIcon(): ImageVector {
        return when (this) {
            TWENTY_SPRINT -> Icons.Default.Edit
            FORTY_SPRINT -> Icons.Default.Edit
            HOUR_SPRINT -> Icons.Default.Edit

            WORD_NERD_200, WORD_NERD_500, WORD_NERD_1000,
            WORD_NERD_10000, WORD_NERD_20000, WORD_NERD_50000 -> Icons.Default.Edit

            STREAK_FREAK_2, STREAK_FREAK_7, STREAK_FREAK_14,
            STREAK_FREAK_31, STREAK_FREAK_100 -> Icons.Default.Edit

            QUICK_WORDS_250, QUICK_WORDS_500, QUICK_WORDS_1000,
            QUICK_WORDS_2000, QUICK_WORDS_5000 -> Icons.Default.Edit

            BOOK_GOAL -> Icons.Default.Edit
        }
    }

    fun getText(): String {
        return when (this) {
            TWENTY_SPRINT -> "20 minute"
            FORTY_SPRINT -> "40 minute"
            HOUR_SPRINT -> "1 hour"

            WORD_NERD_200 -> "Write 200 words in total in the app"
            WORD_NERD_500 -> "Write 500 words in total in the app"
            WORD_NERD_1000 -> "Write 1000 words in total in the app"
            WORD_NERD_10000 -> "Write 10,000 words in total in the app"
            WORD_NERD_20000 -> "Write 20,000 words in total in the app"
            WORD_NERD_50000 -> "Write 50,000 words in total in the app"

            STREAK_FREAK_2 -> "Get a 2 day streak"
            STREAK_FREAK_7 -> "Get a 7 day streak"
            STREAK_FREAK_14 -> "Get a 14 day streak"
            STREAK_FREAK_31 -> "Get a 31 day streak"
            STREAK_FREAK_100 -> "Get a 100 day streak"

            QUICK_WORDS_250 -> "Write 250 words in a sprint"
            QUICK_WORDS_500 -> "Write 500 words in a sprint"
            QUICK_WORDS_1000 -> "Write 1000 words in a sprint"
            QUICK_WORDS_2000 -> "Write 2000 words in a sprint"
            QUICK_WORDS_5000 -> "Write 5000 words in a sprint"

            BOOK_GOAL -> "Hit your book's target word count"
        }
    }
}