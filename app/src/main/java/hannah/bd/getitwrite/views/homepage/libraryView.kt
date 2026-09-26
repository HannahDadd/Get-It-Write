package hannah.bd.getitwrite.views.homepage

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import hannah.bd.getitwrite.R

@Composable
fun LibraryView(
    wordsWritten: Int
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(
                id = getLibraryImage(wordsWritten)
            ),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }
}

private fun getLibraryImage(wordsWritten: Int): Int {

    val libraryImages = listOf(
        R.drawable.library_1,
        R.drawable.library_2,
        R.drawable.library_3,
        R.drawable.library_4,
        R.drawable.library_5,
        R.drawable.library_6,
        R.drawable.library_7,
        R.drawable.library_8,
        R.drawable.library_9,
        R.drawable.library_10,
        R.drawable.library_11,
        R.drawable.library_12,
        R.drawable.library_13,
        R.drawable.library_14,
        R.drawable.library_15,
        R.drawable.library_16,
        R.drawable.library_17,
        R.drawable.library_18,
        R.drawable.library_19,
        R.drawable.library_20,
        R.drawable.library_21
    )

    val stage = ((wordsWritten / 5000) + 1)
        .coerceIn(1, 21)

    return libraryImages[stage - 1]
}
