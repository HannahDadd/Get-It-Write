package com.example.getitwrite.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.getitwrite.GlobalVariables
import com.example.getitwrite.views.login.Colours

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagCloud() {
    Column {
        FlowRow() {
            for (genre in GlobalVariables.genres) {
                TextButton(modifier = Modifier
                    .padding(4.dp)
                    .background(
                        color = Colours.Dark_Background,
                        shape = RoundedCornerShape(4.dp)),
                    onClick = {  }) {
                    Text(text = genre, color = Color.White)
                }
            }
        }
    }
}
