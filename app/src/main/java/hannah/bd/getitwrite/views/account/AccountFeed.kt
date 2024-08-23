package hannah.bd.getitwrite.views.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.RequestPositivity
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.Promo
import hannah.bd.getitwrite.views.critiqueFrenzy.MakeFrenzyView
import hannah.bd.getitwrite.views.feed.RectangleTileButtonNoDate
import hannah.bd.getitwrite.views.positivityCorner.MakePositiveCorner
import hannah.bd.getitwrite.views.proposals.MakeProposalView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountView(user: User, navController: NavHostController,
                critiqued: MutableState<List<Critique>?>,
                queryCritiques: MutableState<List<Critique>?>,
                positiveCritiques: MutableState<List<RequestPositivity>?>,
                proposals: MutableState<List<Proposal>?>,
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
                AccountSheetContent.makeNewProposals -> {
                    MakeProposalView(user = user) {
                        bottomSheet = AccountSheetContent.none
                    }
                }
            }
        }
    }
    LazyColumn {
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                RectangleTileButtonNoDate(
                    title = "Send work to your critique partners.",
                    backgroundColour = MaterialTheme.colorScheme.background,
                    textColour = MaterialTheme.colorScheme.onBackground,
                    padding = 16.dp,
                    icon = Icons.Default.Send,
                    onClick = { navController.navigate("messages") }
                )
            }
        }
        item {
            Promo(
                title = "Summarise your project so other critique partners can find it.",
                buttonText = "Add your novel idea to the mix",
                painter = painterResource(id = R.drawable.bookpromo),) {
                bottomSheet = AccountSheetContent.makeNewProposals
            }
        }
        item {
            ProposalsSection(navController, proposals) {
                bottomSheet = AccountSheetContent.makeNewProposals
            }
        }
        item {
            CritiquedWord(navController, critiqued)
        }
        item {
            CritiquedSection(frenzy, "Critique Frenzy",
                "No partners, no swaps, just feedback on your work.", "critiquedFrenzy",
                navController, {
                bottomSheet = AccountSheetContent.makeNewCritiqueFrenzy
            })
        }
        item {
            PosistivitySection(positiveCritiques, "Positivity Corner",
                "Just positive comments on your work.", "criquedPositives",
                navController, {
                    bottomSheet = AccountSheetContent.makeNewPositive
                })
        }
        item {
            CritiquedSection(queryCritiques, "Quick Query Critique",
                "Critiques on your query, posted to every users home page.", "criquedQueries",
                navController, {
                    bottomSheet = AccountSheetContent.makeNewQueryFrenzy
                })
        }
    }
}

enum class AccountSheetContent {
    none,
    makeNewPositive,
    makeNewCritiqueFrenzy,
    makeNewQueryFrenzy,
    makeNewProposals,
}