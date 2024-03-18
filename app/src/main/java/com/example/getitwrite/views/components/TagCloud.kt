package com.example.getitwrite.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.getitwrite.Colours

@Composable
fun CreateTagCloud(question: String, addAction: (input: String) -> Boolean) {
    var text by remember { mutableStateOf("") }
    val tagsInCloud by remember { mutableStateOf(mutableListOf<String>()) }
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(question, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 4.dp))
        Text("Tap on tags to remove them", fontWeight = FontWeight.Light, modifier = Modifier.padding(vertical = 4.dp))
        Row {
            TextField(
                value = text,
                onValueChange = { text = it }
            )
            RoundedButton(modifier = Modifier, onClick = {
                tagsInCloud.add(text)
                text = ""
                addAction(text)
            })
        }
        TagCloud(tags = tagsInCloud, action = null)
    }
}

@Composable
fun SelectTagCloud(question: String, answers: MutableList<String>, clickAction: (input: String) -> Boolean) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(question, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 4.dp))
        TagCloud(tags = answers, action = clickAction)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagCloud(tags: MutableList<String>, action: ((input: String) -> Boolean)?) {
    FlowRow() {
        for (tag in tags) {
            SingleTag(tag, action)
        }
    }
}

@Composable
fun SingleTag(tagString: String, action: ((input: String) -> Boolean)?) {
    var bgColour = remember { mutableStateOf( Colours.Dark_Background) }
    TextButton(modifier = Modifier
        .padding(4.dp)
        .background(
            color = bgColour.value,
            shape = RoundedCornerShape(4.dp)
        ),
        onClick = {
            if (action != null) {
                action(tagString)
                bgColour.value = Colours.Dark_Readable
            }
        }) {
        Text(text = tagString, color = Color.White)
    }
}
