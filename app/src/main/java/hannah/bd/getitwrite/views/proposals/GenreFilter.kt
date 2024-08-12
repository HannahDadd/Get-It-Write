package hannah.bd.getitwrite.views.proposals

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
        Triple(R.drawable.thriller, "Thriller", "Thriller"),
        Triple(R.drawable.shortstory, "Short Stories", "Short Story"))
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Search by genre",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Find critique partners writing for the same genre as you.",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            genres.forEach {
                ElevatedCardRect(
                    painter = painterResource(id = it.first),
                    buttonText = it.second,
                    width = 160.dp,
                    height = 90.dp,
                    onClick = { navController.navigate("genre/${it.third}") }
                )
            }
        }
    }
}

@Composable
fun ElevatedCardRect(
    painter: Painter,
    buttonText: String,
    height: Dp,
    width: Dp,
    onClick: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        modifier = Modifier
            .size(width = width, height = height)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painter,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(height)
        )
        Text(
            text = buttonText,
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}