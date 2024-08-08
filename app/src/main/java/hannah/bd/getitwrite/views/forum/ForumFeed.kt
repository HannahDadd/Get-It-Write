package hannah.bd.getitwrite.views.forum

import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import hannah.bd.getitwrite.modals.ContentToReportType
import hannah.bd.getitwrite.modals.Question
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.ProfileImage
import hannah.bd.getitwrite.views.components.ReportAndBlockUser
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.views.components.DetailHeader
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumFeed(navController: NavController, user: User, questionList: MutableState<List<Question>?>) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var addedQuestions = remember { mutableStateListOf<Question>() }
    Column {
        DetailHeader(title = "Questions", navigateUp = { navController.navigateUp() })
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Ask question") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                    onClick = { showBottomSheet = true }
                )
            }
        ) { innerPadding ->
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    MakeQuestionView(user) {
                        addedQuestions.plus(it)
                        showBottomSheet = false
                    }
                }
            }
            LazyColumn(Modifier.padding(innerPadding)) {
                items(addedQuestions) {
                    ForumView(user, it, true, { })
                }
                itemsIndexed(questionList.value!!) { index, q ->
                    ForumView(user, q, true, { navController.navigate("question/$index") })
                }
            }
        }
    }
}

@Composable
fun ForumView(user: User, question: Question, isFeed: Boolean, select: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(10.dp)
            .clickable { select(question.id) }) {
        Text(question.question, fontWeight = FontWeight.Bold)
        if (isFeed) {
            ReportAndBlockUser(userToBlock = question.questionerId,
                user = user,
                contentToReport = question,
                contentToReportType = ContentToReportType.questions,
                questionId = null,
                chatId = null)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            Text(
                text = DateUtils.getRelativeTimeSpanString(
                    (question.timestamp.seconds * 1000),
                    System.currentTimeMillis(),
                    DateUtils.DAY_IN_MILLIS
                ).toString(),
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.weight(1.0f))
        }
        Divider()
    }
}

class QuestionsViewModel() : ViewModel() {
    val questionsFlow = flow {
        val documents = Firebase.firestore.collection("questions")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get().await()
        val items = documents.map { doc ->
            Question(doc.id, doc.data)
        }
        emit(items)
    }
}