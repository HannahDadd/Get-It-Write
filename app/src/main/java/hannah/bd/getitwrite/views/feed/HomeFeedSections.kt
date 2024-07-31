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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
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
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.views.components.SquareTileButton

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
            text = "Use our AI to get instant feedback on your writing.",
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
                    title = "Send work to your critique partners.",
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
fun WorkToCritique(username: String, toCritiques: List<RequestCritique>) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Welcome " + username,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        if (!toCritiques.isEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(toCritiques) {
                    SquareTileButton(
                        title = it.title,
                        wordCount = it.text.trim().split("\\s+".toRegex()).size.toString() + " words",
                        backgroundColour = MaterialTheme.colorScheme.primary,
                        textColour = MaterialTheme.colorScheme.onPrimary,
                        icon = Icons.Default.Notifications,
                        onClick = {}
                    )
                }
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
                    wordCount = "Frequently critiques works quickly.",
                    backgroundColour = MaterialTheme.colorScheme.tertiary,
                    textColour = MaterialTheme.colorScheme.onTertiary,
                    icon = Icons.Default.AccountCircle,
                    onClick = {}
                )
            }
            item {
                SquareTileButton(
                    title = "Hannah",
                    wordCount = "Frequently critiques sci fi, like you.",
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
