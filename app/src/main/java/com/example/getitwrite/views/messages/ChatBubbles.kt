package com.example.getitwrite.views.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.getitwrite.Colours

@Composable
fun SingleOwnMessage(text: String) {
    Row(Modifier.height(IntrinsicSize.Max)) {
        Spacer(modifier = Modifier.weight(1.0f))
        Column(
            modifier = Modifier.background(
                color = Colours.paleBlue,
                shape = RoundedCornerShape(4.dp, 4.dp, 0.dp, 4.dp)
            )
                .padding(10.dp)
        ) {
            Text(text)
        }
        Column(
            modifier = Modifier
                .background(
                    color = Colours.paleBlue,
                    shape = OwnTriangleEdgeShape(30)
                )
                .width(8.dp)
                .fillMaxHeight()
        ) {
        }
    }
}


@Composable
fun SingleOtherMessage(text: String) {
    Row(Modifier.height(IntrinsicSize.Max)) {
        Column(
            modifier = Modifier
                .background(
                    color = Colours.bold,
                    shape = OtherTriangleEdgeShape(30)
                )
                .width(8.dp)
                .fillMaxHeight()
        ) {}
        Column(
            modifier = Modifier.background(
                color = Colours.bold,
                shape = RoundedCornerShape(4.dp, 4.dp, 0.dp, 4.dp)
            )
                .padding(10.dp)
        ) {
            Text(text)
        }
        Spacer(modifier = Modifier.weight(1.0f))
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