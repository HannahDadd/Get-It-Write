package hannah.bd.getitwrite.views

import androidx.compose.foundation.Image
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import hannah.bd.getitwrite.views.feed.ShowFeed
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import hannah.bd.getitwrite.Colours
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.Question
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.User
import com.google.firebase.auth.FirebaseAuth
import hannah.bd.getitwrite.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(logoutNavController: NavHostController, questions: List<Question>, toCritiques: List<RequestCritique>,
             critiqueFrenzy: List<RequestCritique>, navController: NavController, proposals: List<Proposal>, selectProposal: (Proposal) -> Unit,
             selectChat: (String, String, String) -> Unit, user: User, selectCritiqueRequest: (String) -> Unit,
             selectQuestion: (String) -> Unit, selectFrenzy: (String) -> Unit) {
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
                    TextButton(onClick = { navController.navigate("yourWork") }) {
                        Row {
                            Icon(Icons.Filled.CheckCircle, contentDescription = "", Modifier.padding(end = 10.dp))
                            Text("Your Work", fontSize = 18.sp)
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
                        containerColor = Colours.bold,
                        titleContentColor = Colours.Dark_Readable,
                    ),
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.words),
                            modifier = Modifier.width(150.dp),
                            contentDescription = "",
                            contentScale = ContentScale.FillWidth
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
        ) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                ShowFeed(user = user, questions = questions, critiqueFrenzy = critiqueFrenzy, toCritiques = toCritiques, proposals = proposals,
                    selectProposal = selectProposal, selectChat = selectChat, selectCritiqueRequest = selectCritiqueRequest,
                    selectQuestion = selectQuestion, selectFrenzy = selectFrenzy)
            }
        }
    }
}
