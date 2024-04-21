package com.example.getitwrite.views.profile

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
import com.example.getitwrite.views.components.DetailHeader
import com.example.getitwrite.views.components.ErrorText
import com.example.getitwrite.views.components.QuestionSection
import com.example.getitwrite.views.components.SelectTagCloud
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun EditProfileView(user: User, navigateUp: () -> Unit) {
    val bio = remember { mutableStateOf(user.bio) }
    val writing = remember { mutableStateOf(user.writing) }
    val critiqueStyle = remember { mutableStateOf(user.critiqueStyle) }
    val genreTags = user.writingGenres
    val authorTags = user.authors
    var errorString = remember { mutableStateOf("") }
    Column {
        DetailHeader(title = user.displayName, navigateUp = navigateUp)
        Column(modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuestionSection(bio, "Tell other writers about yourself.")
            SelectTagCloud("Which genres do you write?", answers = GlobalVariables.genres, preSelectedTags = user.writingGenres) {
                genreTags.add(it)
            }
            QuestionSection(writing, "Tell other writers about your writing.")
            CreateTagCloud("Who are your favourite authors?") {
                authorTags.add(it)
            }
            QuestionSection(critiqueStyle, "Tell other writers about your critique style.")
            ErrorText(error = errorString)
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    Firebase.firestore.collection("users").document(user.id)
                        .set(
                            User(
                                id = user.id,
                                displayName = user.displayName,
                                bio = bio.value,
                                writing = writing.value,
                                critiqueStyle = critiqueStyle.value,
                                authors = authorTags,
                                writingGenres = genreTags,
                                colour = user.colour,
                                arrayListOf()
                            )
                        )
                        .addOnSuccessListener {
                            errorString.value = "Updated!"
                        }
                        .addOnFailureListener { errorString.value = "Network error" }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colours.Dark_Readable,
                    contentColor = Color.White
                )
            ) {
                Text("Update Profile", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
            }
        }
    }
}