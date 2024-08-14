package hannah.bd.getitwrite.views.positivityCorner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.modals.ContentToReportType
import hannah.bd.getitwrite.modals.RequestPositivity
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.theme.AppTypography
import hannah.bd.getitwrite.views.components.CheckInput
import hannah.bd.getitwrite.views.components.DetailHeader

@Composable
fun PositivityCritique(piece: RequestPositivity, navigateUp: () -> Unit) {
    Column {
        DetailHeader(title = "Positivity Piece", navigateUp = navigateUp)
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(text = piece.text)
            }
            item {
                piece.comments.forEach {
                    Divider()
                    Text(it.key, Modifier.padding(10.dp), style = AppTypography.titleMedium)
                    Text(
                        it.value,
                        Modifier.padding(10.dp),
                        style = AppTypography.bodyMedium
                    )
                }
            }
        }
    }
}