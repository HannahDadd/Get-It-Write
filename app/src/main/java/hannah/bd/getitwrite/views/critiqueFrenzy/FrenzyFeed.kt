package hannah.bd.getitwrite.views.critiqueFrenzy

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.toCritique.ToCritiqueView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrenzyFeed(user: User, proposals: List<Proposal>, requests: List<RequestCritique>, select: (String) -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val newEntries = remember { mutableStateListOf<RequestCritique>() }
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add") },
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
                MakeFrenzyView(user, proposals) {
                    newEntries.plus(it)
                    showBottomSheet = false
                }
            }
        }
        LazyColumn(Modifier.padding(innerPadding)) {
            items(newEntries) {
                ToCritiqueView(it, select)
                Divider()
            }
            items(requests) { q ->
                ToCritiqueView(q, select)
                Divider()
            }
        }
    }
}