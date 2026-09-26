package hannah.bd.getitwrite.views.homepage

import android.R.attr.height
import android.R.attr.x
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.contentType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import hannah.bd.getitwrite.R
import kotlin.random.Random

@Composable
fun backGroundView() {
    val index = (0..3).random()

    Box(modifier = Modifier.fillMaxSize()) {
        when (index) {
        0 -> {
            rainView()
        }
        1 -> {
            Image(
                painter = painterResource(id = R.drawable.day),
                contentDescription = "day",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }
        2 -> {
            Image(
                painter = painterResource(id = R.drawable.dawn),
                contentDescription = "day",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }
        3 -> {
            Image(
                painter = painterResource(id = R.drawable.night),
                contentDescription = "day",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
    }
}