package hannah.bd.getitwrite.views.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.theme.AppTypography
import hannah.bd.getitwrite.views.components.SquareTileButton
import hannah.bd.getitwrite.views.components.TitleAndSubText
import hannah.bd.getitwrite.views.positivityCorner.PositivityPopUp
import hannah.bd.getitwrite.views.profile.ProfilePopUp
import hannah.bd.getitwrite.views.profile.ProfileView
import hannah.bd.getitwrite.views.proposals.sendAuthorMessage
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RecommendedCritiquers(recs: MutableState<List<User>?>, navController: NavController) {
    var bottomSheet by remember { mutableStateOf<User?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    bottomSheet?.let {
        ModalBottomSheet(
            onDismissRequest = { bottomSheet = null },
            sheetState = sheetState
        ) {
            ProfilePopUp(user = it, navController = navController, {
                bottomSheet = null
            })
        }
    }
    recs.value?.let { recs ->
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TitleAndSubText(
                "Recommended critique partners",
                "Specially picked out for you.",
                MaterialTheme.colorScheme.onSurface
            )
            LazyVerticalGrid(
                columns = GridCells.Adaptive(128.dp),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 16.dp,
                    end = 12.dp,
                    bottom = 16.dp
                ),
                content = {
                    items(recs.size) { index ->
                        Card(
                            //backgroundColor = Color.Red,
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(),
                            //elevation = 8.dp,
                        ) {
                            Text(
                                text = recs[index].displayName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            )
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(3)
//            ) {
////                    recs.forEach {
//                items(recs) {
//                    RecommendedPartnerCard(
//                        title = it.displayName,
//                        onClick = { bottomSheet = it }
//                    )
//                }
//            }
        }
    }
}

@Composable
fun RecommendedPartnerCard(
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
        )
    }
}

fun getRecs(user: User,
                 onSuccess: (List<User>) -> Unit,
                 onError: (Exception) -> Unit) {
    Firebase.firestore.collection("users")
        //.orderBy("lastCritique", Query.Direction.ASCENDING)
        .limit(10).get()
        .addOnSuccessListener { documents ->
            if (documents != null) {
                val items = documents.map { doc ->
                    User(doc.id, doc.data)
                }
                user.frequencey?.let {freq ->
                    val recs = items.filter { it.frequencey != null }
                        .sortedBy { kotlin.math.abs(it.frequencey!! - freq) }
                    if (recs.size < 4) {
                        onSuccess(items.subList(0, 3))
                    } else {
                        onSuccess(recs.subList(0, 3))
                    }
                } ?: {
                    onSuccess(items.subList(0, 3))
                }
            } else {
                onError(Exception("Data not found"))
            }
        }
        .addOnFailureListener { exception ->
            onError(exception)
        }
}