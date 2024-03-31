package com.example.getitwrite.views.proposals

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.getitwrite.modals.Proposal
import com.example.getitwrite.views.components.TagCloud

@Composable
fun ProposalsFeed(proposals: List<Proposal>, selectProposal: (String) -> Unit) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add your own!") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = {
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(Modifier.padding(innerPadding)) {
            items(proposals) { proposal ->
                ProposalView(proposal, selectProposal)
            }
        }
    }
}

@Composable
fun ProposalView(proposal: Proposal, selectProposal: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(10.dp)
            .clickable { selectProposal(proposal.id) }) {
        Text(proposal.title, fontWeight = FontWeight.Bold)
        Text(proposal.blurb)
        Text(proposal.typeOfProject.joinToString(", "), fontWeight = FontWeight.Light)
        TagCloud(tags = proposal.genres, action = null)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            Text(
                text = DateUtils.getRelativeTimeSpanString(
                    (proposal.timestamp.seconds * 1000),
                    System.currentTimeMillis(),
                    DateUtils.DAY_IN_MILLIS
                ).toString(),
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = "${proposal.wordCount} words",
                fontWeight = FontWeight.Light
            )
        }
        Divider()
    }
}