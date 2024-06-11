package hannah.bd.getitwrite.views.components

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
import hannah.bd.getitwrite.Colours

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
                addAction(text)
                text = ""
            })
        }
        TagCloud(tags = tagsInCloud, action = {
                tagsInCloud.remove(it)
        })
    }
}

@Composable
fun SelectTagCloud(question: String, answers: MutableList<String>, preSelectedTags: MutableList<String> = mutableListOf(), clickAction: (input: String) -> Boolean) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(question, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 4.dp))
        TagCloud(tags = answers, action = clickAction, preSelectedTags = preSelectedTags)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagCloud(tags: MutableList<String>, action: ((input: String) -> Boolean)?, preSelectedTags: MutableList<String>? = null) {
    FlowRow() {
        for (tag in tags) {
            if (preSelectedTags != null) {
                SingleTag(tag, action, preSelectedTags.contains(tag))
            } else {
                SingleTag(tag, action, false)
            }
        }
    }
}

@Composable
private fun SingleTag(tagString: String, action: ((input: String) -> Boolean)?, isSelected: Boolean) {
    var bgColour = remember { mutableStateOf( if(isSelected) Colours.Dark_Readable else Colours.Dark_Background) }
    TextButton(modifier = Modifier
        .padding(4.dp)
        .background(
            color = bgColour.value,
            shape = RoundedCornerShape(4.dp)
        ),
        onClick = {
            if (action != null) {
                bgColour.value = if (action(tagString)) Colours.Dark_Readable else Colours.Dark_Background
            }
        }) {
        Text(text = tagString, color = Color.White)
    }
}
