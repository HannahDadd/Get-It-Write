package hannah.bd.getitwrite.views.commitments

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import hannah.bd.getitwrite.notifications.NotificationReceiver
import hannah.bd.getitwrite.views.components.StretchedButton
import java.time.LocalTime
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommitmentCTA(activity: Activity, context: Context = LocalContext.current) {
    val prefs = remember { context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE) }
    var notif by remember { mutableStateOf(prefs.getBoolean("notification", false)) }
    var showSetSheet by remember { mutableStateOf(false) }
    var showEditSheet by remember { mutableStateOf(false) }
    var time by remember { mutableStateOf(LocalTime.now()) }

    // Request notification permission (Android 13+)
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (ContextCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permission),
                    100
                )
            }
        }
    }

    if (notif) {
        PopupPromo(
            title = "You have a daily notification set!",
            subtitle = "Tap to edit"
        ) {
            showEditSheet = true
        }
    } else {
        PopupPromo(
            title = "Let's get that book written",
            subtitle = "Set a daily notification"
        ) {
            showSetSheet = true
        }
    }

    // Edit existing notification
    if (showEditSheet) {
        AlertDialog(
            onDismissRequest = { showEditSheet = false },
            confirmButton = {},
            text = {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text("Edit Daily Notification", style = MaterialTheme.typography.headlineSmall)
                    TimePicker(time = time, onTimeSelected = { time = it })
                    Spacer(modifier = Modifier.height(24.dp))
                    Column {
                        StretchedButton("Schedule", onClick = {
                            turnOffNotifications(context)
                            scheduleNotification(context, time)
                            notif = true
                            prefs.edit().putBoolean("notification", true).apply()
                            showEditSheet = false
                        })
                        Spacer(modifier = Modifier.height(8.dp))
                        StretchedButton("Cancel Notification", isGrey = true, onClick = {
                            turnOffNotifications(context)
                            notif = false
                            prefs.edit().putBoolean("notification", false).apply()
                            showEditSheet = false
                        })
                    }
                }
            }
        )
    }

    // Schedule new notification
    if (showSetSheet) {
        AlertDialog(
            onDismissRequest = { showSetSheet = false },
            confirmButton = {},
            text = {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text("Schedule Daily Notification", style = MaterialTheme.typography.headlineSmall)
                    TimePicker(time = time, onTimeSelected = { time = it })
                    Spacer(modifier = Modifier.height(24.dp))
                    StretchedButton("Schedule", onClick = {
                        turnOffNotifications(context)
                        scheduleNotification(context, time)
                        notif = true
                        prefs.edit().putBoolean("notification", true).apply()
                        showSetSheet = false
                    })
                }
            }
        )
    }
}

// A simple time picker using AndroidView
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePicker(time: LocalTime, onTimeSelected: (LocalTime) -> Unit) {
    AndroidView(
        factory = { ctx ->
            TimePicker(ctx).apply {
                setIs24HourView(true)
                hour = time.hour
                minute = time.minute
                setOnTimeChangedListener { _, h, m -> onTimeSelected(LocalTime.of(h, m)) }
            }
        },
        update = { picker ->
            picker.hour = time.hour
            picker.minute = time.minute
        }
    )
}

// Scheduling notification (requires NotificationChannel setup)
@RequiresApi(Build.VERSION_CODES.O)
fun scheduleNotification(context: Context, time: LocalTime) {
    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("title", "Let's get writing!")
        putExtra("message", "Open Get It Write and start your writing sprint now!")
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, time.hour)
        set(Calendar.MINUTE, time.minute)
        set(Calendar.SECOND, 0)
    }

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )
}

fun turnOffNotifications(context: Context) {
    val intent = Intent(context, NotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
    )
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    if (pendingIntent != null) {
        alarmManager.cancel(pendingIntent)
    }
}
