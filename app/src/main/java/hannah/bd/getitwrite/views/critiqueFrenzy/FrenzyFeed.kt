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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.theme.AppTypography
import hannah.bd.getitwrite.views.components.DetailHeader
import hannah.bd.getitwrite.views.components.TagCloud
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrenzyFeed(navController: NavController, dbName: String, placeholder: String, user: User, requests: MutableState<List<RequestCritique>?>) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    val newEntries = remember { mutableStateListOf<RequestCritique>() }
    val title = if (dbName == "frenzy") "No partners, no swaps, just feedback" else "Queries"
    Column {
        DetailHeader(title = title, navigateUp = { navController.navigateUp() })
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
                    MakeFrenzyView(user, dbName, placeholder) {
                        newEntries.plus(it)
                        showBottomSheet = false
                    }
                }
            }
            LazyColumn(Modifier.padding(innerPadding)) {
                items(newEntries) {
                    ToCritiqueFrenzyView(it, {})
                    Divider()
                }
                itemsIndexed(requests.value!!) {index, item ->
                    ToCritiqueFrenzyView(item, { navController.navigate("frenzy/$index") })
                    Divider()
                }
            }
        }
    }
}

@Composable
fun ToCritiqueFrenzyView(requestCritique: RequestCritique, select: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(10.dp)
            .clickable { select() }) {
        Text(text = requestCritique.genres.joinToString(),
            style = AppTypography.titleMedium)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding()) {
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