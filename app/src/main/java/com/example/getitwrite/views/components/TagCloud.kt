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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.getitwrite.GlobalVariables
import com.example.getitwrite.views.login.Colours

@Composable
fun SelectTagCloud(question: String) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(question, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 4.dp))
        TagCloud(clickAction = true)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagCloud(clickAction: Boolean) {
    FlowRow() {
        for (genre in GlobalVariables.genres) {
            SingleTag(clickAction, genre)
        }
    }
}

@Composable
fun SingleTag(clickAction: Boolean, tagString: String) {
    var bgColour = remember { mutableStateOf( Colours.Dark_Background) }
    TextButton(modifier = Modifier
        .padding(4.dp)
        .background(
            color = bgColour.value,
            shape = RoundedCornerShape(4.dp)
        ),
        onClick = {
            if (clickAction) {
                bgColour.value = Colours.Dark_Readable
            }
        }) {
        Text(text = tagString, color = Color.White)
    }
}
