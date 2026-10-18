package hannah.bd.getitwrite.views.homepage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hannah.bd.getitwrite.GlobalVariables
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.modals.AppDatabase
import hannah.bd.getitwrite.modals.Stat
import hannah.bd.getitwrite.modals.UserPreferences
import hannah.bd.getitwrite.views.ghosts.Ghost
import hannah.bd.getitwrite.views.ghosts.SingleGhostView
import hannah.bd.getitwrite.views.settings.settingsView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuView(
    growAction: () -> Unit,
    db: AppDatabase?,
    navController: NavController,
    userPreferences: UserPreferences,
) {
    val wordsWritten by userPreferences
        .wordsWritten
        .collectAsState(initial = 0)
    var stats by remember { mutableStateOf(listOf<Stat>()) }

    val stage = ((wordsWritten / 5000) + 1)
        .coerceIn(1, 20)

    var showButtons by remember {
        mutableStateOf(false)
    }

    var showSettings by remember {
        mutableStateOf(false)
    }

    val buttonSize = 75.dp

    LaunchedEffect(Unit) {

        db?.let {
            stats = db.statDao().getAll()
        }
    }

    var quote by remember { mutableStateOf("") }

    val ghosts = remember {
        listOf(
            Ghost(
                imageRes = R.drawable.ghost_wizard,
                ghostName = "ghostly",
                comments = GlobalVariables.ghostlyGhostQuotes,
                width = 60.dp,
                height = 80.dp
            ),
            Ghost(
                imageRes = R.drawable.ghost_sid,
                ghostName = "sid",
                comments = GlobalVariables.sidGhostQuotes,
                width = 60.dp,
                height = 70.dp
            ),
            Ghost(
                imageRes = R.drawable.ghost_marge,
                ghostName = "margerie",
                comments = GlobalVariables.margerieGhostQuotes,
                width = 60.dp,
                height = 75.dp
            ),
            Ghost(
                imageRes = R.drawable.ghost_gilly,
                ghostName = "gilly",
                comments = GlobalVariables.gillyGhostQuotes,
                width = 60.dp,
                height = 60.dp
            ),
            Ghost(
                imageRes = R.drawable.ghost_oldman,
                ghostName = "gramp",
                comments = GlobalVariables.grampGhostQuotes,
                width = 60.dp,
                height = 85.dp
            ),
            Ghost(
                imageRes = R.drawable.ghost_paula,
                ghostName = "paula",
                comments = GlobalVariables.paulineGhostQuotes,
                width = 60.dp,
                height = 60.dp
            )
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        ghosts.forEach { ghost ->

            SingleGhostView(
                ghost = ghost,
                onGhostTapped = {
                    quote = "hello"
                    Log.d("Ghost", "tap")
                }
            )
        }

        if (quote.isNotEmpty()) {
            Text(
                text = quote,
                color = Color.Black,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White)
                    .clickable {
                        quote = ""
                    }
                    .padding(16.dp)
                    .wrapContentSize()
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp)
                .padding(bottom = 70.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.menu_btn),
                contentDescription = "Menu",
                modifier = Modifier
                    .size(buttonSize)
                    .clickable {
                        growAction()
                    }
            )
        }
    }

    if (showSettings) {

        ModalBottomSheet(
            onDismissRequest = {
                showSettings = false
            }
        ) {
            settingsView(navController)
        }
    }
}