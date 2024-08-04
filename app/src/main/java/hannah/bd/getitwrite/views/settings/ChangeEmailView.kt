package hannah.bd.getitwrite.views.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.Colours
import hannah.bd.getitwrite.views.components.ErrorText

@Composable
fun ChangeEmailView() {
    var errorString = remember { mutableStateOf<String?>(null) }
    val email = remember { mutableStateOf("") }
    Column {
        OutlinedTextField(
            value = email.value,
            maxLines = 1,
            onValueChange = { email.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Email") }
        )
        ErrorText(error = errorString)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
            },
            colors = ButtonDefaults.buttonColors(containerColor = Colours.Dark_Readable, contentColor = Color.White)
        ) {
            Text("Re-authenticate", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}