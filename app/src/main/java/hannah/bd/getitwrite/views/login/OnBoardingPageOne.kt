package hannah.bd.getitwrite.views.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.window.core.layout.WindowHeightSizeClass
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.GlobalVariables
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.ErrorText

@Composable
fun OnBoardingPageOne(navController: NavController, displayName: String, auth: FirebaseAuth) {
    val windowSize = currentWindowAdaptiveInfo().windowSizeClass.windowHeightSizeClass
    if (windowSize == WindowHeightSizeClass.COMPACT) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.onboarding1),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
            )
            OnBoardingPageOneContent(navController, displayName, auth)
        }
    } else {
        Row(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.onboarding1),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(10.dp))
            )
            OnBoardingPageOneContent(navController, displayName, auth)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingPageOneContent(navController: NavController, displayName: String, auth: FirebaseAuth) {
    var expanded by remember { mutableStateOf(false) }
    var errorString = remember { mutableStateOf<String?>(null) }
    var selectedOption by remember { mutableStateOf("Select an option") }
    Column(Modifier.padding(16.dp)) {
        Text(
            text = "Once upon a time there was a writer who went on a journey to find their community…",
            color = Color.Black,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(4.dp),
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Row {
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(onClick = {
                val user = User(id = auth.currentUser?.uid ?: "ID", displayName = displayName,
                    bio = "", writing = "", critiqueStyle = "", authors = mutableListOf(),
                    writingGenres = mutableListOf(),
                    colour = (0..<GlobalVariables.profileColours.size).random(),
                    blockedUserIds = mutableListOf(), critiquerExpected = selectedOption)
                Firebase.firestore.collection("users").document(auth.currentUser?.uid.toString())
                    .set(user)
                    .addOnSuccessListener {  }
                    .addOnFailureListener { errorString.value = "Network error" }
                navController.navigate("onboardingPageTwo")
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "Localized description"
                )
            }
        }
        Spacer(modifier = Modifier.weight(1.0f))
        ErrorText(errorString)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                label = { Text("What are you looking for in a critique partner?") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                val options = listOf("Confidence Builder", "Honest Feedback Giver", "Query Package Evaluator", "Accountability Partner", "Writing Pal")

                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedOption = option
                            expanded = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ShowOpeningPage(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Welcome to Get It Write!",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "What are you looking for most?",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        FilledTonalButton(onClick = { navController.navigate("enterUsernameCosyCorner") },
            modifier = Modifier.padding(16.dp)) {
            Text("A supportive community of writers.",
                style = MaterialTheme.typography.bodyMedium)
        }
        FilledTonalButton(onClick = { navController.navigate("enterUsernameKillerCritiquer") },
            modifier = Modifier.padding(16.dp)) {
            Text("Honest critiques of your work.",
                style = MaterialTheme.typography.bodyMedium)
        }
    }
}