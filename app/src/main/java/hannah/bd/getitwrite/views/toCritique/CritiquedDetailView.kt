package hannah.bd.getitwrite.views.toCritique

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hannah.bd.getitwrite.modals.Critique
import hannah.bd.getitwrite.views.components.DetailHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CritiquedDetailedView(critique: Critique, navigateUp: () -> Unit) {
    val paragraphs = critique.text.split("\n")
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var bottomSheetText by remember { mutableStateOf(Triple("", 1, "")) }
    val comments = critique.comments.map {(k,v)-> v to k}.toMap<Long, String>()
    Column {
        DetailHeader(title = critique.projectTitle, navigateUp = navigateUp)
        Column(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (bottomSheetText.first != "") {
                ModalBottomSheet(
                    onDismissRequest = {
                        bottomSheetText = Triple("", 1, "")
                    },
                    sheetState = sheetState
                ) {
                    CritiquedSheet(bottomSheetText)
                }
            }
            paragraphs.forEachIndexed { index, element ->
                if (critique.comments.containsValue(index.toLong())) {
                    Text(element, style = TextStyle(background = MaterialTheme.colorScheme.primary,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        letterSpacing = 0.5.sp), modifier = Modifier.clickable { bottomSheetText = Triple(element, index, comments.get(index.toLong()) ?: "")})
                } else {
                    Text(element)
                }
            }
            Divider()
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding()) {
                Spacer(modifier = Modifier.weight(1.0f))
                Text(
                    text = "Comments ${critique.comments.size}",
                    fontWeight = FontWeight.Light
                )
            }
            Text(text = "Overall feedback:", fontWeight = FontWeight.Bold)
            Text(text = critique.overallFeedback)
        }
    }
}

@Composable
fun CritiquedSheet(pair: Triple<String, Int, String>) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "Paragraph:", fontWeight = FontWeight.Bold)
        Text(text = pair.first)
        Text(text = "Comment:", fontWeight = FontWeight.Bold)
        Text(text = pair.third)
    }
}