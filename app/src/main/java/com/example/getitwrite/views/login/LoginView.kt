package com.example.getitwrite.views.login

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getitwrite.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.getitwrite.Colours
import com.example.getitwrite.views.components.ErrorText
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FirebaseAuth
import java.util.Arrays

@Composable
fun ShowLogin(navController: NavController, auth: FirebaseAuth) {
    val callbackManager = CallbackManager.Factory.create();
    var errorString = remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Image(painter = painterResource(id = R.drawable.sitting), modifier = Modifier.fillMaxWidth(), contentDescription = "", contentScale = ContentScale.FillWidth)
        Text("Login", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        CompositionLocalProvider(
            LocalFacebookCallbackManager provides callbackManager
        ) {
            FBLoginScreen({ navController.navigate("feed") })
        }
        ErrorText(error = errorString)
        TextButton(onClick = { navController.navigate("signup") }) {
            Text(modifier = Modifier.align(Alignment.Bottom),
                text = "Don't have an account? Sign Up", color = Colours.Dark_Readable, fontWeight = FontWeight.Bold)
        }
    }
}

val LocalFacebookCallbackManager = staticCompositionLocalOf<CallbackManager> { error("No CallbackManager provided") }

@Composable
fun FBLoginScreen(successCallback: () -> Unit) {
    var errorString = remember { mutableStateOf("") }
    val callbackManager = LocalFacebookCallbackManager.current
    DisposableEffect(Unit) {
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    successCallback()
                }

                override fun onCancel() {
                    errorString.value = "Login cancelled"
                }

                override fun onError(exception: FacebookException) {
                    errorString.value = "Error: $exception"
                }
            }
        )
        onDispose {
            LoginManager.getInstance().unregisterCallback(callbackManager)
        }
    }
    val context = LocalContext.current
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            context.findActivity()?.let {
                LoginManager.getInstance()
                    .logInWithReadPermissions(it, Arrays.asList("public_profile"))
            };
        },
        colors = ButtonDefaults.buttonColors(containerColor = Colours.Dark_Readable, contentColor = Color.White)
    ) {
        Text("FACEBOOK LOGIN", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
    }
    ErrorText(error = errorString)
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}