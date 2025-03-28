package hannah.bd.getitwrite.views.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hannah.bd.getitwrite.GlobalVariables
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.CreateTagCloud
import hannah.bd.getitwrite.views.components.ErrorText
import hannah.bd.getitwrite.views.components.QuestionSection
import hannah.bd.getitwrite.views.components.SelectTagCloud
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.views.components.CheckInput

@Composable
fun ShowCreateAccountView(navController: NavController, auth: FirebaseAuth) {
    val displayName = remember { mutableStateOf("") }
    val bio = remember { mutableStateOf("") }
    val writing = remember { mutableStateOf("") }
    val critiqueStyle = remember { mutableStateOf("") }
    val genreTags = ArrayList<String>()
    val authorTags = ArrayList<String>()
    var errorString = remember { mutableStateOf<String?>(null) }
    Column(modifier = Modifier
        .padding(20.dp)
        .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Image(painter = painterResource(id = R.drawable.lounging), modifier = Modifier.fillMaxWidth(), contentDescription = "", contentScale = ContentScale.FillWidth)
        Text("Setup Profile", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            OutlinedTextField(
                value = displayName.value,
                maxLines = 1,
                onValueChange = { displayName.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Box {
                        Text(text = "Display Name (Optional)")
                    }
                }
            )
            QuestionSection(bio, "Tell other writers about yourself. (Optional)")
            SelectTagCloud("Which genres do you write? (Optional)", answers = GlobalVariables.genres) {
                genreTags.add(it)
            }
            QuestionSection(writing, "Tell other writers about your writing. (Optional)")
            CreateTagCloud("Who are your favourite authors?") {
                authorTags.add(it)
            }
            QuestionSection(critiqueStyle, "Tell other writers about your critique style. (Optional)")
            ErrorText(error = errorString)
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (CheckInput.verifyCanBeEmpty(displayName.value) ||
                    CheckInput.verifyCanBeEmpty(bio.value) ||
                    CheckInput.verifyCanBeEmpty(writing.value) ||
                    CheckInput.verifyCanBeEmpty(critiqueStyle.value)) {
                    val user = User(id = auth.currentUser?.uid ?: "ID", displayName = displayName.value,
                        bio = bio.value, writing = writing.value, critiqueStyle = critiqueStyle.value,
                        authors = authorTags, writingGenres = genreTags,
                        colour = (0..<GlobalVariables.profileColours.size).random(), blockedUserIds = ArrayList())
                    Firebase.firestore.collection("users").document(auth.currentUser?.uid ?: "ID")
                        .set(user)
                        .addOnSuccessListener { navController.navigate("feed") }
                        .addOnFailureListener { errorString.value = "Network error" }

                } else {
                    errorString.value = "The input contains profanities."
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary)
        ) {
            Text("Sign Up!", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}