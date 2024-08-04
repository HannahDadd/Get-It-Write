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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.Colours
import hannah.bd.getitwrite.modals.ContentToReportType
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.RequestPositivity
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.theme.AppTypography
import hannah.bd.getitwrite.views.components.CheckInput
import hannah.bd.getitwrite.views.components.ErrorText
import hannah.bd.getitwrite.views.components.ReportAndBlockUser
import hannah.bd.getitwrite.views.proposals.ProposalsViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID

@Composable
fun PositivityPopUp(user: User,
    close: () -> Unit
) {
    val peice by PositivityViewModel().randPiece.collectAsState(initial = null)
    val comment = remember { mutableStateOf("") }
    var errorString = remember { mutableStateOf<String?>(null) }

//    LaunchedEffect(Unit) {
//        getIds(
//            onSuccess = { returnedIds ->
//                ids = returnedIds
//                isLoading = false
//            },
//            onError = { exception ->
//                errorString.value = exception.message
//                isLoading = false
//            }
//        )
//    }

    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        peice?.let {
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
                        if(CheckInput.isStringGood(comment.value, 50)) {
                            val id = it.id
                            val newComments = it.comments.plus(Pair(user.displayName, comment.value))
                            val critique = RequestPositivity(id = id, text = it.text, writerId = it.writerId, writerName = it.writerName, comments = newComments)
                            Firebase.firestore.collection("users").document(it.writerId)
                                .collection("positivityPeices").document(id).set(critique)
                                .addOnSuccessListener {
                                    Firebase.firestore.collection("positivityPeices").document(id)
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
                    Text("Submit Critique", Modifier.padding(10.dp), style = AppTypography.titleMedium)
                }
            }
            item {
                it.comments.forEach {
                    Divider()
                    Text(it.key, Modifier.padding(10.dp), style = AppTypography.titleMedium)
                    Text(it.value, Modifier.padding(10.dp), style = AppTypography.bodyMedium)
                }
            }
        } ?: item { Text("Loading...") }
    }
}

class PositivityViewModel : ViewModel() {
    val randPiece = flow {
        val ids = Firebase.firestore.collection("positivityPeices")
            .document("ids").get().await()
        val idsArray = ids["ids"] as MutableList<String>
        val doc = Firebase.firestore.collection("positivityPeices")
            .document(idsArray.random()).get().await()
        doc.data?.let {
            emit(RequestPositivity(id = doc.id, data = it))
        } ?: run {
            emit(null)
        }
    }
}