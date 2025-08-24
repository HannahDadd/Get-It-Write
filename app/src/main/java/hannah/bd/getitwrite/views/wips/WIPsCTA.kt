package hannah.bd.getitwrite.views.wips

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import hannah.bd.getitwrite.modals.AppDatabase
import hannah.bd.getitwrite.modals.WIP
import hannah.bd.getitwrite.views.graphs.GraphForWIP

@Composable
fun WIPsCTA(db: AppDatabase) {
    val context = LocalContext.current
    var wips by remember { mutableStateOf(listOf<WIP>()) }
    var showNewWipDialog by remember { mutableStateOf(false) }
    var selectedWip by remember { mutableStateOf<WIP?>(null) }

    LaunchedEffect(Unit) {
        wips = db.wipDao().getAll()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Your WIPs", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add WIP",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { showNewWipDialog = true }
            )
        }

        if (wips.isEmpty()) {
            Text("Add your writing projects here.")
        }

        HorizontalDivider()

        LazyColumn {
            items(wips) { wip ->
                WIPView(wip = wip, onClick = { selectedWip = wip })
            }
        }
    }

    if (showNewWipDialog) {
        Dialog(onDismissRequest = { showNewWipDialog = false }) {
            Surface(shape = RoundedCornerShape(8.dp)) {
                NewWIP(db, existingWips = wips) {
                    wips = it
                    showNewWipDialog = false
                }
            }
        }
    }

    selectedWip?.let {
        Dialog(onDismissRequest = { selectedWip = null }) {
            Surface(shape = RoundedCornerShape(8.dp)) {
                GraphForWIP(db, wip = it)
            }
        }
    }
}
