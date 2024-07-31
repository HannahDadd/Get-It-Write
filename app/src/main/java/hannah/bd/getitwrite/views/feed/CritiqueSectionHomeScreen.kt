package hannah.bd.getitwrite.views.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
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
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.views.components.SquareTileButton

@Composable
fun PositiveFeedback() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Positive feedback only!",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                ImageButtonWithTextCentred(
                    painter = painterResource(id = R.drawable.positivebg),
                    buttonText = "Title",
                    size = 150.dp,
                    onClick = {})
            }
            item {
                SquareTileButton(
                    title = "Add yours",
                    wordCount = "",
                    backgroundColour = MaterialTheme.colorScheme.tertiaryContainer,
                    textColour = MaterialTheme.colorScheme.onTertiaryContainer,
                    icon = Icons.Default.Add,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun QuickQueryCritique() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp)
    ) {
        Text(
            text = "Quick query critique",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondary
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SquareTileButton(
                    title = "To critique",
                    wordCount = "",
                    backgroundColour = MaterialTheme.colorScheme.primaryContainer,
                    textColour = MaterialTheme.colorScheme.onPrimaryContainer,
                    icon = Icons.Default.Email,
                    onClick = {}
                )
            }
            item {
                SquareTileButton(
                    title = "Add your own.",
                    wordCount = "",
                    backgroundColour = MaterialTheme.colorScheme.secondaryContainer,
                    textColour = MaterialTheme.colorScheme.onSecondaryContainer,
                    icon = Icons.Default.Add,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun FreeForAll() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp)
    ) {
        Text(
            text = "No partners, no swaps, just feedback.",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondary
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                SquareTileButton(
                    title = "To critique",
                    wordCount = "",
                    backgroundColour = MaterialTheme.colorScheme.primaryContainer,
                    textColour = MaterialTheme.colorScheme.onPrimaryContainer,
                    icon = Icons.Default.Email,
                    onClick = {}
                )
            }
            item {
                SquareTileButton(
                    title = "Add your own.",
                    wordCount = "",
                    backgroundColour = MaterialTheme.colorScheme.secondaryContainer,
                    textColour = MaterialTheme.colorScheme.onSecondaryContainer,
                    icon = Icons.Default.Add,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun ImageButtonWithTextCentred(
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
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color.White)
                .align(Alignment.Center)
        )
    }
}