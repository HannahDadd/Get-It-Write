package hannah.bd.getitwrite.views.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.theme.AppTypography
import hannah.bd.getitwrite.views.components.ImageButtonWithText

@Composable
fun HomeFeed() {
    val images = listOf(
        Triple(R.drawable.adults, "Adult", "crime"),
        Triple(R.drawable.childrens, "Childrens", "crime"),
        Triple(R.drawable.ya, "YA", "crime"),
        Triple(R.drawable.shortstory, "Short Story", "crime"))
    val genres = listOf(Triple(R.drawable.scifi, "Sci Fi", "scifi"),
        Triple(R.drawable.crime, "Crime", "crime"),
        Triple(R.drawable.dystopian, "Dystopian", "crime"),
        Triple(R.drawable.fantasy, "Fantasy", "crime"),
        Triple(R.drawable.histroical, "Historical", "crime"),
        Triple(R.drawable.magicalrealism, "Magical Realism", "crime"),
        Triple(R.drawable.memoir, "Memoir", "crime"),
        Triple(R.drawable.romance, "Romance", "crime"),
        Triple(R.drawable.thriller, "Thriller", "crime"))
    Column {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Text(
                text = "title",
                style = MaterialTheme.typography.titleLarge,
//                color = contentColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    SquareTileButton(
                        title = "To critique",
                        wordCount = "100 words",
                        onClick = {}
                    )
                }
            }
        }

        LazyRow {
            items(genres) {(image, name, dest) ->

                ImageButtonWithText(
                    painter = painterResource(image),
                    contentDescription = "",
                    buttonText = name,
                    onClick = { /* Handle Click */ }
                )
            }
        }
        LazyRow {
            items(images) {(image, name, dest) ->

                ImageButtonWithText(
                    painter = painterResource(image),
                    contentDescription = "",
                    buttonText = name,
                    onClick = { /* Handle Click */ }
                )
            }
        }
    }

}

@Composable
fun SquareTileButton(
    title: String,
    wordCount: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .size(150.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFA726), // Dark orange (start)
                        Color(0xFFF57C00)  // Darker orange (end)
                    )
                )
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(Icons.Filled.Notifications, contentDescription = "", tint = Color.White)
        Text(
            text = title,
            style = AppTypography.headlineMedium,
            color = Color.White,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Text(
            text = wordCount,
            style = AppTypography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 18.sp
        )
    }
}