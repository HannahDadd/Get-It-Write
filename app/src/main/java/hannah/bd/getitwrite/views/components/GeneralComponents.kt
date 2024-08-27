package hannah.bd.getitwrite.views.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hannah.bd.getitwrite.GlobalVariables
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.theme.AppTypography
import hannah.bd.getitwrite.views.feed.RectangleTileButtonNoDate

@Composable
fun RoundedButton(modifier: Modifier, onClick: () -> Unit) {
    Box(modifier = modifier.padding(horizontal = 10.dp)) {
        Button(
            onClick = onClick,
            shape = CircleShape,
            modifier = modifier.size(40.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Favorite",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun ErrorText(error: String) {
    Text(error, color = MaterialTheme.colorScheme.error)
}

@Composable
fun ErrorText(error: MutableState<String?>) {
    error.value?.let {Text(it, color = MaterialTheme.colorScheme.error) }
}

@Composable
fun SquareTileButton(
    modifier: Modifier = Modifier,
    title: String,
    wordCount: String,
    backgroundColour: Color,
    textColour: Color,
    icon: ImageVector,
    size: Dp,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(8.dp))
            .background(color = backgroundColour)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            icon,
            contentDescription = "",
            tint = textColour
        )
        Text(
            text = title,
            color = textColour
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Text(
            text = wordCount,
            style = AppTypography.labelSmall,
            color = textColour
        )
    }
}

@Composable
fun Promo(
    title: String,
    buttonText: String,
    painter: Painter,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
        )
        Column(Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = title,
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    ).padding(4.dp),
            )
            Spacer(modifier = Modifier.weight(1.0f))
            RectangleTileButtonNoDate(
                title = buttonText,
                backgroundColour = MaterialTheme.colorScheme.background,
                textColour = MaterialTheme.colorScheme.onBackground,
                padding = 8.dp,
                onClick = onClick
            )
        }
    }
}

@Composable
fun HomePageTileButton(
    title: String,
    bubbleText: String,
    icon: ImageVector,
    isFirstItemInCarousel: Boolean,
    isLastItemInCarousel: Boolean = false,
    onClick: () -> Unit
) {
    val primary = MaterialTheme.colorScheme.primary
    Column(
        modifier = Modifier
            .padding(start = if (isFirstItemInCarousel) 8.dp else 0.dp)
            .padding(end = if (isLastItemInCarousel) 8.dp else 0.dp)
            .size(150.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.background)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            icon,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = title, maxLines = 2, overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Row {
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                modifier = Modifier
                    .background(
                        color = primary,
                        shape = RoundedCornerShape(8.dp)
                    ).padding(4.dp),
                text = bubbleText,
                style = AppTypography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun TitleAndSubText(
    title: String,
    subText: String,
    textColour: Color
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = textColour
        )
        Text(
            text = subText,
            style = MaterialTheme.typography.labelLarge,
            color = textColour
        )
    }
}

@Composable
fun QuestionSection(response: MutableState<String>, question: String) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(vertical = 10.dp)) {
        Text(question, fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = response.value,
            maxLines = 5,
            onValueChange = { response.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
    }
}

@Composable
fun ProfileImage(username: String, profileColour: Int) {
    if (username.isEmpty()) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .drawBehind {
                    drawCircle(
                        color = GlobalVariables.profileColours.get(profileColour),
                        radius = this.size.maxDimension
                    )
                },
            color = Color.White,
            text = " ",
        )
    } else {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .drawBehind {
                    drawCircle(
                        color = GlobalVariables.profileColours.get(profileColour),
                        radius = this.size.maxDimension
                    )
                },
            color = Color.White,
            text = username.first().toString(),
        )
    }
}

@Composable
fun FindPartnersText() {
    Text(text = "Select 'find partners' on the bottom nav to find new critique partners.")
}

@Composable
fun DetailHeader(
    title: String,
    navigateUp: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title, color = MaterialTheme.colorScheme.onSecondary) },
        backgroundColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                androidx.compose.material.Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "")
            }
        }
    )
}