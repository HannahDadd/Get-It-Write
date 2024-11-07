package hannah.bd.getitwrite.modals

enum class DatabaseNames(val field: String) {
    users("users"),
    projects("projects"),
    requestCritiques("requestCritiques"),
    critiques("critiques"),
    critiqueFrenzy("frenzy"),
    queryFrenzy("queries"),
    proposals("proposals"),
    positivityPeices("positivityPeices"),
    questions("questions"),
    replies("replies"),
    reportedContent("reportedContent"),
    messages("messages")
}