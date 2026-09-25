package hannah.bd.shelfify

import android.Manifest
import android.app.Activity
import android.provider.Settings
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.room.Room
import com.example.compose.AppTheme
import com.hannah.shelfify.views.ghosts.ghostView
import hannah.bd.shelfify.modals.AppDatabase
import hannah.bd.shelfify.modals.UserPreferences
import hannah.bd.shelfify.modals.onboardingPages
import hannah.bd.shelfify.views.homepage.LibraryView
import hannah.bd.shelfify.views.homepage.MenuView
import hannah.bd.shelfify.views.homepage.NavigationStack
import hannah.bd.shelfify.views.homepage.Screen
import hannah.bd.shelfify.views.homepage.backGroundView
import hannah.bd.shelfify.views.homepage.growYourLibraryHomepage
import hannah.bd.shelfify.views.notifications.NotificationHelper
import hannah.bd.shelfify.views.onboarding.OnboardingOverlay
import hannah.bd.shelfify.views.sprints.liveUpdate.LiveUpdateSprintService
import hannah.bd.shelfify.views.sprints.liveUpdate.LiveUpdateNotificationManager
import kotlinx.coroutines.launch
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    var db: AppDatabase? = null

    private lateinit var notificationManager: LiveUpdateNotificationManager
    private var hasNotificationPermission by mutableStateOf(false)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasNotificationPermission = isGranted
        if (isGranted) {
            Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        NotificationHelper.createChannel(this)
        notificationManager = LiveUpdateNotificationManager(this)
        checkNotificationPermission()
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationStack(
                        modifier = Modifier.padding(innerPadding),
//                        hasPermission = hasNotificationPermission,
//                        canPostPromoted = notificationManager.canPostPromotedNotifications(),
//                        onRequestPermission = { requestNotificationPermission() },
                        startTwentyMinsActivity = { startWritingSprint("20mins") },
                        startFortyMinsActivity = { startWritingSprint("40mins") },
                        startSixtyMinsActivity = { startWritingSprint("60mins") },
                        db
                    )
                }
            }
        }
    }

    private fun checkNotificationPermission() {
        hasNotificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // Permissions automatically granted on older Android versions
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun startWritingSprint(delayLength: String) {
//        if (!hasNotificationPermission) {
//            Toast.makeText(this, "Please grant notification permission first", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val intent = Intent(this, LiveUpdateSprintService::class.java).apply {
//            action = LiveUpdateSprintService.ACTION_START
//        }
//        intent.putExtra("delayLength", delayLength)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent)
//        } else {
//            startService(intent)
//        }
//
        Toast.makeText(this, "Writing sprint started", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun AppMainPage(navController: NavController,
                userPreferences: UserPreferences,
                db: AppDatabase?,
//                onRequestPermission: () -> Unit,
                ) {

    val scope = rememberCoroutineScope()
    val hasSeenOnboarding by userPreferences
        .hasSeenOnboarding
        .collectAsState(initial = false)

    val wordsWritten by userPreferences
        .wordsWritten
        .collectAsState(initial = 0)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        backGroundView()
        LibraryView(wordsWritten)
        MenuView(
            { navController.navigate(Screen.Grow.route)},
            { navController.navigate(Screen.Stats.route)},
            {navController.navigate(Screen.Final.route)},
            db,
            navController = navController,
            userPreferences)

        if (!hasSeenOnboarding) {
            OnboardingOverlay(
                pages = onboardingPages,
                onFinished = {
                    scope.launch {
                        userPreferences.completeOnboarding()
//                        onRequestPermission()
                    }
                }
            )
        }
    }
}

@Composable
fun NotificationPermissionRequest() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

        val launcher =
            rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { granted ->
                // Optional: handle result
            }

        LaunchedEffect(Unit) {
            launcher.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }
    }
}