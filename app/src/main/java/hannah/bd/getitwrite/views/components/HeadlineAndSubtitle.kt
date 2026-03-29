package hannah.bd.getitwrite.views.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import hannah.bd.getitwrite.R

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
            fontFamily = FontFamily(Font(R.font.abrilfatfaceregular)),
        )
        Text(
            text = subtitle,
            textAlign = TextAlign.Start,
            fontFamily = FontFamily(Font(R.font.bellefairregularfont)),
        )
        Spacer(modifier = Modifier.fillMaxWidth())
    }
}
