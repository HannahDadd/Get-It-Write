package hannah.bd.getitwrite.views.sprints

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.modals.WIP
import hannah.bd.getitwrite.views.wips.WIPView

@Composable
fun SelectWip(
    onWipSelected: (WIP) -> Unit
) {
    val context = LocalContext.current
    var wips by remember { mutableStateOf(listOf<WIP>()) }

    LaunchedEffect(Unit) {
        // Get items from data store
//        val EXAMPLE_COUNTER = intPreferencesKey("example_counter")
//        val exampleCounterFlow: Flow<Int> = context.dataStore.data
//            .map { preferences ->
//                // No type safety.
//                preferences[EXAMPLE_COUNTER] ?: 0
//            }
//
//        val prefs = context.getSharedPreferences("user_defaults", Context.MODE_PRIVATE)
//        val json = prefs.getString("wips", null)
//        json?.let {
//            val type = object : TypeToken<List<WIP>>() {}.type
//            wips = Gson().fromJson(it, type)
//        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Your WIPs", style = MaterialTheme.typography.headlineMedium)
        if (wips.isEmpty()) {
            Text("No writing projects yet.")
        } else {
            LazyColumn {
                items(wips) { wip ->
                    WIPView(wip = wip, onClick = { onWipSelected(wip) })
                }
            }
        }
    }
}
