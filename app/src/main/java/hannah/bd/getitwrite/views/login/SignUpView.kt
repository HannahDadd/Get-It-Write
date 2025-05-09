package hannah.bd.getitwrite.views.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hannah.bd.getitwrite.GlobalVariables
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.ErrorText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.views.settings.TsAndCsView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowSignUp(navController: NavController, auth: FirebaseAuth) {
    var email = remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var confirmPassword = remember { mutableStateOf("") }
    var errorString = remember { mutableStateOf<String?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                TsAndCsView()
            }
        }
        Image(painter = painterResource(id = R.drawable.building), modifier = Modifier.fillMaxWidth(), contentDescription = "", contentScale = ContentScale.FillWidth)
        Text("Sign Up", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            OutlinedTextField(
                value = username,
                maxLines = 1,
                onValueChange = { username = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Username") }
            )
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
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Password") }
            )
            OutlinedTextField(
                value = confirmPassword.value,
                maxLines = 1,
                onValueChange = { confirmPassword.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Confirm Password") }
            )
        }
        TextButton(onClick = { showBottomSheet = true }) {
            Text("By signing up you agree you were over 18 and our terms and conditions.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = Color.Red)
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (email.value.isEmpty())
                    errorString.value = "Please provide an email address"
                else if (password.value.isEmpty())
                    errorString.value = "Please provide a password"
                else if (password.value != confirmPassword.value)
                    errorString.value = "Passwords do not match"
                else
                    auth.createUserWithEmailAndPassword(email.value, password.value)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = User(id = auth.currentUser?.uid ?: "ID", displayName = username,
                                    bio = "", writing = "", critiqueStyle = "", authors = mutableListOf<String>(),
                                    writingGenres = mutableListOf<String>(), colour = (0..<GlobalVariables.profileColours.size).random(),
                                    blockedUserIds = mutableListOf<String>())
                                Firebase.firestore.collection("users").document(auth.currentUser?.uid.toString())
                                    .set(user)
                                    .addOnSuccessListener {  }
                                    .addOnFailureListener { errorString.value = "Network error" }
                                navController.navigate("onboardingPageOne/$username")
                            } else {
                                errorString.value = task.exception?.message.toString()
                            }
                        }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary)
        ) {
            Text("SIGN UP", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
        ErrorText(errorString)
        TextButton(onClick = { navController.navigate("login") }) {
            Text(modifier = Modifier.align(Alignment.Bottom),
                text = "Back to Login", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }
    }
}
