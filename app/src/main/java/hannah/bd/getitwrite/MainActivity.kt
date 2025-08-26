package hannah.bd.getitwrite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import hannah.bd.getitwrite.modals.AppDatabase
import hannah.bd.getitwrite.theme.GetItWriteTheme
import hannah.bd.getitwrite.views.graphs.GraphForWriter
import hannah.bd.getitwrite.views.sprints.SprintCTA
import hannah.bd.getitwrite.views.sprints.SprintStack
import hannah.bd.getitwrite.views.wips.WIPsCTA
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            GetItWriteTheme {
                Surface(tonalElevation = 5.dp) {
                    MainPage(navController)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainPage(navController: NavHostController = rememberNavController()) {

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Get It Write", style = MaterialTheme.typography.titleLarge) })
            },
            content = { it ->
                NavigationGraph(navController, it)
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Home(navController: NavHostController, paddingValues: PaddingValues) {
        val scrollState = rememberScrollState()
        val coroutineScope = rememberCoroutineScope()

        Column(modifier = Modifier.padding(paddingValues)) {
            HorizontalScrollButtons { index ->
                coroutineScope.launch {
                    scrollState.animateScrollTo(index * 500) // Approximate scroll position
                }
            }
            VerticalContent(scrollState, navController)
        }
    }

    @Composable
    fun HorizontalScrollButtons(onScrollTo: (Int) -> Unit) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(8.dp)
        ) { //"Writing Schedule" to 1
            val buttons = listOf("Sprint!" to 0, "Your WIPs" to 1, "Graphs" to 2)
            buttons.forEach { (label, index) ->
                Button(
                    onClick = { onScrollTo(index) },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(label, color = MaterialTheme.colorScheme.onPrimary)
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }

    @Composable
    fun VerticalContent(scrollState: ScrollState, navController: NavHostController) {

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            HorizontalDivider()
            SprintCTA {
                navController.navigate("sprint")
            }
//            Divider()
//            CommitmentCTA()
            HorizontalDivider()
            WIPsCTA(db)
            HorizontalDivider()
            GraphForWriter(db)
        }
    }

    @Composable
    fun NavigationGraph(navController: NavHostController, paddingValues: PaddingValues) {
        NavHost(navController, startDestination = "main") {
            composable("main") { Home(navController, paddingValues) }
            composable("sprint") { SprintStack(db) { navController.popBackStack() } }
        }
    }
}
