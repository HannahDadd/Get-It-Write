package hannah.bd.shelfify.views.notifications

import android.app.TimePickerDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import hannah.bd.getitwrite.views.commitments.PopupPromo
import java.util.Calendar

@Composable
fun DailyReminderButton() {

    val context = LocalContext.current

    PopupPromo("Let's get that book written", "Set the time for your daily writing reminder", {

        val now = Calendar.getInstance()

        TimePickerDialog(
            context,
            { _, hour, minute ->

                DailyNotificationScheduler.scheduleDailyNotification(
                    context = context,
                    hour = hour,
                    minute = minute
                )

            },
            now.get(Calendar.HOUR_OF_DAY),
            now.get(Calendar.MINUTE),
            true
        ).show()
    })
}