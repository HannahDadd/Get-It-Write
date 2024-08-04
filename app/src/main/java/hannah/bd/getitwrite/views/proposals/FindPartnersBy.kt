package hannah.bd.getitwrite.views.proposals

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import hannah.bd.getitwrite.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FindPartnersByGenre(navController: NavHostController) {
    val genres = listOf(Triple(R.drawable.scifi, "Sci Fi", "Science Fiction"),
        Triple(R.drawable.crime, "Crime", "Mystery"),
        Triple(R.drawable.dystopian, "Dystopian", "Dystopian"),
        Triple(R.drawable.fantasy, "Fantasy", "Fantasy"),
        Triple(R.drawable.histroical, "Historical", "Historical"),
        Triple(R.drawable.magicalrealism, "Magical Realism", "Magical Realism"),
        Triple(R.drawable.memoir, "Memoir", "Memoir"),
        Triple(R.drawable.romance, "Romance", "Romance"),
        Triple(R.drawable.thriller, "Thriller", "Thriller"))
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Find critique partners by genre",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            genres.forEach {
                ImageButtonWithText(
                    painter = painterResource(id = it.first),
                    buttonText = it.second,
                    size = 120.dp,
                    onClick = { navController.navigate("genre/${it.third}") }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FindPartnersByAudience(navController: NavHostController) {
    val images = listOf(
        Triple(R.drawable.adults, "Adult", "Adult"),
        Triple(R.drawable.ya, "YA", "Young Adult"),
        Triple(R.drawable.childrens, "Childrens", "crime"))
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Find critique partners by audience",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            images.forEach {
                ImageButtonWithText(
                    painter = painterResource(id = it.first),
                    buttonText = it.second,
                    size = 120.dp,
                    onClick = { navController.navigate("genre/${it.third}") }
                )
            }
        }
    }
}

@Composable
fun ImageButtonWithText(
    painter: Painter,
    buttonText: String,
    size: Dp,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(size)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painter,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(size)
        )
        Text(
            text = buttonText,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color.White)
                .align(Alignment.BottomCenter)
        )
    }
}