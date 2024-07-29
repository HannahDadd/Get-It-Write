package hannah.bd.getitwrite.views.feed

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.views.components.ImageButtonWithText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFeed() {
    LazyRow {
        ImageButtonWithText(
            painter = painterResource(id = R.drawable.sci-fi),
            contentDescription = "Sample Image",
            buttonText = "Click Me",
            onClick = { /* Handle Click */ }
        )
    }
}