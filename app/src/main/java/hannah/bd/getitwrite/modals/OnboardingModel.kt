package hannah.bd.shelfify.modals

data class OnboardingPage(
    val description: String,
    val description2: String
)

val onboardingPages = listOf(
    OnboardingPage(
        description = "Welcome Writer! You've stumbled across an abandoned library!",
        description2 = ""
    ),
    OnboardingPage(
        description = "With every word you write, the library will grow.",
        description2 = ""
    ),
    OnboardingPage(
        description = "Use focus mode, daily notifications and motivational widgets to stay consistent.",
        description2 = "Shelfify works fully offline so it's totally distraction free."
    ),
    OnboardingPage(
        description = "Let's get writing.",
        description2 = ""
    )
)