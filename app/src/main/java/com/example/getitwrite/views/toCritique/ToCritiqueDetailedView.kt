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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
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
fun ToCritiqueDetailedView(user: User, isCritiqueFrenzy: Boolean, toCritique: RequestCritique, navigateUp: () -> Unit) {
    var errorString = remember { mutableStateOf("") }
    val overallFeedback = remember { mutableStateOf("") }
    val paragraphs = toCritique.text.split("\n")
    val sheetState = rememberModalBottomSheetState()
    var bottomSheetText by remember { mutableStateOf(Triple("", 1, "")) }
    var comments = remember { mutableStateOf(mutableStateMapOf<String, Long>()) }
    Column {
        DetailHeader(title = toCritique.workTitle, navigateUp = navigateUp)
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
                    CritiqueSheet(bottomSheetText) { comment: String, index: Int ->
                        comments.value.put(comment, index.toLong())
                        bottomSheetText = Triple("", 1, "")
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
            paragraphs.forEachIndexed { index, element ->
                if (comments.value.containsValue(index.toLong())) {
                    Text(element, style = TextStyle(background = Colours.bold,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        letterSpacing = 0.5.sp), modifier = Modifier.clickable { bottomSheetText = Triple(element, index, "") })
                } else {
                    Text(element, modifier = Modifier.clickable { bottomSheetText = Triple(element, index, "") })
                }
            }
            Divider()
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding()) {
                Spacer(modifier = Modifier.weight(1.0f))
                Text(
                    text = "Comments ${comments.value.size}",
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
            ErrorText(error = errorString)
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val id = UUID.randomUUID().toString()
                    val critique = Critique(id, comments = comments.value, overallFeedback = overallFeedback.value, critiquerId = user.id, text = toCritique.text, title = toCritique.workTitle, projectTitle = toCritique.title, critiquerName = user.displayName, critiquerProfileColour = user.colour, timestamp = Timestamp.now(), rated = false)
                    Firebase.firestore.collection("users").document(toCritique.writerId)
                        .collection("critiques").document(id).set(critique)
                        .addOnSuccessListener {
                            if (!isCritiqueFrenzy) {
                                Firebase.firestore.collection("users").document(user.id)
                                    .collection("requestCritiques").document(toCritique.id).delete()
                            }
                            navigateUp()
                        }
                        .addOnFailureListener {
                            errorString.value = it.message.toString()
                        }
                },
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
fun CritiqueSheet(pair: Triple<String, Int, String>, submit: (String, Int) -> Unit) {
    val comment = remember { mutableStateOf(pair.third) }
    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "Paragraph:", fontWeight = FontWeight.Bold)
        Text(text = pair.first)
        OutlinedTextField(value = comment.value, maxLines = 5,
            onValueChange = { comment.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            label = { Text(text = "Comment") }
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { submit(comment.value, pair.second) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Colours.Dark_Readable,
                contentColor = Color.White
            )
        ) {
            Text("Add Comment", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}