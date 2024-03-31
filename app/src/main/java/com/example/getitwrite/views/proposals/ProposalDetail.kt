package com.example.getitwrite.views.proposals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.getitwrite.Colours
import com.example.getitwrite.modals.Proposal
import com.example.getitwrite.views.components.DetailHeader
import com.example.getitwrite.views.components.TagCloud

@Composable
fun ProposalDetails(
    proposalId: String,
    proposals: List<Proposal>,
    navigateUp: () -> Unit
) {
    val proposal: Proposal? = remember(proposalId) {
        proposals.find { it.id == proposalId }
    }

    if (proposal != null) {
        Column {
            DetailHeader(title = proposal.title, navigateUp = navigateUp)
            Column(
                modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Blurb", fontWeight = FontWeight.Bold)
                Text(proposal.blurb)
                Divider()
                Text("Author's notes", fontWeight = FontWeight.Bold)
                Text(proposal.authorNotes)
                Text(text = "${proposal.wordCount} words", fontWeight = FontWeight.Light)
                Divider()
                Text(proposal.typeOfProject.joinToString(", "), fontWeight = FontWeight.Bold)
                Text("Genres", fontWeight = FontWeight.Bold)
                TagCloud(tags = proposal.genres, action = null)
                Text("Trigger warnings:", fontWeight = FontWeight.Bold)
                TagCloud(tags = proposal.triggerWarnings, action = null)
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Colours.Dark_Readable,
                        contentColor = Color.White
                    )
                ) {
                    Text("Send Author Message", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}