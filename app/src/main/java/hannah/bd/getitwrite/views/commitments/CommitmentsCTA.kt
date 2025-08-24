//package hannah.bd.getitwrite.views.commitments
//
//import android.Manifest
//import android.content.Context
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.material.icons.Icons
//import androidx.compose.material3.Icon
//import androidx.compose.material.icons.filled.AddCircle
//import androidx.compose.material.icons.filled.CheckCircle
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Switch
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.isGranted
//import com.google.accompanist.permissions.rememberPermissionState
//import hannah.bd.getitwrite.notifications.NotificationHandler
//import hannah.bd.getitwrite.R
//import java.time.LocalTime
//import java.time.format.DateTimeFormatter
//import java.time.format.FormatStyle
//
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun CommitmentCTA(context: Context) {
//    val postNotificationPermission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
//    val notificationHandler = NotificationHandler(context)
//    var notifEnabled by remember { mutableStateOf(false) }
//
//    val notifPerm = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
//
//    LaunchedEffect(Unit) {
//        if (!postNotificationPermission.status.isGranted) {
//            postNotificationPermission.launchPermissionRequest()
//        }
//    }
//
//    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
//        Text("Weekly Writing Schedule", style = MaterialTheme.typography.titleLarge)
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text("Writing notification")
//            Spacer(Modifier.weight(1f))
//            Switch(checked = notifEnabled, onCheckedChange = { enabled ->
//                notifEnabled = enabled
//                if (enabled) {
//
//                } else {
//
//                }
////                if (!enabled) AlarmScheduler.cancelAll(context)
////                saveCommitment(enabled, timeMillis, daysSet, context)
////                if (enabled) AlarmScheduler.scheduleWeekly(context, daysSet, timeMillis)
//            })
//        }
//        if (notifEnabled) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                val time = remember { mutableStateOf(LocalTime.ofSecondOfDay(timeMillis / 1000L)) }
//                val showTimePicker = remember { mutableStateOf(false) }
//                Text("Time: ${time.value.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))}")
//                Spacer(Modifier.weight(1f))
//                Button(onClick = { showTimePicker.value = true }) { Text("Pick Time") }
//                if (showTimePicker.value) {
//                    DialTimePicker(onConfirm = { h, m ->
//                        val newTime = LocalTime.of(h, m)
//                        time.value = newTime
//                        timeMillis = newTime.toSecondOfDay() * 1000L
//                        AlarmScheduler.scheduleWeekly(context, daysSet, timeMillis)
//                        saveCommitment(notifEnabled, timeMillis, daysSet, context)
//                        },
//                        onDismiss = { showTimePicker.value = false }
//                    )
//                }
//            }
//            Text("Days:")
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                listOf("Mon","Tue","Wed","Th","Fri","Sat","Sun").forEach { d ->
//                    val selected = daysSet.contains(d)
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Icon(
//                            imageVector = if (selected) Icons.Default.CheckCircle else Icons.Default.AddCircle,
//                            contentDescription = d,
//                            modifier = Modifier
//                                .size(40.dp)
//                                .clickable {
////                                    daysSet = if (selected) daysSet - d else daysSet + d
////                                    AlarmScheduler.scheduleWeekly(context, daysSet, timeMillis)
////                                    saveCommitment(notifEnabled, timeMillis, daysSet, context)
//                                }
//                        )
//                        Text(d)
//                    }
//                }
//            }
//        }
//    }
//}