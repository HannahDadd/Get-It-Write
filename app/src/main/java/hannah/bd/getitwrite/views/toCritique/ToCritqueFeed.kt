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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.FindPartnersText
import hannah.bd.getitwrite.views.components.TagCloud
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@Composable
fun ToCritiqueFeed(toCritiques: List<RequestCritique>, selectCritiqueRequest: (String) -> Unit) {
    if (toCritiques.isEmpty()) {
        Column(Modifier.padding(10.dp)) {
            Text("You have nothing to critique.", fontWeight = FontWeight.Bold)
            FindPartnersText()
        }
    } else {
        LazyColumn(Modifier.padding(10.dp)) {
            items(toCritiques) { work ->
                ToCritiqueView(work, selectCritiqueRequest)
                Divider()
            }
        }
    }
}

@Composable
fun ToCritiqueView(requestCritique: RequestCritique, selectProposal: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp).clickable { selectProposal(requestCritique.id) }) {
        Text(requestCritique.workTitle, fontSize = 20.sp)
        Text(requestCritique.title, fontWeight = FontWeight.Bold)
        Text(requestCritique.blurb)
        TagCloud(tags = requestCritique.genres, action = null)
        Row(modifier = Modifier.fillMaxWidth().padding()) {
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = DateUtils.getRelativeTimeSpanString(
                    (requestCritique.timestamp.seconds * 1000),
                    System.currentTimeMillis(),
                    DateUtils.DAY_IN_MILLIS
                ).toString(),
                fontWeight = FontWeight.Light
            )
        }
    }
}

class ToCritiqueViewModel(user: User) : ViewModel() {
    val toCritiques = flow {
        if (user.id != "") {
            val documents = Firebase.firestore.collection("users/${user.id}/requestCritiques")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get().await()
            val items = documents.map { doc ->
                RequestCritique(doc.id, doc.data)
            }
            emit(items)
        } else {
            emit(listOf<RequestCritique>())
        }
    }
}