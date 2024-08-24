package hannah.bd.getitwrite.views.toCritique

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hannah.bd.getitwrite.modals.ContentToReportType
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.DetailHeader
import hannah.bd.getitwrite.views.components.ErrorText
import hannah.bd.getitwrite.views.components.ReportAndBlockUser
import hannah.bd.getitwrite.views.components.TagCloud
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.views.components.CheckInput
import java.util.UUID
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToCritiqueDetailedView(user: User, isCritiqueFrenzy: Boolean, toCritique: RequestCritique,
                           navController: NavController) {

    var errorString = remember { mutableStateOf<String?>(null) }
    val overallFeedback = remember { mutableStateOf("") }
    val paragraphs = toCritique.text.split("\n")
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var bottomSheetText by remember { mutableStateOf(Triple("", 1, "")) }
    var comments = remember { mutableStateOf(mutableStateMapOf<String, Long>()) }
    Column {
        DetailHeader(title = toCritique.workTitle, navigateUp = { navController.navigateUp() })
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
            if(!isCritiqueFrenzy) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(toCritique.title, fontWeight = FontWeight.Bold)
                    Text("by ${toCritique.writerName}")
                }
                Divider()
                Text("Blurb", fontWeight = FontWeight.Bold)
                ReportAndBlockUser(
                    userToBlock = toCritique.writerId,
                    user = user,
                    contentToReport = toCritique,
                    contentToReportType = ContentToReportType.REQUESTCRITIQUE,
                    questionId = null,
                    chatId = null
                )
                Text(toCritique.blurb)
                TagCloud(tags = toCritique.triggerWarnings, action = null)
                Divider()
            }
            paragraphs.forEachIndexed { index, element ->
                if (comments.value.containsValue(index.toLong())) {
                    Text(
                        text = element,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable { bottomSheetText = Triple(element, index, "") }
                    )
                } else {
                    Text(element,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.clickable { bottomSheetText = Triple(element, index, "") })
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
                    if (CheckInput.verifyCanBeEmpty(overallFeedback.value)) {
                        val id = UUID.randomUUID().toString()
                        val collectionName = if(toCritique.title == "Critique Frenzy") "critiquedFrenzy" else if(toCritique.title == "Query Frenzy") "critiquedQuery" else "critiques"
                        val critique = Critique(id, comments = comments.value, overallFeedback = overallFeedback.value,
                            critiquerId = user.id, text = toCritique.text, title = toCritique.workTitle,
                            projectTitle = toCritique.title, critiquerName = user.displayName,
                            critiquerProfileColour = user.colour, timestamp = Timestamp.now(), rated = false)
                        Firebase.firestore.collection("users").document(toCritique.writerId)
                            .collection(collectionName).document(id).set(critique)
                            .addOnSuccessListener {
                                if (!isCritiqueFrenzy) {
                                    Firebase.firestore.collection("users").document(user.id)
                                        .collection("requestCritiques").document(toCritique.id).delete()
                                }
                                user.lastCritique = Timestamp.now()
                                var lastFive = mutableListOf(Timestamp.now())
                                var freq = 0.0
                                user.lastFiveCritiques?.let {
                                    it.add(0, Timestamp.now())
                                    if (it.size > 5) {
                                        lastFive = it.subList(0, 5)
                                    } else {
                                        lastFive = it
                                    }

                                    val timesInMillis = it.map { it.toDate().time }
                                    val differencesInSeconds = timesInMillis.zipWithNext { first, second ->
                                        TimeUnit.MILLISECONDS.toSeconds(second - first)
                                    }
                                    val averageSeconds = if (differencesInSeconds.isNotEmpty()) {
                                        differencesInSeconds.average()
                                    } else {
                                        0.0
                                    }
                                    freq = averageSeconds.absoluteValue
                                }
                                user.lastFiveCritiques = lastFive
                                user.frequencey = freq.toLong()

                                Firebase.firestore.collection("users").document(user.id).set(user)
                                navController.navigateUp()
                            }
                            .addOnFailureListener {
                                errorString.value = it.message.toString()
                            }
                    } else {
                        errorString.value = "Overall feedback contains profanities. This is not allowed."
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary)
            ) {
                Text("Submit Critique", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CritiqueSheet(pair: Triple<String, Int, String>, submit: (String, Int) -> Unit) {
    var errorString = remember { mutableStateOf<String?>(null) }
    val comment = remember { mutableStateOf(pair.third) }
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "Paragraph:", fontWeight = FontWeight.Bold)
        Text(text = pair.first)
        ErrorText(error = errorString)
        OutlinedTextField(value = comment.value, maxLines = 5,
            onValueChange = { comment.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            label = { Text(text = "Comment") }
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (CheckInput.verify(comment.value)) {
                    submit(comment.value, pair.second)
                } else {
                    errorString.value = CheckInput.errorStringText
                }},
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary)
        ) {
            Text("Add Comment", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}