package hannah.bd.getitwrite.views.toCritique

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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import hannah.bd.getitwrite.modals.ContentToReportType
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.DetailHeader
import hannah.bd.getitwrite.views.components.FindPartnersText
import hannah.bd.getitwrite.views.components.ProfileImage
import hannah.bd.getitwrite.views.components.ReportAndBlockUser
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@Composable
fun CritiquedFeed(user: User, critiques: List<Critique>, selectCritique: (String) -> Unit, navigateUp: () -> Unit) {
    if (critiques.isEmpty()) {
        Column {
            DetailHeader(title = "Your Work, Critiqued", navigateUp = navigateUp)
            Column(Modifier.padding(10.dp)) {
                Text("None of your work has been critiqued.", fontWeight = FontWeight.Bold)
                FindPartnersText()
            }
        }
    } else {
        Column {
            DetailHeader(title = "Your Work, Critiqued", navigateUp = navigateUp)
            LazyColumn(Modifier.padding(10.dp)) {
                items(critiques) { work ->
                    CritiqueView(user = user, critique = work, selectCritique)
                    Divider()
                }
            }
        }
    }
}

@Composable
fun CritiqueView(user: User, critique: Critique, select: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp).clickable { select(critique.id) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(username = critique.critiquerName, profileColour = critique.critiquerProfileColour)
            Text(critique.critiquerName, fontSize = 20.sp)
        }
        ReportAndBlockUser(
            userToBlock = critique.critiquerId,
            user = user,
            contentToReport = critique,
            contentToReportType = ContentToReportType.CRITIQUE,
            questionId = null,
            chatId = null
        )
        Text(text = critique.overallFeedback,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.fillMaxWidth().padding()) {
            Text(
                text = DateUtils.getRelativeTimeSpanString(
                    (critique.timestamp.seconds * 1000),
                    System.currentTimeMillis(),
                    DateUtils.DAY_IN_MILLIS
                ).toString(),
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = "${critique.comments.size} comments",
                fontWeight = FontWeight.Light
            )
        }
    }
}

class CritiquedViewModel(user: User) : ViewModel() {
    val critiqued = flow {
        if (user.id != "") {
            val documents = Firebase.firestore.collection("users/${user.id}/critiques")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get().await()
            val items = documents.map { doc ->
                Critique(doc.id, doc.data)
            }
            emit(items)
        } else {
            emit(listOf<Critique>())
        }
    }
}