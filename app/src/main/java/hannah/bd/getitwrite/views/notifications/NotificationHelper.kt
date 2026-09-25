package hannah.bd.shelfify.views.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.IconCompat
import hannah.bd.getitwrite.R

object NotificationHelper {

    const val CHANNEL_ID = "daily_reminders"

    fun createChannel(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                "Daily Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager =
                context.getSystemService(
                    NotificationManager::class.java
                )

            manager.createNotificationChannel(channel)
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(
        context: Context,
        title: String,
        message: String
    ) {

        val notification =
            NotificationCompat.Builder(
                context,
                CHANNEL_ID
            )
                .setSmallIcon(IconCompat.createWithResource(context, android.R.drawable.ic_menu_sort_by_size))
                .setContentTitle("✍️ Let's get Writing!")
                .setContentText("Time to add some words to that writing project.")
                .build()

        val manager = NotificationManagerCompat.from(context)
        manager.notify(1, notification)
//            .notify(
//                System.currentTimeMillis().toInt(),
//                notification
//            )
    }
}