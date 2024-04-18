package com.example.getitwrite.views.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReportAndBlockUser() {
    var showButtons = remember { mutableStateOf(false) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            Spacer(modifier = Modifier.weight(1.0f))
            TextButton(onClick = { showButtons.value = true }) {
                Icon(Icons.Filled.Info, contentDescription = "", Modifier.padding(end = 10.dp))
            }
        }
        if (showButtons.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
            ) {
                TextButton(onClick = {  }) {
                    Row {
                        Icon(Icons.Filled.Warning, contentDescription = "", Modifier.padding(end = 10.dp))
                        Text("Report content",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Red)
                    }
                }
                Spacer(modifier = Modifier.weight(1.0f))
                TextButton(onClick = {  }) {
                    Text("Block user",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Red)
                }
            }
        }
    }
}