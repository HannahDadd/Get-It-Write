package hannah.bd.getitwrite.views.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.views.components.ErrorText


@Composable
fun ShowForgottenPasswordView(navController: NavController, auth: FirebaseAuth) {
    val email = remember { mutableStateOf("") }
    var errorString = remember { mutableStateOf<String?>(null) }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Image(painter = painterResource(id = R.drawable.sitting), modifier = Modifier.fillMaxWidth(), contentDescription = "", contentScale = ContentScale.FillWidth)
        Text("Reset Password", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        ErrorText(error = errorString)
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            TextButton(onClick = {
                navController.navigate("login")
            }) {
                Text("Forgot Password?", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, textAlign = TextAlign.End)
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if(email.value.isEmpty()) {
                    errorString.value = "Email cannot be empty."
                } else {
                    auth.sendPasswordResetEmail(email.value)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                errorString.value = "password reset email sent"
                            }
                        }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary)
        ) {
            Text("Reset Password", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
        TextButton(onClick = { navController.navigate("login") }) {
            Text(modifier = Modifier.align(Alignment.Bottom),
                text = "Back to login", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }
    }
}