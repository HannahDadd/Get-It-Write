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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.getitwrite.Colours
import com.example.getitwrite.modals.Proposal
import com.example.getitwrite.views.components.TagCloud
//import com.google.accompanist.adaptive.TwoPane

@Composable
fun ProposalsFeed(proposalViewModel: ProposalsViewModel = viewModel()) {
    val proposals by proposalViewModel.proposalsFlow.collectAsState(initial = emptyList())
    val errorString = remember { mutableStateOf("") }
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
//        TwoPane (
//
//        )
        LazyColumn(Modifier.padding(innerPadding)) {
            items(proposals) { proposal ->
                ProposalView(proposal, Modifier.clickable {
                })
            }
        }
    }
}

@Composable
fun ProposalView(proposal: Proposal, modifier: Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier.padding(10.dp)) {
        Text(proposal.title, fontWeight = FontWeight.Bold)
        Text(proposal.blurb)
        Text(proposal.typeOfProject.joinToString(", "), fontWeight = FontWeight.Light)
        TagCloud(tags = proposal.genres, action = null)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding()) {
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

@Composable
fun ExpandedProposalView(proposal: Proposal) {
    Column {
        Text(proposal.title, fontSize = 40.sp)
        Text("by ${proposal.writerName}")
        Divider()
        Text("Author's notes", fontWeight = FontWeight.Bold)
        Text(proposal.authorNotes)
        Divider()
        Text(proposal.typeOfProject.joinToString(", "), fontWeight = FontWeight.Light)
        TagCloud(tags = proposal.genres, action = null)
        Text(text = "${proposal.wordCount} words", fontWeight = FontWeight.Bold)
        Text("Trigger warnings:", fontWeight = FontWeight.Bold)
        TagCloud(tags = proposal.triggerWarnings, action = null)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(containerColor = Colours.Dark_Readable, contentColor = Color.White)
        ) {
            Text("Send Author Message", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
        }
    }
}