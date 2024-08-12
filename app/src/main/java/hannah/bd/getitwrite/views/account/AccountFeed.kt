package hannah.bd.getitwrite.views.account

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.critiqueFrenzy.getCritiqued
import hannah.bd.getitwrite.views.critiqueFrenzy.getCritiques
import hannah.bd.getitwrite.views.critiqueFrenzy.getQuestions
import hannah.bd.getitwrite.views.critiqueFrenzy.getToCritiques
import hannah.bd.getitwrite.views.feed.CritiquedWord
import hannah.bd.getitwrite.views.feed.HomeFeed
import hannah.bd.getitwrite.views.messages.MessagesNavHost
import hannah.bd.getitwrite.views.proposals.FindPartnersByAudience
import hannah.bd.getitwrite.views.proposals.FindPartnersByGenre
import hannah.bd.getitwrite.views.proposals.ProposalNavHost
import hannah.bd.getitwrite.views.toCritique.CritiquedDetailedView

@Composable
fun AccountNavHost(user: User) {
    var critiqued = remember { mutableStateOf<List<Critique>?>(null) }
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        getCritiqued(user,
            onSuccess = { critiqued.value = it },
            onError = { exception -> }
        )
    }

    NavHost(navController = navController, startDestination = "account") {
        composable("account") {
            AccountView(user = user, navController, critiqued)
        }
        composable("messages") {
            MessagesNavHost(navController, user)
        }
        composable(
            "critiqued/{index}",
            arguments = listOf(navArgument("index") { type = NavType.StringType })
        ) { backStackEntry ->
            requireNotNull(backStackEntry.arguments).getString("index")?.let {
                critiqued.value?.get(index = it.toInt())?.let {
                    CritiquedDetailedView(it, { navController.navigateUp() })
                }
            }
        }
    }
}

@Composable
fun AccountView(user: User, navController: NavHostController, critiqued: MutableState<List<Critique>?>) {
    LazyColumn {
        item {
            CritiquedWord(navController, critiqued)
        }
    }
}