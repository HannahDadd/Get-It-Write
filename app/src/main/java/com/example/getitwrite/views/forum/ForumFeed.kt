package com.example.getitwrite.views.forum

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getitwrite.modals.Forum
import com.example.getitwrite.modals.Proposal
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.ProfileImage
import com.example.getitwrite.views.components.TagCloud
import com.example.getitwrite.views.proposals.MakeProposalView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumFeed(user: User, proposals: List<Forum>, select: (Forum) -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add question") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = { showBottomSheet = true }
            )
        }
    ) { innerPadding ->
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                MakeProposalView(user) {
                    showBottomSheet = false
                }
            }
        }
        LazyColumn(Modifier.padding(innerPadding)) {
            items(proposals) { proposal ->
                ProposalView(proposal, selectProposal)
            }
        }
    }
}

@Composable
fun ForumView(forum: Forum, select: (Forum) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(10.dp)
            .clickable { select(forum) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(username = forum.questionerName, profileColour = forum.questionerColour)
            Text(forum.questionerName, fontSize = 20.sp)
        }
        Text(forum.question, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            Text(
                text = DateUtils.getRelativeTimeSpanString(
                    (forum.timestamp.seconds * 1000),
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