package hannah.bd.getitwrite.views.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hannah.bd.getitwrite.Colours
import hannah.bd.getitwrite.modals.ContentToReportType
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.modals.UserGeneratedContent
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportAndBlockUser(userToBlock: String,
                       user: User,
                       contentToReport: UserGeneratedContent,
                       contentToReportType: ContentToReportType,
                       questionId: String?,
                       chatId: String?) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var showButtons = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val shouldShowDialog = remember { mutableStateOf(false) }
    if (shouldShowDialog.value) {
        MyAlertDialog(shouldShowDialog = shouldShowDialog, user = user, blockUserId = userToBlock)
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            ReportContent(contentToReport = contentToReport,
                user = user,
                contentToReportType = contentToReportType,
                questionId = questionId,
                chatId = chatId)
        }
    }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            Spacer(modifier = Modifier.weight(1.0f))
            TextButton(onClick = { showButtons.value = !showButtons.value }) {
                Icon(Icons.Filled.Info, contentDescription = "", Modifier.padding(end = 10.dp))
            }
        }
        if (showButtons.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
            ) {
                TextButton(onClick = { showBottomSheet = true }) {
                    Row {
                        Icon(Icons.Filled.Warning,
                            contentDescription = "",
                            Modifier
                                .padding(end = 10.dp)
                                .size(10.dp),
                            tint = MaterialTheme.colorScheme.error,)
                        Text("Report content",
                            style = AppTypography.labelSmall,
                            color = MaterialTheme.colorScheme.error)
                    }
                }
                Spacer(modifier = Modifier.weight(1.0f))
                TextButton(onClick = { shouldShowDialog.value = true }) {
                    Text("Block user",
                        style = AppTypography.labelSmall,
                        color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun MyAlertDialog(shouldShowDialog: MutableState<Boolean>, user: User, blockUserId: String) {
    var lowerTextString = remember { mutableStateOf("You cannot undo this.") }
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            title = { Text(text = "Are you sure you want to block this user?") },
            text = { Text(text = lowerTextString.value) },
            confirmButton = {
                Button(
                    onClick = {
                        val blockedUserIds = user.blockedUserIds
                        blockedUserIds.add(blockUserId)
                        Firebase.firestore.collection("users").document(user.id)
                            .set(
                                User(
                                    id = user.id,
                                    displayName = user.displayName,
                                    bio = user.bio,
                                    writing = user.writing,
                                    critiqueStyle = user.critiqueStyle,
                                    authors = user.authors,
                                    writingGenres = user.writingGenres,
                                    colour = user.colour,
                                    blockedUserIds = blockedUserIds
                                )
                            )
                            .addOnSuccessListener {
                                shouldShowDialog.value = false
                            }
                            .addOnFailureListener { e ->
                                lowerTextString.value = "There's been a problem. Please try again later."
                            }
                    }
                ) {
                    Text(
                        text = "Confirm",
                        color = Color.White
                    )
                }
            }
        )
    }
}

@Composable
fun ReportContent(contentToReport: UserGeneratedContent,
                  user: User,
                  contentToReportType: ContentToReportType,
                  questionId: String?,
                  chatId: String?) {
    var errorString = remember { mutableStateOf("") }
    val comments = remember { mutableStateOf("") }
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Would you like to add any notes about the content you are reporting?")
        OutlinedTextField(
            value = comments.value,
            maxLines = 5,
            onValueChange = { comments.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Text(
            text = "The content will be reviewed and appropriate action will be taken, which can include deleting the perpetrators account. Upon refresh, you'll find the content has been removed from the app.",
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.weight(1.0f))
        ErrorText(error = errorString)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                Firebase.firestore.collection("reportedContent").document(contentToReport.id).set(contentToReport)
                    .addOnSuccessListener {
                        if (contentToReportType == ContentToReportType.CRITIQUE) {
                            Firebase.firestore.collection("users").document(user.id)
                                .collection("critiques").document(contentToReport.id).delete()
                        } else if (contentToReportType == ContentToReportType.REQUESTCRITIQUE) {
                            Firebase.firestore.collection("users").document(user.id)
                                .collection("requestCritiques").document(contentToReport.id).delete()
                        } else {
                            questionId?.let {
                                Firebase.firestore.collection("questions").document(it)
                                    .collection("replies").document(contentToReport.id).delete()
                            } ?: run {
                                chatId?.let {
                                    Firebase.firestore.collection("chats").document(it)
                                        .collection("messages").document(contentToReport.id).delete()
                                } ?: run {
                                    if (contentToReportType == ContentToReportType.questions) {
                                        Firebase.firestore.collection("questions").document(contentToReport.id).delete()
                                    } else if (contentToReportType == ContentToReportType.proposals) {
                                        Firebase.firestore.collection("proposals").document(contentToReport.id).delete()
                                    } else {
                                        errorString.value = "Content not valid."
                                    }
                                }
                            }
                        }
                        errorString.value = "Reported!"
                    }
                    .addOnFailureListener {
                        errorString.value = it.message.toString()
                    }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Colours.Dark_Readable, contentColor = Color.White)
        ) {
            Text("Report", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}