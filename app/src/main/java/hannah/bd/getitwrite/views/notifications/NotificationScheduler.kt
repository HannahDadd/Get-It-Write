package hannah.bd.shelfify.views.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import java.util.Calendar
import kotlin.jvm.java

object DailyNotificationScheduler {

    fun scheduleDailyNotification(
        context: Context,
        hour: Int,
        minute: Int
    ) {

        val alarmManager =
            context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
            !alarmManager.canScheduleExactAlarms()
        ) {

            val intent = Intent(
                Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
            )

            context.startActivity(intent)
            return
        }

        val intent = Intent(
            context,
            DailyNotificationReceiver::class.java
        )

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            100,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

        saveTime(context, hour, minute)
    }

    private fun saveTime(
        context: Context,
        hour: Int,
        minute: Int
    ) {
        context.getSharedPreferences(
            "daily_notification",
            Context.MODE_PRIVATE
        )
            .edit()
            .putInt("hour", hour)
            .putInt("minute", minute)
            .apply()
    }
}