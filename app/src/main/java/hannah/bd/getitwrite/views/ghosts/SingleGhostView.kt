package com.hannah.shelfify.views.ghosts

import android.R.attr.onClick
import android.media.Image
import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random


@Composable
fun SingleGhostView(
    ghost: Ghost,
    onGhostTapped: () -> Unit
) {

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthPx = with(density) {
        configuration.screenWidthDp.dp.toPx()
    }

    val screenHeightPx = with(density) {
        configuration.screenHeightDp.dp.toPx()
    }

    var position by remember {
        mutableStateOf(
            randomPoint(screenWidthPx, screenHeightPx)
        )
    }

    var isVisible by remember {
        mutableStateOf(false)
    }

    val animatedPosition by animateOffsetAsState(
        targetValue = position,
        animationSpec = tween(
            durationMillis = 10000,
            easing = LinearOutSlowInEasing
        ),
        label = "ghostMovement"
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = if (isVisible) 0.4f else 0f,
        animationSpec = tween(1000),
        label = "ghostAlpha"
    )

    LaunchedEffect(Unit) {
        Log.d("Ghost", "hello")

        while (true) {

            delay(Random.nextLong(5_000, 120_000))

            position = randomPoint(
                screenWidthPx,
                screenHeightPx
            )

            isVisible = true

            delay(Random.nextLong(10_000, 20_000))

            position = randomPoint(
                screenWidthPx,
                screenHeightPx
            )

            delay(Random.nextLong(8_000, 20_000))

            isVisible = false
        }
    }

    Image(
        painter = painterResource(id = ghost.imageRes),
        contentDescription = ghost.ghostName,
        modifier = Modifier
            .size(
                width = ghost.width,
                height = ghost.height
            )
            .clickable(enabled = isVisible) {
                onGhostTapped()
            }
            .offset {
                IntOffset(
                    animatedPosition.x.toInt(),
                    animatedPosition.y.toInt()
                )
            },
        alpha = animatedAlpha
    )
}

private fun randomPoint(
    screenWidth: Float,
    screenHeight: Float
): Offset {

    return Offset(
        x = Random.nextFloat() * (screenWidth - 120f) + 60f,
        y = Random.nextFloat() * (screenHeight - 120f) + 60f
    )
}