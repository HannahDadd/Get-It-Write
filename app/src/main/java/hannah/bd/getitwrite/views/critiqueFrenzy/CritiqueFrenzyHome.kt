package hannah.bd.getitwrite.views.critiqueFrenzy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.views.components.DetailHeader
import hannah.bd.getitwrite.views.components.ErrorText
import hannah.bd.getitwrite.views.components.SquareTileButton

@Composable
fun FreeForAll(requests: MutableState<List<RequestCritique>?>, navController: NavController, onCreate: () -> Unit) {
    if (requests.value?.isNotEmpty() == true) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "No partners, no swaps, just feedback.",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(16.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                item {
                    SquareTileButton(
                        title = "Add your own.",
                        wordCount = "",
                        backgroundColour = MaterialTheme.colorScheme.secondary,
                        textColour = MaterialTheme.colorScheme.onSecondary,
                        icon = Icons.Default.Add,
                        size = 150.dp,
                        onClick = onCreate
                    )
                }
                itemsIndexed(requests.value!!.subList(0, 5)) {index, item ->
                    SquareTileButton(
                        title = item.genres.joinToString(),
                        wordCount = "",
                        backgroundColour = MaterialTheme.colorScheme.primary,
                        textColour = MaterialTheme.colorScheme.onPrimary,
                        icon = Icons.Default.Email,
                        size = 150.dp,
                        onClick = { navController.navigate("frenzy/$index") }
                    )
                }
                item {
                    SquareTileButton(
                        title = "View more.",
                        wordCount = "",
                        backgroundColour = MaterialTheme.colorScheme.tertiaryContainer,
                        textColour = MaterialTheme.colorScheme.onTertiaryContainer,
                        icon = Icons.Default.ArrowForward,
                        size = 150.dp,
                        onClick = { navController.navigate("frenzyFeed") }
                    )
                }
            }
        }
    } else {
        Text("Loading...", modifier = Modifier.padding(16.dp))
    }
}

fun getCritiques(dbName: String,
    onSuccess: (List<RequestCritique>) -> Unit,
    onError: (Exception) -> Unit) {
    Firebase.firestore.collection(dbName)
        .orderBy("timestamp", Query.Direction.DESCENDING)
        .get()
        .addOnSuccessListener { documents ->
            if (documents != null) {
                val items = documents.map { doc ->
                    RequestCritique(doc.id, doc.data)
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