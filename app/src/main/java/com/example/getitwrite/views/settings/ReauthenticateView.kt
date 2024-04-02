package com.example.getitwrite.views.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.getitwrite.Colours
import com.example.getitwrite.views.components.ErrorText
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ReAuthBottomSheetNavComponent(nextTask: PostReAuthTask) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "reauth"
    ) {
        composable("reauth") {
            ReAuthView(navController, nextTask = nextTask)
        }
        composable("changeEmail") {
            ChangeEmailView()
        }
        composable("changePassword") {
            ChangePasswordView()
        }
        composable("deleteAccount") {
            DeleteAccountView()
        }
    }
}

@Composable
fun ReAuthView(navController: NavController, nextTask: PostReAuthTask) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var errorString = remember { mutableStateOf("") }
    Column {
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
                                navController.navigate("changePassword")
                            } else if (nextTask == PostReAuthTask.deleteAccount) {
                                navController.navigate("deleteAccount")
                            } else {
                                navController.navigate("changeEmail")
                            }
                        } else {
                            errorString.value = "Failed to Reauthenticate"
                        }
                    })
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Colours.Dark_Readable,
                contentColor = Color.White
            )
        ) {
            Text("Re-authenticate", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}

enum class PostReAuthTask {
    changeEmail,
    changePassword,
    deleteAccount
}