package com.example.getitwrite.views.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.getitwrite.Colours
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.DetailHeader
import com.example.getitwrite.views.components.TagCloud

@Composable
fun ProfileView(navController: NavController, ownProfile: Boolean, user: User, navigateUp: () -> Unit) {
    Column {
        DetailHeader(title = user.displayName, navigateUp = navigateUp)
        Column(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Bio", fontWeight = FontWeight.Bold)
            Text(user.bio)
            Text("Favourite author", fontWeight = FontWeight.Bold)
            TagCloud(tags = user.authors, action = null)
            Text("Writing", fontWeight = FontWeight.Bold)
            Text(user.writing)
            Text("Writing Genres", fontWeight = FontWeight.Bold)
            TagCloud(tags = user.writingGenres, action = null)
            Text("Critique Style", fontWeight = FontWeight.Bold)
            Text(user.critiqueStyle)
            if (ownProfile) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navController.navigate("editProfile")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Colours.Dark_Readable, contentColor = Color.White)
                ) {
                    Text("Edit Profile", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}