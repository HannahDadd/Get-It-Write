package com.example.getitwrite.views.toCritique

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getitwrite.Colours
import com.example.getitwrite.modals.Critique
import com.example.getitwrite.modals.RequestCritique
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.DetailHeader
import com.example.getitwrite.views.components.ErrorText
import com.example.getitwrite.views.components.TagCloud
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CritiquedDetailedView(critique: Critique, navigateUp: () -> Unit) {
    val paragraphs = critique.text.split("\n")
    val sheetState = rememberModalBottomSheetState()
    var bottomSheetText by remember { mutableStateOf(Triple("", 1, "")) }
    val comments = critique.comments.map {(k,v)-> v to k}.toMap<Long, String>()
    Column {
        DetailHeader(title = critique.projectTitle, navigateUp = navigateUp)
        Column(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (bottomSheetText.first != "") {
                ModalBottomSheet(
                    onDismissRequest = {
                        bottomSheetText = Triple("", 1, "")
                    },
                    sheetState = sheetState
                ) {
                    CritiquedSheet(bottomSheetText)
                }
            }
            paragraphs.forEachIndexed { index, element ->
                if (critique.comments.containsValue(index.toLong())) {
                    Text(element, style = TextStyle(background = Colours.bold, fontSize = 16.sp), modifier = Modifier.clickable { bottomSheetText = Triple(element, index, comments.get(index.toLong()) ?: "")})
                } else {
                    Text(element)
                }
            }
            Divider()
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding()) {
                Spacer(modifier = Modifier.weight(1.0f))
                Text(
                    text = "Comments ${critique.comments.size}",
                    fontWeight = FontWeight.Light
                )
            }
            Text(text = "Overall feedback:", fontWeight = FontWeight.Bold)
            Text(text = critique.overallFeedback)
        }
    }
}

@Composable
fun CritiquedSheet(pair: Triple<String, Int, String>) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "Paragraph:", fontWeight = FontWeight.Bold)
        Text(text = pair.first)
        Text(text = "Comment:", fontWeight = FontWeight.Bold)
        Text(text = pair.third)
    }
}