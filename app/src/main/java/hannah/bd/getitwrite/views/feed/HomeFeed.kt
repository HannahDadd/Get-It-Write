package hannah.bd.getitwrite.views.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import hannah.bd.getitwrite.modals.Question
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.ReportContent
import hannah.bd.getitwrite.views.positivityCorner.MakePositiveCorner
import hannah.bd.getitwrite.views.positivityCorner.PositiveFeedback
import hannah.bd.getitwrite.views.positivityCorner.PositivityPopUp
import hannah.bd.getitwrite.views.proposals.FindPartnersByAudience
import hannah.bd.getitwrite.views.proposals.FindPartnersByGenre
import hannah.bd.getitwrite.views.proposals.ProposalNavHost
import hannah.bd.getitwrite.views.toCritique.FreeForAll
import hannah.bd.getitwrite.views.toCritique.QuickQueryCritique

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFeed(user: User, questions: List<Question>, critiqueFrenzy: List<RequestCritique>, toCritiques: List<RequestCritique>,
             navController: NavHostController
) {
    var bottomSheet by remember { mutableStateOf(HomeSheetContent.none) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    if (bottomSheet != HomeSheetContent.none) {
        ModalBottomSheet(
            onDismissRequest = {
                bottomSheet = HomeSheetContent.none
            },
            sheetState = sheetState
        ) {
            when (bottomSheet) {
                HomeSheetContent.none -> {
                    Column{}
                }
                HomeSheetContent.positiveReview -> {
                    PositivityPopUp(user = user) {
                        bottomSheet = HomeSheetContent.none
                    }
                }
                HomeSheetContent.makeNewPositive -> {
                    MakePositiveCorner(user = user) {
                        bottomSheet = HomeSheetContent.none
                    }
                }
            }
        }
    }
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
            FindPartnersByAudience(navController)
        }
        item {
            QuickQueryCritique()
        }
        item {
            FreeForAll()
        }
        item {
            PositiveFeedback(onTap = { bottomSheet = HomeSheetContent.positiveReview }) {
                bottomSheet = HomeSheetContent.makeNewPositive
            }
        }
        item {
            FindPartnersByGenre(navController)
        }
    }
}

enum class HomeSheetContent {
    none,
    positiveReview,
    makeNewPositive
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
            arguments.getString("id")?.let { ProposalNavHost(it, navController, user) }
        }
        composable("feed") {
            HomeFeed(user = user, questions = questions, critiqueFrenzy = critiqueFrenzy, toCritiques = toCritiques, navController)
        }
    }
}