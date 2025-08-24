package hannah.bd.getitwrite.modals

import java.util.Date

data class WeeklyCommitment(
    val writingDays: List<String>,
    val time: Date
)