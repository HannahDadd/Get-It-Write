package hannah.bd.getitwrite.views.forum

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hannah.bd.getitwrite.modals.Question
import hannah.bd.getitwrite.theme.AppTypography
import hannah.bd.getitwrite.views.components.TitleAndSubText

@Composable
fun RectangleTileButton(
    title: String,
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
    }
}

@Composable
fun JoinTheConvo(navController: NavController, questions: MutableState<List<Question>?>) {
    if (questions.value?.isNotEmpty() == true) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TitleAndSubText(
                "Join the conversation",
                "",
                MaterialTheme.colorScheme.onSurface
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                questions.value!!.subList(0, 3).forEachIndexed { index, it ->
                    RectangleTileButton(
                        title = it.question,
                        backgroundColour = MaterialTheme.colorScheme.background,
                        textColour = MaterialTheme.colorScheme.onBackground,
                        onClick = { navController.navigate("question/$index") }
                    )
                }
                RectangleTileButtonNoDate(
                    title = "View more",
                    backgroundColour = MaterialTheme.colorScheme.background,
                    textColour = MaterialTheme.colorScheme.onBackground,
                    padding = 16.dp,
                    onClick = { navController.navigate("questionFeed") }
                )
            }
        }
    } else {
        Text("Loading...", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun RectangleTileButtonNoDate(
    title: String,
    backgroundColour: Color,
    textColour: Color,
    padding: Dp,
    icon: ImageVector = Icons.Default.ArrowForward,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = backgroundColour)
            .clickable(onClick = onClick)
            .padding(padding),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = AppTypography.bodyLarge,
                color = textColour
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Icon(
                icon,
                contentDescription = "",
                tint = textColour
            )
        }
    }
}