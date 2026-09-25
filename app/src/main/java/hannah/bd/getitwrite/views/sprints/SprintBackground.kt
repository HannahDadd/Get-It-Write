package hannah.bd.shelfify.views.sprints

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import hannah.bd.getitwrite.R
import hannah.bd.shelfify.views.homepage.rainView

@Composable
fun SprintBackGroundView() {
    val index = (1..3).random()

    Box(modifier = Modifier.fillMaxSize()) {
        when (index) {
            1 -> {
                Image(
                    painter = painterResource(id = R.drawable.sprint_bg_1),
                    contentDescription = "day",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            2 -> {
                Image(
                    painter = painterResource(id = R.drawable.sprint_bg_2),
                    contentDescription = "day",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            3 -> {
                Image(
                    painter = painterResource(id = R.drawable.sprint_bg_3),
                    contentDescription = "day",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}