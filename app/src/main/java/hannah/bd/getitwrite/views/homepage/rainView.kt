package hannah.bd.getitwrite.views.homepage

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.R
import java.util.UUID
import kotlin.random.Random

data class RainDrop(
    val id: String = UUID.randomUUID().toString(),
    val xPosition: Float,
    val delay: Int,
    val duration: Int,
    val size: Float
)

@Composable
fun rainView() {

    val accessibilityManager =
        LocalAccessibilityManager.current

    val reduceMotion =
        accessibilityManager?.calculateRecommendedTimeoutMillis(
            0,
            containsIcons = false,
            containsText = false,
            containsControls = false
        )?.toInt() != 0

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {

        val screenWidth = constraints.maxWidth.toFloat()
        val screenHeight = constraints.maxHeight.toFloat()

        val drops = remember {

            List(100) {

                RainDrop(
                    xPosition = Random.nextFloat() * screenWidth,
                    delay = Random.nextInt(0, 2000),
                    duration = Random.nextInt(1000, 2000),
                    size = Random.nextFloat() * 3f + 5f
                )
            }
        }

        Image(
            painter = painterResource(R.drawable.rain),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        if (!reduceMotion) {

            drops.forEach { drop ->

                RainDropView(
                    drop = drop,
                    screenHeight = screenHeight
                )
            }
        }
    }
}

@Composable
private fun RainDropView(
    drop: RainDrop,
    screenHeight: Float
) {

    val transition = rememberInfiniteTransition(
        label = "rain"
    )

    val y by transition.animateFloat(
        initialValue = -10f,
        targetValue = screenHeight,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = drop.duration,
                delayMillis = drop.delay,
                easing = LinearEasing
            )
        ),
        label = "dropPosition"
    )

    val alpha by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = drop.duration,
                delayMillis = drop.delay,
                easing = LinearEasing
            )
        ),
        label = "dropAlpha"
    )

    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    drop.xPosition.toInt(),
                    y.toInt()
                )
            }
            .size(drop.size.dp)
            .background(
                Color.Gray.copy(alpha = 0.7f * alpha),
                CircleShape
            )
    )
}