package hannah.bd.getitwrite.views.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.views.components.SquareTileButton

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FindPartnersByGenre() {
    val genres = listOf(Triple(R.drawable.scifi, "Sci Fi", "scifi"),
        Triple(R.drawable.crime, "Crime", "crime"),
        Triple(R.drawable.dystopian, "Dystopian", "crime"),
        Triple(R.drawable.fantasy, "Fantasy", "crime"),
        Triple(R.drawable.histroical, "Historical", "crime"),
        Triple(R.drawable.magicalrealism, "Magical Realism", "crime"),
        Triple(R.drawable.memoir, "Memoir", "crime"),
        Triple(R.drawable.romance, "Romance", "crime"),
        Triple(R.drawable.thriller, "Thriller", "crime"))
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
                    onClick = {}
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FindPartnersByAudience() {
    val images = listOf(
        Triple(R.drawable.adults, "Adult", "crime"),
        Triple(R.drawable.ya, "YA", "crime"),
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
                    onClick = {}
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