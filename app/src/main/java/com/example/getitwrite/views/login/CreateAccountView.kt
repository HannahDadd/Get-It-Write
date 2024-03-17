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
import com.example.getitwrite.R
import com.example.getitwrite.views.components.QuestionSection
import com.example.getitwrite.views.components.SelectTagCloud

@Composable
fun ShowCreateAccountView() {
    var displayName = remember { mutableStateOf("") }
    var bio = remember { mutableStateOf("") }
    var writing = remember { mutableStateOf("") }
    var critiqueStyle = remember { mutableStateOf("") }
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
            SelectTagCloud("Which genres do you write?")
            QuestionSection(bio, "Tell other writers about yourself.")
            QuestionSection(writing, "Tell other writers about your writing.")
            QuestionSection(critiqueStyle, "Tell other writers about your critique style.")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /* ... */ },
            colors = ButtonDefaults.buttonColors(containerColor = Colours.Dark_Readable, contentColor = Color.White)
        ) {
            Text("Sign Up!", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}