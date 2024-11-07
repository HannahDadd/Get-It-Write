package hannah.bd.getitwrite.views.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.GlobalVariables
import hannah.bd.getitwrite.MainActivity
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.modals.User

@Composable
fun OnBoardingPageTwo(navController: NavController) {
//    val permissionState = rememberPermissionState(Manifest.permission.)
//
//    val requestPermissionLauncher = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        if (isGranted) {
//            // Permission granted
//        } else {
//            // Handle permission denial
//        }
//    }
//
//    LaunchedEffect(cameraPermissionState) {
//        if (!cameraPermissionState.status.isGranted && cameraPermissionState.status.shouldShowRationale) {
//            // Show rationale if needed
//        } else {
//            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
//        }
//    }

    val primary = MaterialTheme.colorScheme.primary
    Box {
        Column(Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier.weight(1.0f))
            Row {
                Spacer(modifier = Modifier.weight(1.0f))
                IconButton(onClick = {
                    navController.navigate("feed")
                }) {
                    Icon(
                        modifier = Modifier
                            .drawBehind {
                                drawRoundRect(
                                    primary,
                                    cornerRadius = CornerRadius(10.dp.toPx())
                                )
                            }
                            .size(100.dp),
                        imageVector = Icons.Filled.ArrowForward,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = "Localized description"
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1.0f))
        }
        Column(Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.crime),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(50.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1.0f))
            TextOnboarding("Once upon a time there was a writer who went on a journey to find their community…")
            Spacer(modifier = Modifier.weight(1.0f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    "Allow Notifications",
                    Modifier.padding(10.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.weight(1.0f))
        }
    }
}