package hannah.bd.getitwrite.modals

import java.util.Date

data class Stat(
    val id: Int,
    val wordsWritten: Int,
    val date: Date,
    val wipId: Int?, // Nullable just like Swift's optional
    val minutes: Int
)
