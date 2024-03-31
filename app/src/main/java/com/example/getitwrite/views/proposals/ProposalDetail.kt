package com.example.getitwrite.views.proposals

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getitwrite.Colours
import com.example.getitwrite.modals.Proposal
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
            ProposalDetailHeader(title = proposal.title, navigateUp = navigateUp)
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

@Composable
private fun ProposalDetailHeader(
    title: String,
    navigateUp: () -> Unit
) {
    Box {
        Text(title, fontSize = 40.sp)
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            contentColor = Color.White
        ) {
            IconButton(onClick = navigateUp) {
                Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "")
            }
        }
    }
}