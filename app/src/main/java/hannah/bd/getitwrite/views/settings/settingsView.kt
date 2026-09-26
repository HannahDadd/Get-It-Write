package hannah.bd.getitwrite.views.settings

import android.R.attr.padding
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hannah.bd.getitwrite.R

@Composable
fun CategoryItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp), horizontalArrangement = Arrangement.spacedBy(30.dp)) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(28.dp), tint = MaterialTheme.colorScheme.onSurface)
            Text(title, style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily(Font(R.font.dynapuff)),)
        }
    }
}

@Composable
fun AppVersion(versionText: String, copyrights: String, onClick: () -> Unit) {
    Surface(onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), horizontalArrangement = Arrangement.spacedBy(30.dp)) {
            Box(
                modifier = Modifier.size(30.dp),
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(versionText, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(0.44f))
                Text(copyrights, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(0.44f))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun settingsView(navController: NavController) {
    val listState = rememberLazyListState()
    val hasScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemScrollOffset > 0
        }
    }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            LazyColumn(contentPadding = PaddingValues(16.dp), modifier = Modifier.widthIn(max = 600.dp), state = listState) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item { CategoryItem(title = "Support the app!", icon = Icons.Outlined.Favorite, onClick = {
                    navController.navigate("supportUs") })
                }
                item { HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp)) }
                item { CategoryItem(title = "How it Works", icon = Icons.Outlined.Info, onClick = {
                    navController.navigate("howItWorks")
                }) }
                item { CategoryItem(title = "Meet the Family", icon = Icons.Outlined.AccountCircle, onClick = {
                    navController.navigate("meetTheFamily")
                }) }
//                item { CategoryItem(title = "Our Other Apps", icon = Icons.Outlined.AddCircle, onClick = {
//                    navController.navigate("otherApps") })
//                }
                item { HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp)) }
                item { CategoryItem(title = "Privacy Policy", icon = Icons.Outlined.Lock, onClick = {
                    navController.navigate("privacyPolicy")
                }) }
                item { CategoryItem(title = "Terms and Conditions", icon = Icons.Outlined.Settings, onClick = {
                    navController.navigate("tsAndCs")
                }) }
//                item { CategoryItem(title = "Send Feedback", icon = Icons.Outlined.Email, onClick = { /*TODO*/ }) }
                item { HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp)) }
//                item { HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp)) }
//                item { AppVersion(versionText = "Version 1.0.0", copyrights = "© 2024 Your Company", onClick = { /* TODO Add easter egg after 8 times is clicked */ }) }
            }
        }
}