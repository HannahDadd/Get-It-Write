package hannah.bd.getitwrite.views.homepage

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

    if (wordsWritten < 200) {
        return R.drawable.library_1
    } else if (wordsWritten < 500) {
        return R.drawable.library_2
    } else if (wordsWritten < 1000) {
        return R.drawable.library_3
    } else if (wordsWritten < 5000) {
        return R.drawable.library_4
    } else if (wordsWritten < 10000) {
        return R.drawable.library_5
    } else if (wordsWritten < 12000) {
        return R.drawable.library_6
    } else if (wordsWritten < 15000) {
        return R.drawable.library_7
    } else if (wordsWritten < 20000) {
        return R.drawable.library_8
    } else if (wordsWritten < 24000) {
        return R.drawable.library_9
    } else if (wordsWritten < 26000) {
        return R.drawable.library_10
    } else if (wordsWritten < 28000) {
        return R.drawable.library_11
    } else if (wordsWritten < 30000) {
        return R.drawable.library_12
    } else if (wordsWritten < 35000) {
        return R.drawable.library_13
    } else if (wordsWritten < 37000) {
        return R.drawable.library_14
    } else if (wordsWritten < 40000) {
        return R.drawable.library_15
    } else if (wordsWritten < 50000) {
        return R.drawable.library_16
    } else if (wordsWritten < 55000) {
        return R.drawable.library_17
    } else if (wordsWritten < 60000) {
        return R.drawable.library_18
    } else if (wordsWritten < 70000) {
        return R.drawable.library_19
    } else if (wordsWritten < 80000) {
        return R.drawable.library_20
    } else {
        return R.drawable.library_21
    }
}
