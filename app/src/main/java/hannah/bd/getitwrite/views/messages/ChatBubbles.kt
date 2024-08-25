package hannah.bd.getitwrite.views.messages

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.modals.ContentToReportType
import hannah.bd.getitwrite.modals.Message
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.ReportAndBlockUser
import com.google.firebase.Timestamp

@Composable
fun SingleOwnMessage(text: String, timestamp: Timestamp) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Row(Modifier.height(IntrinsicSize.Max)) {
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = DateUtils.getRelativeTimeSpanString(
                    (timestamp.seconds * 1000),
                    System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.MINUTE_IN_MILLIS.toInt()
                ).toString(),
                fontWeight = FontWeight.Light
            )
        }
        Row(
            Modifier
                .height(IntrinsicSize.Max)
                .padding(vertical = 5.dp)) {
            Spacer(modifier = Modifier.weight(1.0f))
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(4.dp, 4.dp, 0.dp, 4.dp)
                    )
                    .padding(10.dp)
            ) {
                Text(text, color = MaterialTheme.colorScheme.onSecondary)
            }
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = OwnTriangleEdgeShape(30)
                    )
                    .width(8.dp)
                    .fillMaxHeight()
            ) {
            }
        }
    }
}

@Composable
fun SingleOtherMessage(user: User, message: Message, chatId: String) {
    var showReportButton = remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .padding(vertical = 10.dp)
        .clickable { showReportButton.value = !showReportButton.value }
    ) {
        Text(
            text = DateUtils.getRelativeTimeSpanString(
                (message.created.seconds * 1000),
                System.currentTimeMillis(),
                DateUtils.DAY_IN_MILLIS
            ).toString(),
            fontWeight = FontWeight.Light
        )
        Row(
            Modifier
                .height(IntrinsicSize.Max)
                .padding(vertical = 5.dp)) {
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = OtherTriangleEdgeShape(30)
                    )
                    .width(8.dp)
                    .fillMaxHeight()
            ) {}
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(0.dp, 4.dp, 4.dp, 4.dp)
                    )
                    .padding(10.dp)
            ) {
                Text(message.content, color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
            Spacer(modifier = Modifier.weight(1.0f))
        }
        if (showReportButton.value) {
            ReportAndBlockUser(
                userToBlock = message.senderId,
                user = user,
                contentToReport = message,
                contentToReportType = ContentToReportType.OTHER,
                questionId = null,
                chatId = chatId
            )
        }
    }
}

class OwnTriangleEdgeShape(val offset: Int) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            moveTo(x = 0f, y = size.height - offset)
            lineTo(x = 0f, y = size.height)
            lineTo(x = 0f + offset, y = size.height)
        }
        return Outline.Generic(path = trianglePath)
    }
}

class OtherTriangleEdgeShape(val offset: Int) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            moveTo(x = 0f, y = size.height)
            lineTo(x = 0f + offset, y = size.height)
            lineTo(x = 0f + offset, y = size.height - offset)
        }
        return Outline.Generic(path = trianglePath)
    }
}