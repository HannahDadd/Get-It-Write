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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.Question
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.RequestPositivity
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.account.StatsView
import hannah.bd.getitwrite.views.account.getProposals
import hannah.bd.getitwrite.views.critiqueFrenzy.FrenzyFeed
import hannah.bd.getitwrite.views.positivityCorner.PositiveFeedback
import hannah.bd.getitwrite.views.positivityCorner.PositivityPopUp
import hannah.bd.getitwrite.views.proposals.ProposalNavHost
import hannah.bd.getitwrite.views.critiqueFrenzy.getCritiqued
import hannah.bd.getitwrite.views.critiqueFrenzy.getCritiques
import hannah.bd.getitwrite.views.critiqueFrenzy.getPositivities
import hannah.bd.getitwrite.views.critiqueFrenzy.getQuestions
import hannah.bd.getitwrite.views.critiqueFrenzy.getToCritiques
import hannah.bd.getitwrite.views.forum.ForumFeed
import hannah.bd.getitwrite.views.forum.JoinTheConvo
import hannah.bd.getitwrite.views.forum.QuestionDetailView
import hannah.bd.getitwrite.views.messages.ShowMessages
import hannah.bd.getitwrite.views.positivityCorner.PositivityCritique
import hannah.bd.getitwrite.views.profile.ProfileView
import hannah.bd.getitwrite.views.proposals.ProposalDetails
import hannah.bd.getitwrite.views.proposals.SearchView
import hannah.bd.getitwrite.views.toCritique.CritiquedDetailedView
import hannah.bd.getitwrite.views.toCritique.CritiquedFeed
import hannah.bd.getitwrite.views.toCritique.ToCritiqueDetailedView

@Composable
fun FeedNavHost(user: User, logoutNavController: NavHostController, hostnavController: NavController) {
    val navController = rememberNavController()
    var frenzies = remember { mutableStateOf<List<RequestCritique>?>(null) }
    var queries = remember { mutableStateOf<List<RequestCritique>?>(null) }
    var questions = remember { mutableStateOf<List<Question>?>(null) }
    var toCritiques = remember { mutableStateOf<List<RequestCritique>?>(null) }
    var critiqued = remember { mutableStateOf<List<Critique>?>(null) }
    var frenzy = remember { mutableStateOf<List<Critique>?>(null) }
    var positives = remember { mutableStateOf<List<RequestPositivity>?>(null) }
    var queriesToCritique = remember { mutableStateOf<List<Critique>?>(null) }
    var proposals = remember { mutableStateOf<List<Proposal>?>(null) }
    var recs = remember { mutableStateOf<List<User>?>(null) }

    LaunchedEffect(Unit) {
        getRecs(user,
            onSuccess = { recs.value = it },
            onError = { exception -> }
        )
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
        getCritiqued(user, "critiques",
            onSuccess = { critiqued.value = it },
            onError = { exception -> }
        )
        getPositivities(user, "positivityPeices",
            onSuccess = { positives.value = it },
            onError = { exception -> }
        )
        getCritiqued(user, "queries",
            onSuccess = { queriesToCritique.value = it },
            onError = { exception -> }
        )
        getCritiqued(user, "frenzy",
            onSuccess = { frenzy.value = it },
            onError = { exception -> }
        )
        getProposals(user,
            onSuccess = { proposals.value = it },
            onError = { exception -> }
        )
    }

    NavHost(navController = navController, startDestination = "bottomNav") {
        composable("bottomNav") {
            ShowBottomNav(
                user = user,
                questions = questions,
                recs = recs,
                toCritiques = toCritiques,
                hostNavController = navController,
                frenzies = frenzies,
                queries = queries,
                critiqued = critiqued,
                queryCritiques = queriesToCritique,
                positiveCritiques = positives,
                proposals = proposals,
                frenzy = frenzy
            )
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
                queries.value?.get(index = it.toInt())?.let {
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
        composable(
            "proposal/{index}",
            arguments = listOf(navArgument("index") { type = NavType.StringType })
        ) { backStackEntry ->
            requireNotNull(backStackEntry.arguments).getString("index")?.let {
                proposals.value?.get(index = it.toInt())?.let {
                    ProposalDetails(
                        proposal = it,
                        user = user,
                        isOwn = true,
                    navController = navController
                    )
                }
            }
        }
        composable("critiquedFeed") {
            CritiquedFeed(user, critiqued, navController)
        }
        composable("critiquedFrenzy-Feed") {
            CritiquedFeed(user, frenzy, navController)
        }
        composable("criquedQueries-Feed") {
            CritiquedFeed(user, queriesToCritique, navController)
        }
        composable(
            "critiqued/{index}",
            arguments = listOf(navArgument("index") { type = NavType.StringType })
        ) { backStackEntry ->
            requireNotNull(backStackEntry.arguments).getString("index")?.let {
                critiqued.value?.get(index = it.toInt())?.let {
                    CritiquedDetailedView(it, user, { navController.navigateUp() })
                }
            }
        }
        composable(
            "critiquedId/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            requireNotNull(backStackEntry.arguments).getString("id")?.let {id ->
                critiqued.value?.filter { it.id == id }?.first()?.let {
                    CritiquedDetailedView(it, user, { navController.navigateUp() })
                }
            }
        }
        composable(
            "critiquedFrenzy/{index}",
            arguments = listOf(navArgument("index") { type = NavType.StringType })
        ) { backStackEntry ->
            requireNotNull(backStackEntry.arguments).getString("index")?.let {
                frenzy.value?.get(index = it.toInt())?.let {
                    CritiquedDetailedView(it, user, { navController.navigateUp() })
                }
            }
        }
        composable(
            "criquedPositives/{index}",
            arguments = listOf(navArgument("index") { type = NavType.StringType })
        ) { backStackEntry ->
            requireNotNull(backStackEntry.arguments).getString("index")?.let {
                positives.value?.get(index = it.toInt())?.let {
                    PositivityCritique(it, { navController.navigateUp() })
                }
            }
        }
        composable(
            "criquedQueries/{index}",
            arguments = listOf(navArgument("index") { type = NavType.StringType })
        ) { backStackEntry ->
            requireNotNull(backStackEntry.arguments).getString("index")?.let {
                queriesToCritique.value?.get(index = it.toInt())?.let {
                    CritiquedDetailedView(it, user, { navController.navigateUp() })
                }
            }
        }
        composable("stats") {
            StatsView(user = user, navController = navController)
        }
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
        composable(
            "usersProposals/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            arguments.getString("id")?.let { ProposalNavHost(it, navController, user, true) }
        }
        composable(
            "chatDetails/{chat_id}/{otherUserName}/{otherUserId}",
            arguments = listOf(
                navArgument("chat_id") {
                    type = NavType.StringType
                },
                navArgument("otherUserName") {
                    type = NavType.StringType
                },
                navArgument("otherUserId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ShowMessages(
                chatId = arguments.getString("chat_id")!!,
                user = user,
                user2Name = arguments.getString("otherUserName")!!,
                user2Id = arguments.getString("otherUserId")!!,
                backStackEntry = backStackEntry,
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFeed(user: User, recs: MutableState<List<User>?>, questions: MutableState<List<Question>?>, toCritiques: MutableState<List<RequestCritique>?>,
             navController: NavHostController, frenzies: MutableState<List<RequestCritique>?>,
             queries: MutableState<List<RequestCritique>?>
) {
    var bottomSheet by remember { mutableStateOf(HomeSheetContent.none) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var userInPopUp: User? = null
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
                HomeSheetContent.recommendedProfile -> {
                    userInPopUp?.let {
                        ProfileView(navController = navController, ownProfile = false,
                            loggedInUser = user, user = it) {

                        }
                    }
                }
            }
        }
    }
    LazyColumn {
        item {
            WorkToCritique(user.displayName, navController, toCritiques)
        }
//        item {
//            QuickQueryCritique(queries, navController)
//        }
        item {
            RecommendedCritiquers(recs, navController)
        }
        item {
            JoinTheConvo(navController, questions)
        }
        item {
            PositiveFeedback(onTap = { bottomSheet = HomeSheetContent.positiveReview })
        }
    }
}

enum class HomeSheetContent {
    none,
    positiveReview,
    recommendedProfile
}