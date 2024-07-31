package hannah.bd.getitwrite.views.feed

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Timestamp
import hannah.bd.getitwrite.modals.Question
import hannah.bd.getitwrite.theme.AppTypography
import hannah.bd.getitwrite.views.components.SquareTileButton

@Composable
fun RectangleTileButton(
    title: String,
    seconds: Timestamp,
    backgroundColour: Color,
    textColour: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = backgroundColour)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = title,
            color = textColour
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            Text(
                text = DateUtils.getRelativeTimeSpanString(
                    (seconds.seconds * 1000),
                    System.currentTimeMillis(),
                    DateUtils.DAY_IN_MILLIS
                ).toString(),
                style = AppTypography.labelSmall,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.weight(1.0f))
        }
    }
}

@Composable
fun RectangleTileButtonNoDate(
    title: String,
    backgroundColour: Color,
    textColour: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = backgroundColour)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = AppTypography.titleSmall,
                color = textColour
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "",
                tint = textColour
            )
        }
    }
}

@Composable
fun JoinTheConvo(questions: List<Question>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp)
    ) {
        Text(
            text = "Join the conversation",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondary,
        )
        if(questions.isEmpty()) {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary,
            )
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                questions.subList(0, 3).forEach {
                    RectangleTileButton(
                        title = it.question,
                        seconds = it.timestamp,
                        backgroundColour = MaterialTheme.colorScheme.secondaryContainer,
                        textColour = MaterialTheme.colorScheme.onSecondaryContainer,
                        onClick = {}
                    )
                }
                RectangleTileButtonNoDate(
                    title = "View more",
                    backgroundColour = MaterialTheme.colorScheme.tertiaryContainer,
                    textColour = MaterialTheme.colorScheme.onTertiaryContainer,
                    onClick = {}
                )
            }
        }
    }
}