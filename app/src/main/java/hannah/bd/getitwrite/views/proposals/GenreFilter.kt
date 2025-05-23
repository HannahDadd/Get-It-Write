package hannah.bd.getitwrite.views.proposals

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import hannah.bd.getitwrite.views.components.TitleAndSubText

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FindPartnersByGenre(navController: NavHostController) {
    val genres = listOf(Triple("", "Sci Fi", "Science Fiction"),
        Triple("", "Mystery", "Mystery"),
        Triple("", "Dystopian", "Dystopian"),
        Triple("", "Fantasy", "Fantasy"),
        Triple("", "Historical", "Historical"),
        Triple("", "Magical Realism", "Magical Realism"),
        Triple("", "Memoir", "Memoir"),
        Triple("", "Romance", "Romance"),
        Triple("", "Thriller", "Thriller"),
        Triple("", "Short Stories", "Short Stories"))
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleAndSubText(
            "Filter by genre",
            "Find critique partners writing for the same genre as you.",
            MaterialTheme.colorScheme.onSurface
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            genres.forEach {
                ElevatedCardRect(
                    buttonText = it.second,
                    width = 180.dp,
                    height = 80.dp,
                    onClick = { navController.navigate("genre/${it.third}") }
                )
            }
        }
    }
}

@Composable
fun ElevatedCardRect(
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
        Row(modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
            Text(
                text = buttonText,
                modifier = Modifier
                    .padding(16.dp),
            )
        }
    }
}