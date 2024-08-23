package hannah.bd.getitwrite.views.positivityCorner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.modals.ContentToReportType
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.RequestPositivity
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.theme.AppTypography
import hannah.bd.getitwrite.views.components.CheckInput
import hannah.bd.getitwrite.views.components.ErrorText
import hannah.bd.getitwrite.views.components.ReportAndBlockUser

@Composable
fun PositivityPopUp(user: User,
    close: () -> Unit
) {
    var piece = remember { mutableStateOf<RequestPositivity?>(null) }
    val comment = remember { mutableStateOf("") }
    var errorString = remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        getRandPeice(
            onSuccess = {
                piece.value = it
                isLoading = false
            },
            onError = { exception ->
                errorString.value = exception.message
                isLoading = false
            }
        )
    }

    if (isLoading) {
        Text("Loading...", modifier = Modifier.padding(16.dp))
    } else {
        errorString.value?.let {
            ErrorText(error = it)
        }?: run {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                piece.value?.let {
                    item {
                        Text(text = it.text)
                    }
                    item {
                        ReportAndBlockUser(
                            userToBlock = it.writerId,
                            user = user,
                            contentToReport = it,
                            contentToReportType = ContentToReportType.REQUESTCRITIQUE,
                            questionId = null,
                            chatId = null
                        )
                    }
                    item {
                        OutlinedTextField(value = comment.value,
                            maxLines = 5,
                            onValueChange = { comment.value = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            label = { Text(text = "Positive Comment") }
                        )
                    }
                    item {
                        ErrorText(error = errorString)
                    }
                    item {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                if (CheckInput.isStringGood(comment.value, 50)) {
                                    val id = it.id
                                    val newComments =
                                        it.comments + (user.displayName to comment.value)

                                    val critique = RequestPositivity(
                                        id = id,
                                        text = it.text,
                                        writerId = it.writerId,
                                        writerName = it.writerName,
                                        comments = newComments
                                    )
                                    Firebase.firestore.collection("users").document(it.writerId)
                                        .collection("positivityPeices").document(id).set(critique)
                                        .addOnSuccessListener {
                                            Firebase.firestore.collection("positivityPeices")
                                                .document(id)
                                                .set(critique)
                                                .addOnSuccessListener {
                                                    close()
                                                }
                                                .addOnFailureListener {
                                                    errorString.value = it.message.toString()
                                                }
                                        }
                                        .addOnFailureListener {
                                            errorString.value = it.message.toString()
                                        }
                                } else {
                                    errorString.value = CheckInput.errorStringText
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text(
                                "Submit Critique",
                                Modifier.padding(10.dp),
                                style = AppTypography.titleMedium
                            )
                        }
                    }
                    item {
                        it.comments.forEach {
                            Divider()
                            Text(it.key, Modifier.padding(10.dp), style = AppTypography.titleMedium)
                            Text(
                                it.value,
                                Modifier.padding(10.dp),
                                style = AppTypography.bodyMedium
                            )
                        }
                    }
                } ?: item { Text("Loading...") }
            }
        }
    }
}

fun getRandPeice(
    onSuccess: (RequestPositivity) -> Unit,
    onError: (Exception) -> Unit) {
    Firebase.firestore.collection("positivityPeices")
        .document("ids").get()
        .addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val idsArray = document.data?.get("ids") as MutableList<String>
                if (idsArray != null) {
                    Firebase.firestore.collection("positivityPeices")
                        .document(idsArray.random()).get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            val piece =
                                document.data?.let { RequestPositivity(id = document.id, data = it) }
                            if (piece != null) {
                                onSuccess(piece)
                            } else {
                                onError(Exception("Positivity Peice is null"))
                            }
                        } else {
                            onError(Exception("Data not found"))
                        }
                    }
                    .addOnFailureListener { exception ->
                        onError(exception)
                    }
                } else {
                    onError(Exception("Date in wrong format"))
                }
            } else {
                onError(Exception("Data not found"))
            }
        }
        .addOnFailureListener { exception ->
            onError(exception)
        }
}