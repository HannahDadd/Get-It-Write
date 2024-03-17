package com.example.getitwrite.views.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ErrorText(error: MutableState<String>) {
    Text(error.value, color = Color.Red)
}

@Composable
fun QuestionSection(response: MutableState<String>, question: String) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text(question, fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = response.value,
            maxLines = 1,
            onValueChange = { response.value = it },
            modifier = Modifier.fillMaxWidth().height(120.dp)
        )
    }
}