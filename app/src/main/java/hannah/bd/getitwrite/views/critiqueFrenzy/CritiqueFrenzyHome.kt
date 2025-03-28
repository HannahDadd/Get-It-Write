package hannah.bd.getitwrite.views.critiqueFrenzy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.modals.Question
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.RequestPositivity
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.DetailHeader
import hannah.bd.getitwrite.views.components.ErrorText
import hannah.bd.getitwrite.views.components.HomePageTileButton
import hannah.bd.getitwrite.views.components.SquareTileButton
import hannah.bd.getitwrite.views.components.TitleAndSubText
import kotlinx.coroutines.tasks.await

@Composable
fun FreeForAll(requests: MutableState<List<RequestCritique>?>, navController: NavController) {
    if (requests.value?.isNotEmpty() == true) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            TitleAndSubText(
                "Critique Frenzy",
                "No partners, no swaps, just feedback.",
                MaterialTheme.colorScheme.onSurface
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(requests.value!!.subList(0, 5)) {index, item ->
                    HomePageTileButton(
                        title = item.genres.joinToString(),
                        bubbleText = "${item.text.length} words",
                        icon = Icons.Default.Edit,
                        isFirstItemInCarousel = index == 0,
                        onClick = {navController.navigate("frenzy/$index")})
                }
                item {
                    SquareTileButton(
                        modifier = Modifier.padding(end = 8.dp),
                        title = "View more.",
                        wordCount = "",
                        backgroundColour = MaterialTheme.colorScheme.background,
                        textColour = MaterialTheme.colorScheme.onBackground,
                        icon = Icons.Default.ArrowForward,
                        size = 150.dp,
                        onClick = { navController.navigate("frenzyFeed") }
                    )
                }
            }
        }
    } else {
        Text("Loading...", modifier = Modifier.padding(16.dp))
    }
}