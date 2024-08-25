package hannah.bd.getitwrite.views.critiqueFrenzy

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.ErrorText
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.GlobalVariables
import hannah.bd.getitwrite.MainActivity
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.views.components.CheckInput
import hannah.bd.getitwrite.views.components.SelectTagCloud
import java.util.UUID
import java.util.concurrent.TimeUnit

@Composable
fun MakeFrenzyView(user: User, dbName: String, placeHolder: String, onSuccess: (RequestCritique) -> Unit) {
    var errorString = remember { mutableStateOf<String?>(null) }
    val genreTags = remember { mutableStateOf(mutableListOf<String>()) }
    val text = remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("1,000 word limit without any trigger warnings.")
        SelectTagCloud(question = "Select genres", answers = GlobalVariables.genres) {
            genreTags.value.add(it)
        }
        OutlinedTextField(value = text.value,
            maxLines = 10,
            onValueChange = { text.value = it },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            label = { Text(text = placeHolder) }
        )
        ErrorText(error = errorString)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (genreTags.value.isEmpty()) {
                    errorString.value = "You must select at least one genre"
                } else if (CheckInput.isStringGood(text.value, 1000)) {
                    val id = UUID.randomUUID().toString()
                    val workTitle = if (dbName == "frenzy") "Critique Frenzy" else "Query Frenzy"
                    val request = RequestCritique(id = id, title = workTitle, blurb = "", genres = genreTags.value,
                        triggerWarnings = mutableListOf(), workTitle = workTitle, text = text.value, timestamp = Timestamp.now(), writerId = user.id, writerName = user.displayName)
                    Firebase.firestore.collection(dbName).document(id).set(request)
                        .addOnSuccessListener {
                            val notificationRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                                .setInitialDelay(10, TimeUnit.SECONDS)
                                .setInputData(workDataOf("title" to "Check back on your $workTitle to see if you've got any critiques!", "message" to "You posted it 3 days ago."))
                                .build()

                            WorkManager.getInstance(context).enqueue(notificationRequest)
                            onSuccess(request)
                        }
                        .addOnFailureListener {
                            errorString.value = it.message.toString()
                        }
                } else {
                    errorString.value = CheckInput.errorStringText
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )        ) {
            Text("Submit", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}

class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Default Title"
        val message = inputData.getString("message") ?: "Default Message"

        showNotification(applicationContext, title, message)

        return Result.success()
    }
}

fun showNotification(context: Context, title: String, message: String) {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    val builder = NotificationCompat.Builder(context, "scheduled_channel_id")
        .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your app's icon
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notify(1001, builder.build())
    }
}

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Scheduled Notifications"
        val descriptionText = "Channel for scheduled notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("scheduled_channel_id", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}