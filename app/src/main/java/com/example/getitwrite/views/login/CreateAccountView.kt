package com.example.getitwrite.views.login

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
import com.example.getitwrite.Colours
import com.example.getitwrite.GlobalVariables
import com.example.getitwrite.R
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.CreateTagCloud
import com.example.getitwrite.views.components.ErrorText
import com.example.getitwrite.views.components.QuestionSection
import com.example.getitwrite.views.components.SelectTagCloud
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun ShowCreateAccountView(navController: NavController, auth: FirebaseAuth) {
    val displayName = remember { mutableStateOf("") }
    val bio = remember { mutableStateOf("") }
    val writing = remember { mutableStateOf("") }
    val critiqueStyle = remember { mutableStateOf("") }
    val genreTags = ArrayList<String>()
    val authorTags = ArrayList<String>()
    var errorString = remember { mutableStateOf("") }
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
                        Text(text = "Display Name")
                    }
                }
            )
            QuestionSection(bio, "Tell other writers about yourself.")
            SelectTagCloud("Which genres do you write?", answers = GlobalVariables.genres) {
                genreTags.add(it)
            }
            QuestionSection(writing, "Tell other writers about your writing.")
            CreateTagCloud("Who are your favourite authors?") {
                authorTags.add(it)
            }
            QuestionSection(critiqueStyle, "Tell other writers about your critique style.")
            ErrorText(error = errorString)
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val db = Firebase.firestore
                val user = User(id = auth.currentUser?.uid ?: "ID", displayName = displayName.value, bio = bio.value, writing = writing.value, critiqueStyle = critiqueStyle.value, authors = authorTags, writingGenres = genreTags, colour = (0..<GlobalVariables.profileColours.size).random())
                db.collection("cities").document("LA")
                    .set(user)
                    .addOnSuccessListener { navController.navigate("feed") }
                    .addOnFailureListener { errorString.value = "Network error" }

            },
            colors = ButtonDefaults.buttonColors(containerColor = Colours.Dark_Readable, contentColor = Color.White)
        ) {
            Text("Sign Up!", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}