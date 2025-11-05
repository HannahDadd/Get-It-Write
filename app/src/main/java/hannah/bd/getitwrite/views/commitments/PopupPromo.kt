package hannah.bd.getitwrite.views.commitments

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@Composable
fun PopupPromo(
    title: String,
    subtitle: String,
    action: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { action() }
            .background(MaterialTheme.colorScheme.primary)
            .shadow(5.dp)
    ) {
        DrawingPathsPopupPromo(modifier = Modifier.matchParentSize())

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.titleMedium)
            Text(subtitle, color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun DrawingPathsPopupPromo(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(180f, 0f)
            cubicTo(100f, 75f, 110f, 80f, 150f, 140f)
            lineTo(0f, 100f)
            close()
        }
        drawPath(path = path, color = Color("8C5637".hexToInt()))
    }
}
