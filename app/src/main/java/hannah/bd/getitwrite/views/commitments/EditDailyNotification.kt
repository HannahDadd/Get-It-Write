package hannah.bd.getitwrite.views.commitments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.modals.AppDatabase
import hannah.bd.getitwrite.notifications.ScheduleNotification
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDailyNotification(
    db: AppDatabase
) {
    val context = LocalContext.current

    val date = remember { Calendar.getInstance().timeInMillis }
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    var showDatePicker by remember { mutableStateOf(false) }

    var showTimePicker by remember { mutableStateOf(false) }
    var selectedTime: TimePickerState? by remember { mutableStateOf(null) }

    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Weekly Writing Schedule", style = MaterialTheme.typography.titleLarge)
        Row(verticalAlignment = Alignment.CenterVertically) {

            if (selectedTime != null) {
                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, selectedTime!!.hour)
                cal.set(Calendar.MINUTE, selectedTime!!.minute)
                cal.isLenient = false
                Text("Selected time = ${formatter.format(cal.time)}")
            } else {
                Text("No time selected.")
            }

            Spacer(Modifier.weight(1f))
            Button(onClick = { showTimePicker = true }) { Text("Pick Time") }
            if (showTimePicker) {
                DialTimePicker(
                    onDismiss = { showTimePicker = false },
                    onConfirm = { time ->
                        selectedTime = time
                        showTimePicker = false
                    }
                )
            }
        }
        Text("Days:")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Mon","Tue","Wed","Th","Fri","Sat","Sun").forEach { d ->
                val daysSet = listOf("Mon","Fri","Sat","Sun")
                val selected = daysSet.contains(d)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = if (selected) Icons.Default.CheckCircle else Icons.Default.AddCircle,
                        contentDescription = d,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
//                                    daysSet = if (selected) daysSet - d else daysSet + d
//                                    AlarmScheduler.scheduleWeekly(context, daysSet, timeMillis)
//                                    saveCommitment(notifEnabled, timeMillis, daysSet, context)
                            }
                    )
                    Text(d)
                }
            }
        }
    }

    Button(
        onClick = {
            selectedTime?.let {
                ScheduleNotification().scheduleNotification(context,
                    it, 4, "Open Get it Write and start your writing sprint now!")
            }
        },
    ) { Text(text = "Add reminder") }
}
