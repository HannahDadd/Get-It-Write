package hannah.bd.getitwrite.views.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import hannah.bd.getitwrite.modals.WIP
import hannah.bd.getitwrite.views.graphs.GraphForWriter
import hannah.bd.getitwrite.views.wips.WIPsCTA

@Composable
fun StatsPage() {
    var wips by remember { mutableStateOf(listOf<WIP>()) }
    var createWip by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        HeadlineAndSubtitle(
            title = "Your Writing Stats",
            subtitle = "Writing games to keep you on top form."
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                WIPsCTA()
                GraphForWriter()
            }
        }
    }

    if (createWip) {
        NewWIPDialog(
            wips = wips,
            onDismiss = { createWip = false },
            onCreate = { newWips ->
                wips = newWips
                createWip = false
            }
        )
    }
}
