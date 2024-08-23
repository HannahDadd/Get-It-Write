package hannah.bd.getitwrite.views.proposals

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
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
import androidx.navigation.NavHostController
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.views.components.TitleAndSubText

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FindPartnersByAudience(navController: NavHostController) {
    val images = listOf(
        Triple(R.drawable.adults, "Adult", "Adult"),
        Triple(R.drawable.ya, "YA", "Young Adult"),
        Triple(R.drawable.childrens, "Childrens", "crime"))
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleAndSubText(
            "Filter by target audience",
            "Find critique partners writing for the same target audience as you.",
            MaterialTheme.colorScheme.onSurface
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            images.forEach {
                ImageButtonWithText(
                    painter = painterResource(id = it.first),
                    buttonText = it.second,
                    size = 120.dp,
                    onClick = { navController.navigate("genre/${it.third}") }
                )
            }
        }
    }
}

@Composable
fun ImageButtonWithText(
    painter: Painter,
    buttonText: String,
    size: Dp,
    onClick: () -> Unit
) {
    Card {
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
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(Color.White)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}