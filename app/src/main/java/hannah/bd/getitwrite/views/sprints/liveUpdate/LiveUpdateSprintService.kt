package hannah.bd.shelfify.views.sprints.liveUpdate

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log

class LiveUpdateSprintService : Service() {

    private lateinit var notificationManager: LiveUpdateNotificationManager
    private lateinit var deliveryHandler: WritingSprintServiceHandler
    private var isRunning = false

    companion object {
        private const val TAG = "WritingSprintService"
        const val ACTION_START = "hannah.bd.shelfify.livenotificationupdate.writingsprint.START"
        const val ACTION_STOP = "hannah.bd.shelfify.livenotoficationupdate.writingsprint.STOP"
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = LiveUpdateNotificationManager(this)
        deliveryHandler = WritingSprintServiceHandler(
            service = this,
            notificationManager = notificationManager,
            onComplete = {
                stopSprint()
            }
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {
            ACTION_START -> startDelivery(intent.getStringExtra("delayLength") ?: "")
            ACTION_STOP -> stopSprint()
            else -> Log.w(TAG, "Unknown action: ${intent?.action}")
        }

        return START_NOT_STICKY  // Don't restart if killed
    }

    private fun startDelivery(delayLength: String) {
        if (isRunning) {
            return
        }

        val canPostPromoted = notificationManager.canPostPromotedNotifications()
        isRunning = true
        val notification = deliveryHandler.start(delayLength)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
            val hasPromotable = notification.hasPromotableCharacteristics()
            Log.d(TAG, "Initial notification hasPromotableCharacteristics: $hasPromotable")
        }

        startForeground(
            LiveUpdateNotificationManager.Companion.NOTIFICATION_ID_WRITING_SPRINT,
            notification
        )

    }

    private fun stopSprint() {
        if (!isRunning) {
            return
        }

        isRunning = false
        deliveryHandler.stop()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            @Suppress("DEPRECATION")
            stopForeground(true)
        }

        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()

        if (isRunning) {
            deliveryHandler.stop()
        }
    }
}