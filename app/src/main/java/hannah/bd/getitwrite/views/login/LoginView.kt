package hannah.bd.getitwrite.views.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import hannah.bd.getitwrite.Colours
import hannah.bd.getitwrite.views.components.ErrorText
import com.google.firebase.auth.FirebaseAuth
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.views.components.ReportContent
import hannah.bd.getitwrite.views.settings.TsAndCsView

@Composable
fun ShowLogin(navController: NavController, auth: FirebaseAuth) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var errorString = remember { mutableStateOf<String?>(null) }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Image(painter = painterResource(id = R.drawable.sitting), modifier = Modifier.fillMaxWidth(), contentDescription = "", contentScale = ContentScale.FillWidth)
        Text("Login", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        ErrorText(error = errorString)
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
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
            TextButton(onClick = {/**/}) {
                Text("Forgot Password?", color = Colours.Dark_Readable, fontWeight = FontWeight.Bold, textAlign = TextAlign.End)
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                auth.signInWithEmailAndPassword(email.value, password.value)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            navController.navigate("feed")
                        } else {
                            errorString.value = task.exception?.message.toString()
                        }
                    }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Colours.Dark_Readable, contentColor = Color.White)
        ) {
            Text("LOGIN", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
        TextButton(onClick = { navController.navigate("signup") }) {
            Text(modifier = Modifier.align(Alignment.Bottom),
                text = "Don't have an account? Sign Up", color = Colours.Dark_Readable, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ShowOpeningPage(navController: NavController, auth: FirebaseAuth) {
    Box(
        modifier = with (Modifier){
            fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.loginbg),
                    contentScale = ContentScale.FillBounds)

        })
    {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navController.navigate("login")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Colours.Dark_Readable,
                        contentColor = Color.White
                    )
                ) {
                    Text("Login", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
                }
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navController.navigate("signup")
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text("Sign Up", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.weight(1.0f))
            Column(horizontalAlignment = Alignment.End) {
                Text("Get it Write", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("The start of your writing journey", style = MaterialTheme.typography.bodyMedium, color = Color.White)
            }

        }
    }
}
