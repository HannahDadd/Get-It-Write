package hannah.bd.getitwrite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import hannah.bd.getitwrite.modals.AppDatabase
import hannah.bd.getitwrite.theme.GetItWriteTheme
import hannah.bd.getitwrite.views.badges.BadgePage
import hannah.bd.getitwrite.views.pages.GamesPage
import hannah.bd.getitwrite.views.pages.HomepagePage
import hannah.bd.getitwrite.views.pages.StatsPage
import hannah.bd.getitwrite.views.sprints.SprintStack

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

    @Composable
    fun MainPage(navController: NavHostController) {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        Scaffold(
            bottomBar = {
                NavigationBar {
                    val currentDestination = navController.currentDestination?.route
                    NavigationBarItem(
                        selected = currentDestination == "home",
                        onClick = { navController.navigate("home") },
                        icon = { Icon(Icons.Default.Home, contentDescription = null) }
                    )
                    NavigationBarItem(
                        selected = currentDestination == "stats",
                        onClick = { navController.navigate("stats") },
                        icon = { Icon(Icons.Default.Person, contentDescription = null) }
                    )
                    NavigationBarItem(
                        selected = currentDestination == "badges",
                        onClick = { navController.navigate("badges") },
                        icon = { Icon(Icons.Default.CheckCircle, contentDescription = null) }
                    )
                    NavigationBarItem(
                        selected = currentDestination == "games",
                        onClick = { navController.navigate("games") },
                        icon = { Icon(Icons.Default.Edit, contentDescription = null) }
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") { HomepagePage(navController) }
                composable("stats") { StatsPage() }
                composable("badges") { BadgePage() }
                composable("games") { GamesPage(navController) }
                composable("sprint") {
                    SprintStack(db, onFinish = { navController.popBackStack() })
                }
                composable("streak") {
                    ExtendStreak(onDone = { navController.popBackStack() })
                }
                composable("vocabGame") {
                    VocabGame(onDone = { navController.popBackStack() })
                }
                composable("editingGame") {
                    EditingQuestion(onBack = { navController.popBackStack() })
                }
            }
        }
    }
}