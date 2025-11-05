package hannah.bd.getitwrite.views.games.editing

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@Composable
fun EditingGameCTA(action: () -> Unit) {
    Box(
        modifier = Modifier
            .height(140.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary)
            .clickable { action() }
    ) {
        DrawingPathsToneCTA(
            modifier = Modifier
                .matchParentSize()
                .background(MaterialTheme.colorScheme.primary)
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "Practice editing",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Text(
                "Practice your editing skills on these sentences.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

@Composable
fun DrawingPathsToneCTA(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(180f, 0f)
            cubicTo(100f, 75f, 110f, 80f, 150f, 140f)
            lineTo(0f, 140f)
            close()
        }
        drawPath(
            path = path,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}