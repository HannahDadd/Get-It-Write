package hannah.bd.getitwrite.views.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeadlineAndSubtitle(
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            fontSize = 34.sp,
            //fontFamily = FontFamily(Font(R.font.abrilfatface_regular))
        )
        Text(
            text = subtitle,
            //style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.fillMaxWidth())
    }
}
