package hannah.bd.shelfify.views.sprints.liveUpdate

import android.os.Handler
import android.os.Looper

class WritingSprintTracker(
    private val delayLength: String,
    private val onStateChanged: (OrderState) -> Unit,
    private val onCompleted: () -> Unit
) {
    private val handler = Handler(Looper.getMainLooper())
    private var currentOrderState = 0
    private var isTracking = false

    fun startTracking() {
        isTracking = true
        currentOrderState = 0
        scheduleNextUpdate()
    }

    fun stopTracking() {
        isTracking = false
        handler.removeCallbacksAndMessages(null)
    }

    private fun scheduleNextUpdate() {
        if (!isTracking) return

        val orderStates = OrderState.entries
        if (currentOrderState >= orderStates.size) {
            // Order complete - immediately notify completion
            // (Handler will manage display duration before dismissal)
            onCompleted()
            return
        }

        val state = orderStates[currentOrderState]

        if (delayLength == "20mins") {
            handler.postDelayed({
                onStateChanged(state)
                currentOrderState++
                scheduleNextUpdate()
            }, state.delayTwenty)
        }

        if (delayLength == "40mins") {
            handler.postDelayed({
                onStateChanged(state)
                currentOrderState++
                scheduleNextUpdate()
            }, state.delayForty)
        }

        if (delayLength == "60mins") {
            handler.postDelayed({
                onStateChanged(state)
                currentOrderState++
                scheduleNextUpdate()
            }, state.delaySixty)
        }
    }

    fun getCurrentState(): OrderState? {
        val orderStates = OrderState.entries
        return if (currentOrderState > 0 && currentOrderState <= orderStates.size) {
            orderStates[currentOrderState - 1]
        } else {
            null
        }
    }
}