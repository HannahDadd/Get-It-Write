package hannah.bd.getitwrite.views.positivityCorner

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.views.forum.RectangleTileButtonNoDate

@Composable
fun PositiveFeedback(onTap: () -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Positivity Corner",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Row {
            Image(
                painter = painterResource(id = R.drawable.positivebg),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(150.dp)
            )
            Column(modifier = Modifier.height(150.dp).padding(start = 8.dp)) {
                Text(
                    text = "Leave positive feedback on this work and build someone's confidence.",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.weight(1.0f))
                RectangleTileButtonNoDate(
                    title = "Critique",
                    backgroundColour = MaterialTheme.colorScheme.background,
                    textColour = MaterialTheme.colorScheme.onBackground,
                    padding = 16.dp,
                    onClick = onTap
                )
            }
        }
    }
}