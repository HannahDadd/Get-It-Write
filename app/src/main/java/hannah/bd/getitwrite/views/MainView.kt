package hannah.bd.getitwrite.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.Question
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.User
import com.google.firebase.auth.FirebaseAuth
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.modals.RequestPositivity
import hannah.bd.getitwrite.views.feed.FeedNavHost
import hannah.bd.getitwrite.views.feed.HomeFeed
import hannah.bd.getitwrite.views.feed.ShowBottomNav
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(user: User, logoutNavController: NavHostController,
             navController: NavController,
             recs: MutableState<List<User>?>,
             questions: MutableState<List<Question>?>,
             toCritiques: MutableState<List<RequestCritique>?>,
             hostNavController: NavHostController,
             frenzies: MutableState<List<RequestCritique>?>,
             queries: MutableState<List<RequestCritique>?>,
             critiqued: MutableState<List<Critique>?>,
             queryCritiques: MutableState<List<Critique>?>,
             positiveCritiques: MutableState<List<RequestPositivity>?>,
             proposals: MutableState<List<Proposal>?>,
             frenzy: MutableState<List<Critique>?>
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(Modifier.padding(10.dp)) {
                    TextButton(onClick = { navController.navigate("profile") }) {
                        Row {
                            Icon(Icons.Filled.Face, contentDescription = "", Modifier.padding(end = 10.dp))
                            Text("Profile", fontSize = 18.sp)
                        }
                    }
                    TextButton(onClick = { navController.navigate("settings") }) {
                        Row {
                            Icon(Icons.Filled.Settings, contentDescription = "", Modifier.padding(end = 10.dp))
                            Text("Settings", fontSize = 18.sp)
                        }
                    }
                    TextButton(onClick = {
                        FirebaseAuth.getInstance().signOut()
                        logoutNavController.navigate("login")
                    }) {
                        Row {
                            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "", Modifier.padding(end = 10.dp))
                            Text("Logout", fontSize = 18.sp)
                        }
                    }
                }
            }
        },
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.words),
                            modifier = Modifier.width(150.dp),
                            contentDescription = "",
                            contentScale = ContentScale.FillWidth
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    actions = {
                        val context = LocalContext.current
                        Row(modifier = Modifier
                            .clickable {
                                Toast.makeText(context,
                                    "Collect stars by critiquing other writers' work.",
                                    Toast.LENGTH_SHORT).show() },
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                            ) {
                            user.stars?.let {
                                Text(text = it.toString(),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            } ?: run {
                                Text(text = "0",
                                    color = MaterialTheme.colorScheme.onPrimary)
                            }
                            Icon(
                                imageVector = Icons.Filled.Star,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
        ) { contentPadding ->
            Column(modifier = Modifier.padding(contentPadding)) {
                ShowBottomNav(user = user, questions = questions,recs, toCritiques = toCritiques,
                    hostNavController, frenzies, queries,
                    critiqued = critiqued, queryCritiques = queryCritiques, positiveCritiques = positiveCritiques,
                    proposals= proposals, frenzy = frenzy)
            }
        }
    }
}
