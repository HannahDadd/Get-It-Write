package hannah.bd.getitwrite.views.feed

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.Question
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.proposals.FindPartnersByAudience
import hannah.bd.getitwrite.views.proposals.FindPartnersByGenre
import hannah.bd.getitwrite.views.proposals.ProposalNavHost
import hannah.bd.getitwrite.views.proposals.ProposalView

@Composable
fun HomeFeed(user: User, questions: List<Question>, critiqueFrenzy: List<RequestCritique>, toCritiques: List<RequestCritique>,
             navController: NavHostController
) {
    LazyColumn {
        item {
            WorkToCritique(user.displayName, toCritiques)
        }
        item {
            CritiquedWord()
        }
        item {
            RecomendedCritiquers()
        }
        item {
            AIPromo()
        }
        item {
            JoinTheConvo(questions)
        }
        item {
            FindPartnersByAudience()
        }
        item {
            QuickQueryCritique()
        }
        item {
            FreeForAll()
        }
        item {
            PositiveFeedback()
        }
        item {
            FindPartnersByGenre(navController)
        }
    }
}

@Composable
fun FeedNavHost(user: User, questions: List<Question>, critiqueFrenzy: List<RequestCritique>, toCritiques: List<RequestCritique>) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "feed") {
        composable(
            "genre/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            arguments.getString("id")?.let { ProposalNavHost(it) }
        }
        composable("feed") {
            HomeFeed(user = user, questions = questions, critiqueFrenzy = critiqueFrenzy, toCritiques = toCritiques, navController)
        }
    }
}