package hannah.bd.getitwrite.views.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.views.components.SquareTileButton

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

@Composable
fun AIPromo() {
    Box(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
            .clickable(onClick = { }),
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
            text = "Instantly evaluate your writing with AI.",
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color.White)
                .align(Alignment.Center)
                .width(200.dp)
        )
    }
}

@Composable
fun CritiquedWord() {
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

@Composable
fun WorkToCritique() {
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

@Composable
fun RecomendedCritiquers() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Recommended critique partners",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                SquareTileButton(
                    title = "Hannah",
                    wordCount = "Why? Frequently critiques works quickly.",
                    backgroundColour = MaterialTheme.colorScheme.tertiary,
                    textColour = MaterialTheme.colorScheme.onTertiary,
                    icon = Icons.Default.AccountCircle,
                    onClick = {}
                )
            }
            item {
                SquareTileButton(
                    title = "Hannah",
                    wordCount = "Why? Frequently critiques sci fi, like you.",
                    backgroundColour = MaterialTheme.colorScheme.tertiary,
                    textColour = MaterialTheme.colorScheme.onTertiary,
                    icon = Icons.Default.AccountCircle,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun CheckYourMessages() {
    Column {
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.background(MaterialTheme.colorScheme.secondary)
                .padding(16.dp)
        ) {
            Text(
                text = "Messages",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
        Divider()
    }
}