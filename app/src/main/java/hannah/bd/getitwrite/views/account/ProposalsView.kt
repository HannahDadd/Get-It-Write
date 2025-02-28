package hannah.bd.getitwrite.views.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.HomePageTileButton
import hannah.bd.getitwrite.views.components.TitleAndSubText

@Composable
fun ProposalsSection(navController: NavController, proposals: MutableState<List<Proposal>?>, onCreate: () -> Unit) {
    proposals.value?.let {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth()
        ) {
            TitleAndSubText(
                title = "Your WIPs.",
                "Your current projects.",
                MaterialTheme.colorScheme.onSurface
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(it) {index, item ->
                    HomePageTileButton(
                        title = "${item.title}",
                        bubbleText = "${item.wordCount} words",
                        icon = Icons.Default.Edit,
                        isFirstItemInCarousel = (index == 0),
                        isLastItemInCarousel = (index == ( it.size - 1)),
                        onClick = {
                            navController.navigate("proposal/$index")
                        })
                }
            }
        }
    } ?: run {
        Text("Loading...", modifier = Modifier.padding(16.dp))
    }
}

fun getProposals(user: User,
                 onSuccess: (List<Proposal>) -> Unit,
                 onError: (Exception) -> Unit) {
    Firebase.firestore.collection("proposals")
        .whereEqualTo("writerId", user.id)
        .get()
        .addOnSuccessListener { documents ->
            if (documents != null) {
                val items = documents.map { doc ->
                    Proposal(doc.id, doc.data)
                }
                onSuccess(items)
            } else {
                onError(Exception("Data not found"))
            }
        }
        .addOnFailureListener { exception ->
            onError(exception)
        }
}