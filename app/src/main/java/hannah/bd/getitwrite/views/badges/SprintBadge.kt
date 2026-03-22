package hannah.bd.getitwrite.views.badges

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.modals.Badge

@Composable
fun SprintBadge(badge: Badge, width: Dp) {
    val GoldAchieve = Color(0xFFFFD700)
    val context = LocalContext.current
    val achieved = remember { getAchievement(context, badge.key) }

    Column(
        modifier = Modifier
            .width(width)
            .height(150.dp)
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = badge.getIcon(),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(40.dp)
                .background(
                    if (achieved) GoldAchieve else Color.Gray,
                    shape = CircleShape
                )
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = badge.getText(),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.bellefairregularfont))
        )
    }
}