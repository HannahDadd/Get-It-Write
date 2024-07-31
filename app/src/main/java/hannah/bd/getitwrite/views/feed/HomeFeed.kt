package hannah.bd.getitwrite.views.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.views.components.SquareTileButton

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
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "Welcome",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item {
                        SquareTileButton(
                            title = "To critique",
                            wordCount = "100 words",
                            backgroundColour = MaterialTheme.colorScheme.primary,
                            textColour = MaterialTheme.colorScheme.onPrimary,
                            icon = Icons.Default.Notifications,
                            onClick = {}
                        )
                    }
                }
            }
        }
        item {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "Your work, critiqued by your writing friends",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    item {
                        SquareTileButton(
                            title = "Get feedback on something!",
                            wordCount = "",
                            backgroundColour = MaterialTheme.colorScheme.tertiaryContainer,
                            textColour = MaterialTheme.colorScheme.onTertiaryContainer,
                            icon = Icons.Default.Send,
                            onClick = {}
                        )
                    }
                    item {
                        SquareTileButton(
                            title = "Title",
                            wordCount = "100 words",
                            backgroundColour = MaterialTheme.colorScheme.tertiary,
                            textColour = MaterialTheme.colorScheme.onTertiary,
                            icon = Icons.Default.Edit,
                            onClick = {}
                        )
                    }
                }
            }
        }
        item {
            Box(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .clickable(onClick = {  }),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.aibg),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Text(
                    text = "Get AI feedback on your writing, instantly",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.background(Color.White).align(Alignment.Center).width(200.dp)
                )
            }
        }
        item {
            DarkBackgroundCards("Quick Query Feedback")
        }

//        LazyRow {
//            items(genres) {(image, name, dest) ->
//
//                ImageButtonWithText(
//                    painter = painterResource(image),
//                    contentDescription = "",
//                    buttonText = name,
//                    onClick = { /* Handle Click */ }
//                )
//            }
//        }
//        LazyRow {
//            items(images) {(image, name, dest) ->
//
//                ImageButtonWithText(
//                    painter = painterResource(image),
//                    contentDescription = "",
//                    buttonText = name,
//                    onClick = { /* Handle Click */ }
//                )
//            }
//        }
    }
}

@Composable
fun DarkBackgroundCards(title: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSecondary
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SquareTileButton(
                    title = "To critique",
                    wordCount = "",
                    backgroundColour = MaterialTheme.colorScheme.secondaryContainer,
                    textColour = MaterialTheme.colorScheme.onSecondaryContainer,
                    icon = Icons.Default.Email,
                    onClick = {}
                )
            }
        }
    }
}

