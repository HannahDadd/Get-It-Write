package hannah.bd.getitwrite.views.account

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
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.critiqueFrenzy.MakeFrenzyView
import hannah.bd.getitwrite.views.critiqueFrenzy.getCritiqued
import hannah.bd.getitwrite.views.feed.HomeSheetContent
import hannah.bd.getitwrite.views.messages.MessagesNavHost
import hannah.bd.getitwrite.views.positivityCorner.MakePositiveCorner
import hannah.bd.getitwrite.views.positivityCorner.PositivityPopUp
import hannah.bd.getitwrite.views.toCritique.CritiquedDetailedView
import hannah.bd.getitwrite.views.toCritique.CritiquedFeed

@Composable
fun AccountNavHost(user: User) {
    var critiqued = remember { mutableStateOf<List<Critique>?>(null) }
    var frenzy = remember { mutableStateOf<List<Critique>?>(null) }
    var positives = remember { mutableStateOf<List<Critique>?>(null) }
    var queries = remember { mutableStateOf<List<Critique>?>(null) }
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        getCritiqued(user, "critiques",
            onSuccess = { critiqued.value = it },
            onError = { exception -> }
        )
        getCritiqued(user, "positivityPeices",
            onSuccess = { positives.value = it },
            onError = { exception -> }
        )
        getCritiqued(user, "queries",
            onSuccess = { queries.value = it },
            onError = { exception -> }
        )
        getCritiqued(user, "frenzies",
            onSuccess = { frenzy.value = it },
            onError = { exception -> }
        )
    }

    NavHost(navController = navController, startDestination = "account") {
        composable("account") {
            AccountView(user = user, navController, critiqued = critiqued,
                queryCritiques = queries, positiveCritiques = positives, frenzy = frenzy)
        }
        composable("messages") {
            MessagesNavHost(navController, user)
        }
        composable("critiquedFeed") {
            CritiquedFeed(user, critiqued, navController)
        }
        composable("criquedFrenzy-Feed") {
            CritiquedFeed(user, frenzy, navController)
        }
        composable("criquedQueries-Feed") {
            CritiquedFeed(user, queries, navController)
        }
        composable("criquedPositives-Feed") {
            CritiquedFeed(user, positives, navController)
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
        composable(
            "criquedFrenzy/{index}",
            arguments = listOf(navArgument("index") { type = NavType.StringType })
        ) { backStackEntry ->
            requireNotNull(backStackEntry.arguments).getString("index")?.let {
                frenzy.value?.get(index = it.toInt())?.let {
                    CritiquedDetailedView(it, { navController.navigateUp() })
                }
            }
        }
        composable(
            "criquedPositives/{index}",
            arguments = listOf(navArgument("index") { type = NavType.StringType })
        ) { backStackEntry ->
            requireNotNull(backStackEntry.arguments).getString("index")?.let {
                positives.value?.get(index = it.toInt())?.let {
                    CritiquedDetailedView(it, { navController.navigateUp() })
                }
            }
        }
        composable(
            "criquedQueries/{index}",
            arguments = listOf(navArgument("index") { type = NavType.StringType })
        ) { backStackEntry ->
            requireNotNull(backStackEntry.arguments).getString("index")?.let {
                queries.value?.get(index = it.toInt())?.let {
                    CritiquedDetailedView(it, { navController.navigateUp() })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountView(user: User, navController: NavHostController, critiqued: MutableState<List<Critique>?>,
                queryCritiques: MutableState<List<Critique>?>, positiveCritiques: MutableState<List<Critique>?>,
                frenzy: MutableState<List<Critique>?>) {
    var bottomSheet by remember { mutableStateOf(AccountSheetContent.none) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (bottomSheet != AccountSheetContent.none) {
        ModalBottomSheet(
            onDismissRequest = {
                bottomSheet = AccountSheetContent.none
            },
            sheetState = sheetState
        ) {
            when (bottomSheet) {
                AccountSheetContent.none -> {
                    Column{}
                }
                AccountSheetContent.makeNewPositive -> {
                    MakePositiveCorner(user = user) {
                        bottomSheet = AccountSheetContent.none
                    }
                }
                AccountSheetContent.makeNewCritiqueFrenzy -> {
                    MakeFrenzyView(user = user, "frenzy", "Text") {
                        bottomSheet = AccountSheetContent.none
                    }
                }
                AccountSheetContent.makeNewQueryFrenzy -> {
                    MakeFrenzyView(user = user, "queries", "Query") {
                        bottomSheet = AccountSheetContent.none
                    }
                }
            }
        }
    }
    LazyColumn {
        item {
            CritiquedWord(navController, critiqued)
        }
        item {
            PositiveCritiqued(frenzy, "Critique Frenzy",
                "No partners, no swaps, just feedback on your work.", "criquedFrenzy",
                navController, {
                bottomSheet = AccountSheetContent.makeNewCritiqueFrenzy
            })
        }
        item {
            PositiveCritiqued(positiveCritiques, "Positivity Corner",
                "Just positive comments on your work.", "criquedPositives",
                navController, {
                    bottomSheet = AccountSheetContent.makeNewPositive
                })
        }
        item {
            PositiveCritiqued(queryCritiques, "Query Critique",
                "Critiques on your query, posted on the main page.", "criquedQueries",
                navController, {
                    bottomSheet = AccountSheetContent.makeNewPositive
                })
        }
    }
}

enum class AccountSheetContent {
    none,
    makeNewPositive,
    makeNewCritiqueFrenzy,
    makeNewQueryFrenzy,
}