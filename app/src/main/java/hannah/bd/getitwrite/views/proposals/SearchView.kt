package hannah.bd.getitwrite.views.proposals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.forum.RectangleTileButtonNoDate

@Composable
fun SearchView(user: User, navController: NavHostController) {
    LazyColumn {
        item {
            Column(Modifier.padding(8.dp)) {
                RectangleTileButtonNoDate(
                    title = "View all",
                    backgroundColour = MaterialTheme.colorScheme.background,
                    textColour = MaterialTheme.colorScheme.onBackground,
                    padding = 16.dp,
                    onClick = { navController.navigate("genre/All") }
                )
            }
        }
        item {
            FindPartnersByAudience(navController)
        }
        item {
            FindPartnersByGenre(navController)
        }
    }
}