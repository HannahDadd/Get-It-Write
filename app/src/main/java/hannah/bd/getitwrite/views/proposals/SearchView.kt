package hannah.bd.getitwrite.views.proposals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.modals.Question
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.critiqueFrenzy.FreeForAll
import hannah.bd.getitwrite.views.critiqueFrenzy.FrenzyFeed
import hannah.bd.getitwrite.views.critiqueFrenzy.MakeFrenzyView
import hannah.bd.getitwrite.views.critiqueFrenzy.QuickQueryCritique
import hannah.bd.getitwrite.views.critiqueFrenzy.getCritiqued
import hannah.bd.getitwrite.views.critiqueFrenzy.getCritiques
import hannah.bd.getitwrite.views.critiqueFrenzy.getQuestions
import hannah.bd.getitwrite.views.critiqueFrenzy.getToCritiques
import hannah.bd.getitwrite.views.feed.AIPromo
import hannah.bd.getitwrite.views.feed.CritiquedWord
import hannah.bd.getitwrite.views.feed.HomeFeed
import hannah.bd.getitwrite.views.feed.HomeSheetContent
import hannah.bd.getitwrite.views.feed.JoinTheConvo
import hannah.bd.getitwrite.views.feed.RecomendedCritiquers
import hannah.bd.getitwrite.views.feed.WorkToCritique
import hannah.bd.getitwrite.views.forum.ForumFeed
import hannah.bd.getitwrite.views.forum.QuestionDetailView
import hannah.bd.getitwrite.views.messages.MessagesNavHost
import hannah.bd.getitwrite.views.positivityCorner.MakePositiveCorner
import hannah.bd.getitwrite.views.positivityCorner.PositiveFeedback
import hannah.bd.getitwrite.views.positivityCorner.PositivityPopUp
import hannah.bd.getitwrite.views.toCritique.CritiquedDetailedView
import hannah.bd.getitwrite.views.toCritique.ToCritiqueDetailedView

@Composable
fun SearchNavHost(user: User) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "search") {
        composable("search") {
            SearchView(user = user, navController = navController)
        }
        composable(
            "genre/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            arguments.getString("id")?.let { ProposalNavHost(it, navController, user) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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