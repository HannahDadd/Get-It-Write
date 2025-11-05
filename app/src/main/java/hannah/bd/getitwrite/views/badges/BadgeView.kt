package hannah.bd.getitwrite.views.badges

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hannah.bd.getitwrite.modals.Badge

@Composable
fun BadgeView(
    title: String,
    onTapText: String,
    shouldShowPopup: Boolean,
    context: Context
) {
    var showPopup by remember { mutableStateOf(false) }
    var badge by remember { mutableStateOf(loadBadge(context, title)) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(
                    color = if (shouldShowPopup)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    else
                        MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                )
                .combinedClickable(
                    onClick = {
                        if (shouldShowPopup) {
                            showPopup = true
                        } else {
                            val updated = badge.copy(score = badge.score + 1)
                            badge = updated
                            saveBadge(context, updated)
                        }
                    },
                    onDoubleClick = {
                        val reset = badge.copy(score = 0)
                        badge = reset
                        saveBadge(context, reset)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = badge.score.toString(),
                color = Color.White,
                fontSize = 24.sp,
                //fontFamily = FontFamily(Font(R.font.abrilfatface_regular))
            )
        }

        Text(text = badge.title, fontWeight = FontWeight.Bold)
    }

    if (showPopup) {
        AlertDialog(
            onDismissRequest = { showPopup = false },
            title = { Text(badge.title) },
            text = { Text(onTapText) },
            confirmButton = {
                TextButton(onClick = { showPopup = false }) {
                    Text("Close")
                }
            }
        )
    }
}

private fun loadBadge(context: Context, title: String): Badge {
    val prefs = context.getSharedPreferences("badges", Context.MODE_PRIVATE)
    val score = prefs.getInt(title, 0)
    return Badge(id = title.hashCode(), score = score, title = title)
}

private fun saveBadge(context: Context, badge: Badge) {
    val prefs = context.getSharedPreferences("badges", Context.MODE_PRIVATE)
    prefs.edit().putInt(badge.title, badge.score).apply()
}