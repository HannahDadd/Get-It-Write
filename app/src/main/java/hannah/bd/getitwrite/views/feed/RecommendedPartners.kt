package hannah.bd.getitwrite.views.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.theme.AppTypography
import hannah.bd.getitwrite.views.components.SquareTileButton

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecomendedCritiquers() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Recommended critique partners",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            RecommendedPartnerCard(
                title = "Hannah",
                reason = "Critiques weekly.",
                onClick = {}
            )
            RecommendedPartnerCard(
                title = "Hannah",
                reason = "Critques Sci Fi.",
                onClick = {}
            )
            RecommendedPartnerCard(
                title = "Hannah",
                reason = "Writes Sci Fi.",
                onClick = {}
            )
        }
    }
}

@Composable
fun RecommendedPartnerCard(
    title: String,
    reason: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .size(120.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
                .padding(8.dp)) {
            Icon(
                Icons.Default.Person,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = reason,
                style = AppTypography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}