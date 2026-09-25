package hannah.bd.shelfify.views.sprints.liveUpdate

import android.Manifest
import android.app.Notification
import android.app.Service
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresPermission

class WritingSprintServiceHandler(
    private val service: Service,
    private val notificationManager: LiveUpdateNotificationManager,
    private val onComplete: () -> Unit = {}
) {
    private var writingSprintTracker: WritingSprintTracker? = null
    private var isActive = false
    private val handler = Handler(Looper.getMainLooper())

    companion object {
        private const val TAG = "WritingSprintHandler"
        private const val COMPLETION_DISPLAY_DURATION = 3000L // Show completion for 3 seconds
    }

    fun start(delayLength: String): Notification {
        Log.d(TAG, "Starting writing sprint tracking")
        isActive = true

        // Initialize the tracker with callbacks
        writingSprintTracker = WritingSprintTracker(
            delayLength = delayLength,
            onStateChanged = { state -> updateNotification(state) },
            onCompleted = { complete() }
        )

        // Create initial notification
        val firstState = OrderState.entries[0]
        val notification = createNotification(firstState)

        // Start the tracker
        writingSprintTracker?.startTracking()

        return notification
    }

    fun stop() {
        isActive = false
        writingSprintTracker?.stopTracking()
        writingSprintTracker = null
        handler.removeCallbacksAndMessages(null)
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun updateNotification(state: OrderState) {
        if (!isActive) return

        val notification = createNotification(state)
        notificationManager.notify(
            LiveUpdateNotificationManager.NOTIFICATION_ID_WRITING_SPRINT,
            notification
        )
    }

    private fun createNotification(state: OrderState): Notification {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
            notificationManager.createWritingSprintNotification(
                state.orderStatus,
                state.statusText,
                state.progress
            )
        } else {
            notificationManager.createWritingSprintNotificationCompat(
                state.orderStatus,
                state.statusText,
                state.progress
            )
        }
    }

    private fun complete() {

        // Stop tracker but keep notification visible for a moment
        isActive = false
        writingSprintTracker?.stopTracking()
        writingSprintTracker = null

        // Schedule notification dismissal and service stop
        handler.postDelayed({
            // Cancel the notification
            notificationManager.cancel(LiveUpdateNotificationManager.NOTIFICATION_ID_WRITING_SPRINT)
            // Notify service to stop
            onComplete()
        }, COMPLETION_DISPLAY_DURATION)
    }
}