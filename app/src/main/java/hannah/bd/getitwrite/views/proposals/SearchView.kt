package hannah.bd.getitwrite.views.proposals

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hannah.bd.getitwrite.modals.User

@Composable
fun SearchView(user: User, navController: NavHostController) {
    LazyColumn {
        item {
            FindPartnersByAudience(navController)
        }
        item {
            FindPartnersByGenre(navController)
        }
    }
}