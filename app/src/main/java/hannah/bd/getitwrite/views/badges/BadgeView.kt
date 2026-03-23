package hannah.bd.getitwrite.views.badges

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.modals.Badge

@Composable
fun BadgePromo(badge: Badge) {
    val GoldAchieve = Color(0xFFFFD700)
    val context = LocalContext.current
    val achieved = remember { getAchievement(context, badge.key) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = if (achieved) badge.getIcon() else Icons.Default.Lock,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(40.dp)
                .background(
                    if (achieved) GoldAchieve else MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .padding(8.dp)
        )

        Text(
            text = badge.getText(),
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.bellefairregularfont)),
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = if (achieved) Icons.Default.CheckCircle else Icons.Default.Lock,
            contentDescription = null,
            tint = if (achieved) GoldAchieve else Color.Gray
        )
    }
}

fun getAchievement(context: Context, key: String): Boolean {
    val prefs = context.getSharedPreferences("badges", Context.MODE_PRIVATE)
    return prefs.getBoolean(key, false)
}

fun setAchievement(context: Context, key: String, value: Boolean) {
    val prefs = context.getSharedPreferences("badges", Context.MODE_PRIVATE)
    prefs.edit().putBoolean(key, value).apply()
}