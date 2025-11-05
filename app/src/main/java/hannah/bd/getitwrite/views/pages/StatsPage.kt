package hannah.bd.getitwrite.views.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.modals.AppDatabase
import hannah.bd.getitwrite.modals.WIP
import hannah.bd.getitwrite.views.components.HeadlineAndSubtitle
import hannah.bd.getitwrite.views.graphs.GraphForWriter
import hannah.bd.getitwrite.views.wips.NewWIP
import hannah.bd.getitwrite.views.wips.WIPsCTA

@Composable
fun StatsPage(db: AppDatabase?) {
    var wips by remember { mutableStateOf(listOf<WIP>()) }
    var createWip by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        HeadlineAndSubtitle(
            title = "Your Writing Stats",
            subtitle = "Writing games to keep you on top form."
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                WIPsCTA(db)
                GraphForWriter(db)
            }
        }
    }

    if (createWip) {
        NewWIP(
            db = db,
            existingWips = wips,
            onWipAdded = { newWips ->
                wips = newWips
                createWip = false
            }
        )
    }
}
