package hannah.bd.getitwrite.views.sprints

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.R

@Composable
fun SprintCTA(onStartSprint: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    )  {
        Text(
            text = "pick a custom duration".uppercase(),
            style = MaterialTheme.typography.labelLarge
        )
        Column {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.sprintbg),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Fit
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                        .background(Color.White, shape = RoundedCornerShape(15.dp))
                ) {
                    Text("Unleash your productivity.", style = MaterialTheme.typography.titleMedium,
                        fontFamily = FontFamily(Font(R.font.bellefairregularfont)),
                        modifier = Modifier.padding(16.dp))
                }
            }
            Button(
                onClick = onStartSprint,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5.dp))
            ) {
                Text("Start Sprint")
            }
        }
    }
}
