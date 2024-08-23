package hannah.bd.getitwrite.views.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import hannah.bd.getitwrite.views.components.ErrorText
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


@Composable
fun ReAuthView(logoutNavController: NavHostController, nextTask: PostReAuthTask) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var errorString = remember { mutableStateOf<String?>(null) }

    val openAlertDialog = remember { mutableStateOf(false) }
    val showEmailReset = remember { mutableStateOf(false) }
    val showPasswordReset = remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        OutlinedTextField(
            value = email.value,
            maxLines = 1,
            onValueChange = { email.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Email") }
        )
        OutlinedTextField(
            value = password.value,
            maxLines = 1,
            onValueChange = { password.value = it },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Password") }
        )
        ErrorText(error = errorString)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val credential =
                    EmailAuthProvider.getCredential(email.value, password.value)
                FirebaseAuth.getInstance().currentUser?.reauthenticate(credential)
                    ?.addOnCompleteListener(OnCompleteListener<Void?> { task ->
                        if (task.isSuccessful) {
                            if (nextTask == PostReAuthTask.changePassword) {
                                showPasswordReset.value = true
                            } else if (nextTask == PostReAuthTask.deleteAccount) {
                                openAlertDialog.value = true
                            } else {
                                showEmailReset.value = true
                            }
                        } else {
                            errorString.value = "Failed to Reauthenticate"
                        }
                    })
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary)
        ) {
            Text("Re-authenticate", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
    when {
        openAlertDialog.value -> {
            AlertDialog(
                onDismissRequest = { openAlertDialog.value = false },
                confirmButton = {
                    Firebase.firestore.collection("users")
                        .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                        .delete()
                    FirebaseAuth.getInstance().currentUser?.delete()?.addOnSuccessListener {
                        logoutNavController.navigate("login")
                    }
                },
                title = { Text(text = "Are you sure you want to delete your account?") },
                text = { Text(text = "This cannot be undone.") })
        }
    }
}

enum class PostReAuthTask {
    changeEmail,
    changePassword,
    deleteAccount
}