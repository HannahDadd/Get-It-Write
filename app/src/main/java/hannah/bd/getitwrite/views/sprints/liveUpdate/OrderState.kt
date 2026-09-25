package hannah.bd.shelfify.views.sprints.liveUpdate

enum class OrderState(
    val progress: Int,
    val statusText: String,
    val orderStatus: String,
    val delayTwenty: Long,
    val delayForty: Long,
    val delaySixty: Long
) {
    INITIALIZING(0, "Start", "Get ready to write...", 2000, 2000, 2000),
    SPRINT_ZERO(2, "Sprint", "Let's get writing!", 2000, 2000, 2000),
    SPRINT(10, "Sprint", "Let's get writing!", 120000, 240000, 360000),
    SPRINT_ONE(20, "Sprint", "Get those words written", 120000, 240000, 360000),
    STAGE_TWO(30, "Sprint", "Keep going!", 120000, 240000, 360000),
    STAGE_THREE(40, "Sprint", "Keep writing!", 120000, 240000, 360000),
    STAGE_FOUR(50, "Sprint", "Get those words written", 120000, 240000, 360000),
    STAGE_FIVE(60, "Sprint", "That's over half the writing sprint finished", 120000, 240000, 360000),
    STAGE_SIX(70, "Sprint", "Keep writing!", 120000, 240000, 360000),
    STAGE_SEVEN(80, "Sprint", "Keep going, the writing sprint is nearly done", 120000, 240000, 360000),
    STAGE_EIGHT(90, "Sprint", "Final writing push!", 120000, 240000, 360000),
    ORDER_COMPLETE(100, "Done", "Writing Sprint Finished - Fancy Another Sprint?", 120000, 240000, 360000)
}