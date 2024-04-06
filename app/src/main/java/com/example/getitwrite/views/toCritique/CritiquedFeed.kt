package com.example.getitwrite.views.toCritique

import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.getitwrite.modals.Critique
import com.example.getitwrite.views.components.FindPartnersText
import com.example.getitwrite.views.components.ProfileImage

@Composable
fun CritiquedFeed(critiques: List<Critique>, selectCritique: (String) -> Unit) {
    if (critiques.isEmpty()) {
        Column(Modifier.padding(10.dp)) {
            Text("None of your work has been critiqued.", fontWeight = FontWeight.Bold)
            FindPartnersText()
        }
    } else {
        LazyColumn(Modifier.padding(10.dp)) {
            items(critiques) { work ->
                CritiqueView(critique = work, selectCritique)
                Divider()
            }
        }
    }
}

@Composable
fun CritiqueView(critique: Critique, select: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp).clickable { select(critique.id) }) {
        ProfileImage(username = critique.critiquerName, profileColour = critique.critiquerProfileColour)
        Text(critique.overallFeedback, fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.fillMaxWidth().padding()) {
            Text(
                text = DateUtils.getRelativeTimeSpanString(
                    (critique.timestamp.seconds * 1000),
                    System.currentTimeMillis(),
                    DateUtils.DAY_IN_MILLIS
                ).toString(),
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = "${critique.comments.size} comments",
                fontWeight = FontWeight.Light
            )
        }
    }
}