package hannah.bd.shelfify.views.notifications

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission

class DailyNotificationReceiver : BroadcastReceiver() {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(
        context: Context,
        intent: Intent?
    ) {

        NotificationHelper.showNotification(
            context,
            title = "Library Reminder",
            message = "Come back and write today!"
        )

        scheduleTomorrow(context)
    }

    private fun scheduleTomorrow(
        context: Context
    ) {

        val prefs = context.getSharedPreferences(
            "daily_notification",
            Context.MODE_PRIVATE
        )

        val hour = prefs.getInt("hour", 18)
        val minute = prefs.getInt("minute", 0)

        DailyNotificationScheduler.scheduleDailyNotification(
            context,
            hour,
            minute
        )
    }
}