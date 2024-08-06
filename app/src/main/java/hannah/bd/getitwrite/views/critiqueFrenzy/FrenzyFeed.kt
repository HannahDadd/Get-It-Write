package hannah.bd.getitwrite.views.critiqueFrenzy

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.toCritique.ToCritiqueView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.modals.RequestFrenzy
import hannah.bd.getitwrite.views.components.DetailHeader
import hannah.bd.getitwrite.views.components.TagCloud
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrenzyFeed(navController: NavController, user: User, requests: MutableState<List<RequestFrenzy>?>, select: (String) -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val newEntries = remember { mutableStateListOf<RequestFrenzy>() }
    Column {
        DetailHeader(title = "No partners, no swaps, just feedback", navigateUp = { navController.navigateUp() })
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
                    MakeFrenzyView(user) {
                        newEntries.plus(it)
                        showBottomSheet = false
                    }
                }
            }
            LazyColumn(Modifier.padding(innerPadding)) {
                items(newEntries) {
                    ToCritiqueFrenzyView(it, select)
                    Divider()
                }
                items(requests.value!!) {
                    ToCritiqueFrenzyView(it, select)
                    Divider()
                }
            }
        }
    }
}

@Composable
fun ToCritiqueFrenzyView(requestCritique: RequestFrenzy, selectProposal: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp).clickable { selectProposal(requestCritique.id) }) {
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