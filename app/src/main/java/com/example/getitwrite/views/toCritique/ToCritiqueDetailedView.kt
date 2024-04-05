package com.example.getitwrite.views.toCritique

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getitwrite.Colours
import com.example.getitwrite.modals.RequestCritique
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.DetailHeader
import com.example.getitwrite.views.components.TagCloud
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.UUID

@Composable
fun ToCritiqueDetailedView(toCritiques: List<RequestCritique>, id: String, navigateUp: () -> Unit) {
    val toCritique = toCritiques.filter { it.id == id }.get(0)
    Column {
        DetailHeader(title = toCritique.workTitle, navigateUp = navigateUp)
        Column(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(toCritique.title, fontWeight = FontWeight.Bold)
            Text("by ${toCritique.writerName}")
            Divider()
            Text("Blurb", fontWeight = FontWeight.Bold)
            Text(toCritique.blurb)
            Divider()
            Text(toCritique.text)
            Divider()
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colours.Dark_Readable,
                    contentColor = Color.White
                )
            ) {
                Text("Submit Critique", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
            }
        }
    }
}