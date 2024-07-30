package hannah.bd.getitwrite.views.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.views.components.ImageButtonWithText

@Composable
fun HomeFeed() {
    val images = listOf(
        Triple(R.drawable.adults, "Adult", "crime"),
        Triple(R.drawable.childrens, "Childrens", "crime"),
        Triple(R.drawable.ya, "YA", "crime"),
        Triple(R.drawable.shortstory, "Short Story", "crime"))
    val genres = listOf(Triple(R.drawable.scifi, "Sci Fi", "scifi"),
        Triple(R.drawable.crime, "Crime", "crime"),
        Triple(R.drawable.dystopian, "Dystopian", "crime"),
        Triple(R.drawable.fantasy, "Fantasy", "crime"),
        Triple(R.drawable.histroical, "Historical", "crime"),
        Triple(R.drawable.magicalrealism, "Magical Realism", "crime"),
        Triple(R.drawable.memoir, "Memoir", "crime"),
        Triple(R.drawable.romance, "Romance", "crime"),
        Triple(R.drawable.thriller, "Thriller", "crime"))
    Column {

        LazyRow {
            items(genres) {(image, name, dest) ->

                ImageButtonWithText(
                    painter = painterResource(image),
                    contentDescription = "",
                    buttonText = name,
                    onClick = { /* Handle Click */ }
                )
            }
        }
        LazyRow {
            items(images) {(image, name, dest) ->

                ImageButtonWithText(
                    painter = painterResource(image),
                    contentDescription = "",
                    buttonText = name,
                    onClick = { /* Handle Click */ }
                )
            }
        }
    }

}