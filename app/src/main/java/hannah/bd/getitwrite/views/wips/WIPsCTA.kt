package hannah.bd.getitwrite.views.wips

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
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
import hannah.bd.getitwrite.modals.WIP

@Composable
fun WIPsCTA() {
    val context = LocalContext.current
    var wips by remember { mutableStateOf(listOf<WIP>()) }
    var showNewWipDialog by remember { mutableStateOf(false) }
    var selectedWip by remember { mutableStateOf<WIP?>(null) }

    LaunchedEffect(Unit) {
        val prefs = context.getSharedPreferences("user_defaults", Context.MODE_PRIVATE)
        val json = prefs.getString("wips", null)
        json?.let {
            val type = object : TypeToken<List<WIP>>() {}.type
            wips = Gson().fromJson(it, type)
        }
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

        Divider()

        LazyColumn {
            items(wips) { wip ->
                WIPView(wip = wip, onClick = { selectedWip = it })
            }
        }
    }

    if (showNewWipDialog) {
        Dialog(onDismissRequest = { showNewWipDialog = false }) {
            Surface(shape = RoundedCornerShape(8.dp)) {
                NewWIP(existingWips = wips) {
                    wips = it
                    showNewWipDialog = false
                }
            }
        }
    }

    selectedWip?.let {
        Dialog(onDismissRequest = { selectedWip = null }) {
            Surface(shape = RoundedCornerShape(8.dp)) {
                GraphForWIP(wip = it)
            }
        }
    }
}
