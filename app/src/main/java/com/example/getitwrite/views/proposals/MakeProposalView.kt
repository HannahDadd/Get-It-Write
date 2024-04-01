package com.example.getitwrite.views.proposals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.getitwrite.GlobalVariables
import com.example.getitwrite.views.components.CreateTagCloud
import com.example.getitwrite.views.components.SelectTagCloud

@Composable
fun MakeProposalView() {
    val title = remember { mutableStateOf("") }
    val wordCount = remember { mutableStateOf("") }
    val blurb = remember { mutableStateOf("") }
    val authorNotes = remember { mutableStateOf("") }
    val genreTags = ArrayList<String>()
    val triggerWarnings = ArrayList<String>()
    var projectType = ""
    Column(modifier = Modifier
        .padding(20.dp)
        .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        OutlinedTextField(value = title.value,
            maxLines = 1,
            onValueChange = { title.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Title") }
        )
        OutlinedTextField(value = blurb.value,
            maxLines = 5,
            onValueChange = { blurb.value = it },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            label = { Text(text = "Blurb") }
        )
        SelectTagCloud(question = "Genres", answers = GlobalVariables.genres) {
            genreTags.add(it)
        }
        OutlinedTextField(value = authorNotes.value,
            maxLines = 5,
            onValueChange = { authorNotes.value = it },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            label = { Text(text = "Author's notes") }
        )
        SelectTagCloud(question = "Select project type", answers = GlobalVariables.typeOfProject) {
            projectType = it
            return@SelectTagCloud true
        }
        OutlinedTextField(value = wordCount.value,
            maxLines = 1,
            onValueChange = { wordCount.value = it },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            label = { Text(text = "Word Count") }
        )
        CreateTagCloud(question = "Trigger Warnings") {
            triggerWarnings.add(it)
        }
    }
}