package com.example.getitwrite.views.toCritique

import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.getitwrite.Colours
import com.example.getitwrite.modals.Critique
import com.example.getitwrite.modals.RequestCritique
import com.example.getitwrite.views.components.DetailHeader
import com.example.getitwrite.views.components.TagCloud
import com.example.getitwrite.views.messages.ChatView
import com.example.getitwrite.views.messages.ChatViewViewModel
import com.example.getitwrite.views.proposals.MakeProposalView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToCritiqueDetailedView(toCritiques: List<RequestCritique>, id: String, navigateUp: () -> Unit) {
    val toCritique = toCritiques.filter { it.id == id }.get(0)
    val overallFeedback = remember { mutableStateOf("") }
    val paragraphs = toCritique.text.split("\n")
    val sheetState = rememberModalBottomSheetState()
    var bottomSheetText by remember { mutableStateOf("") }
    Column {
        DetailHeader(title = toCritique.workTitle, navigateUp = navigateUp)
        Column(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (bottomSheetText != "") {
                ModalBottomSheet(
                    onDismissRequest = {
                        bottomSheetText = ""
                    },
                    sheetState = sheetState
                ) {
                    CritiqueSheet(bottomSheetText) {
                        bottomSheetText = ""
                    }
                }
            }
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(toCritique.title, fontWeight = FontWeight.Bold)
                Text("by ${toCritique.writerName}")
            }
            Divider()
            Text("Blurb", fontWeight = FontWeight.Bold)
            Text(toCritique.blurb)
            TagCloud(tags = toCritique.triggerWarnings, action = null)
            Divider()
            LazyColumn(Modifier.padding(10.dp)) {
                items(paragraphs) { text ->
                    Text(text, modifier = Modifier.clickable {  })
                }
            }
            Text(toCritique.text)
            Divider()
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding()) {
                Spacer(modifier = Modifier.weight(1.0f))
                Text(
                    text = "Comments",
                    fontWeight = FontWeight.Light
                )
            }
            OutlinedTextField(value = overallFeedback.value,
                maxLines = 5,
                onValueChange = { overallFeedback.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                label = { Text(text = "Overall Feedback") }
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colours.Dark_Readable,
                    contentColor = Color.White
                )
            ) {
                Text("Submit Critique", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CritiqueSheet(text: String, submit: (String) -> Unit) {
    val comment = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "Paragraph:", fontWeight = FontWeight.Bold)
        Text(text = text)
        OutlinedTextField(value = comment.value, maxLines = 5,
            onValueChange = { comment.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            label = { Text(text = "Comment") }
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { submit(comment.value) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Colours.Dark_Readable,
                contentColor = Color.White
            )
        ) {
            Text("Submit Critique", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}