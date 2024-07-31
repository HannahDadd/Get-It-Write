package hannah.bd.getitwrite.views.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hannah.bd.getitwrite.GlobalVariables
import hannah.bd.getitwrite.theme.AppTypography

@Composable
fun RoundedButton(modifier: Modifier, onClick: () -> Unit) {
    Box(modifier = modifier.padding(horizontal = 10.dp)) {
        Button(
            onClick = onClick,
            shape = CircleShape,
            modifier = modifier.size(40.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            // Inner content including an icon and a text label
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Favorite",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun ErrorText(error: MutableState<String>) {
    Text(error.value, color = Color.Red)
}

@Composable
fun SquareTileButton(
    title: String,
    wordCount: String,
    backgroundColour: Color,
    textColour: Color,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .size(150.dp)
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
            style = AppTypography.titleSmall,
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
        title = { Text(text = title) },
        backgroundColor = Color.White,
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                androidx.compose.material.Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "")
            }
        }
    )
}