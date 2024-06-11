package hannah.bd.getitwrite.modals

interface UserGeneratedContent {
    var id: String
}

enum class ContentToReportType {
    questions,
    proposals,
    CRITIQUE,
    REQUESTCRITIQUE,
    OTHER
}