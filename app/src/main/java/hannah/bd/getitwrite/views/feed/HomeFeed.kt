package hannah.bd.getitwrite.views.feed

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
import hannah.bd.getitwrite.views.positivityCorner.MakePositiveCorner
import hannah.bd.getitwrite.views.positivityCorner.PositiveFeedback
import hannah.bd.getitwrite.views.positivityCorner.PositivityPopUp
import hannah.bd.getitwrite.views.proposals.FindPartnersByAudience
import hannah.bd.getitwrite.views.proposals.FindPartnersByGenre
import hannah.bd.getitwrite.views.proposals.ProposalNavHost
import hannah.bd.getitwrite.views.critiqueFrenzy.QuickQueryCritique
import hannah.bd.getitwrite.views.critiqueFrenzy.getCritiqued
import hannah.bd.getitwrite.views.critiqueFrenzy.getCritiques
import hannah.bd.getitwrite.views.critiqueFrenzy.getQuestions
import hannah.bd.getitwrite.views.critiqueFrenzy.getToCritiques
import hannah.bd.getitwrite.views.forum.ForumFeed
import hannah.bd.getitwrite.views.forum.ForumView
import hannah.bd.getitwrite.views.forum.QuestionDetailView
import hannah.bd.getitwrite.views.messages.ChatsFeed
import hannah.bd.getitwrite.views.messages.MessagesNavHost
import hannah.bd.getitwrite.views.toCritique.CritiquedDetailedView
import hannah.bd.getitwrite.views.toCritique.ToCritiqueDetailedView

@Composable
fun FeedNavHost(user: User) {
    val navController = rememberNavController()
    var frenzies = remember { mutableStateOf<List<RequestCritique>?>(null) }
    var queries = remember { mutableStateOf<List<RequestCritique>?>(null) }
    var questions = remember { mutableStateOf<List<Question>?>(null) }
    var toCritiques = remember { mutableStateOf<List<RequestCritique>?>(null) }

    LaunchedEffect(Unit) {
        getCritiques("frenzy",
            onSuccess = { frenzies.value = it },
            onError = { exception -> }
        )
        getCritiques("queries",
            onSuccess = { queries.value = it },
            onError = { exception -> }
        )
        getQuestions(
            onSuccess = { questions.value = it },
            onError = { exception -> }
        )
        getToCritiques(user,
            onSuccess = { toCritiques.value = it },
            onError = { exception -> }
        )
    }

    NavHost(navController = navController, startDestination = "feed") {
        composable("feed") {
            HomeFeed(user = user, questions = questions, toCritiques = toCritiques, navController,
                frenzies, queries)
        }
        composable("frenzyFeed") {
            FrenzyFeed(navController, "frenzy", "Text", user = user, requests = frenzies)
        }
        composable(
            "frenzy/{index}",
            arguments = listOf(navArgument("index") { type = NavType.StringType })
        ) { backStackEntry ->
            requireNotNull(backStackEntry.arguments).getString("index")?.let {
                frenzies.value?.get(index = it.toInt())?.let {
                    ToCritiqueDetailedView(user, true, it, navController)
                }
            }
        }
        composable("queryFeed") {
            FrenzyFeed(navController, "queries", "Query", user = user, requests = queries)
        }
        composable(
            "queries/{index}",
            arguments = listOf(navArgument("index") { type = NavType.StringType })
        ) { backStackEntry ->
            requireNotNull(backStackEntry.arguments).getString("index")?.let {
                frenzies.value?.get(index = it.toInt())?.let {
                    ToCritiqueDetailedView(user, true, it, navController)
                }
            }
        }
        composable("questionFeed") {
            ForumFeed(navController, user = user, questions)
        }
        composable(
            "question/{index}",
            arguments = listOf(navArgument("index") { type = NavType.StringType })
        ) { backStackEntry ->
            requireNotNull(backStackEntry.arguments).getString("index")?.let {
                questions.value?.get(index = it.toInt())?.let {
                    QuestionDetailView(it, user, navController = navController, backStackEntry)
                }
            }
        }
        composable(
            "toCritique/{index}",
            arguments = listOf(navArgument("index") { type = NavType.StringType })
        ) { backStackEntry ->
            requireNotNull(backStackEntry.arguments).getString("index")?.let {
                toCritiques.value?.get(index = it.toInt())?.let {
                    ToCritiqueDetailedView(user, false, it, navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFeed(user: User, questions: MutableState<List<Question>?>, toCritiques: MutableState<List<RequestCritique>?>,
             navController: NavHostController, frenzies: MutableState<List<RequestCritique>?>,
             queries: MutableState<List<RequestCritique>?>
) {
    var bottomSheet by remember { mutableStateOf(HomeSheetContent.none) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
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
                HomeSheetContent.makeNewCritiqueFrenzy -> {
                    MakeFrenzyView(user = user, "frenzy", "Text") {
                        bottomSheet = HomeSheetContent.none
                    }
                }
                HomeSheetContent.makeNewQueryFrenzy -> {
                    MakeFrenzyView(user = user, "queries", "Query") {
                        bottomSheet = HomeSheetContent.none
                    }
                }
            }
        }
    }
    LazyColumn {
        item {
            WorkToCritique(user.displayName, navController, toCritiques)
        }
        item {
            RecomendedCritiquers()
        }
        item {
            QuickQueryCritique(queries, navController) {
                bottomSheet = HomeSheetContent.makeNewQueryFrenzy
            }
        }
        item {
            PositiveFeedback(onTap = { bottomSheet = HomeSheetContent.positiveReview }) {
                bottomSheet = HomeSheetContent.makeNewPositive
            }
        }
        item {
            JoinTheConvo(navController, questions)
        }
        item {
            FreeForAll(frenzies, navController = navController) {
                bottomSheet = HomeSheetContent.makeNewCritiqueFrenzy
            }
        }
        item {
            AIPromo()
        }
    }
}

enum class HomeSheetContent {
    none,
    positiveReview,
    makeNewPositive,
    makeNewCritiqueFrenzy,
    makeNewQueryFrenzy,
}