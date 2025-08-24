package hannah.bd.getitwrite.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.util.Calendar

class ScheduleNotification {

    @OptIn(ExperimentalMaterial3Api::class)
    fun scheduleNotification(
        context: Context,
        timePickerState: TimePickerState,
        dayOfWeek: Int,
        title: String
    ) {
        val intent = Intent(context.applicationContext, ReminderReceiver::class.java)
        intent.putExtra("Let's get writing!", title)
        val pendingIntent = PendingIntent.getBroadcast(
            context.applicationContext,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val cal = Calendar.getInstance()
        cal.timeInMillis = System.currentTimeMillis()
        cal[Calendar.HOUR_OF_DAY] = timePickerState.hour
        cal[Calendar.MINUTE] = timePickerState.minute
        cal[Calendar.DAY_OF_WEEK] = dayOfWeek
        cal.add(Calendar.SECOND, 2)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            cal.timeInMillis, (24 * 7 * 60 * 60 * 1000).toLong(), pendingIntent
        )

        Toast.makeText(context, "Reminder set!", Toast.LENGTH_SHORT).show()
    }

}