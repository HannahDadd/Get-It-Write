package hannah.bd.getitwrite.views.proposals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hannah.bd.getitwrite.Colours
import hannah.bd.getitwrite.modals.ContentToReportType
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.DetailHeader
import hannah.bd.getitwrite.views.components.ReportAndBlockUser
import hannah.bd.getitwrite.views.components.TagCloud
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.theme.AppTypography
import java.util.UUID

@Composable
fun ProposalDetails(
    proposal: Proposal,
    user: User,
    navController: NavController
) {
    Scaffold(
        bottomBar = {
            Button(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                onClick = {
                    Firebase.firestore.collection("chats")
                        .whereArrayContains("users", arrayOf(user.id, proposal.writerId))
                        .get().addOnSuccessListener {
                            if (it.isEmpty) {
                                val id = UUID.randomUUID().toString()
                                Firebase.firestore.collection("chats").document(id)
                                    .set(mapOf("users" to listOf(user.id, proposal.writerId)))
                                    .addOnSuccessListener { navController.navigate("chatDetails/${id}${proposal.writerName}") }
                                    .addOnFailureListener { }
                            } else {
                                val doc = it.documents.get(0)
                                navController.navigate("chatDetails/${doc.id}${proposal.writerName}")
                            }
                        }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    "Send Author Message",
                    Modifier.padding(10.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            DetailHeader(title = proposal.title, navigateUp = { navController.navigateUp() })
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Blurb", style = AppTypography.titleMedium,)
                Text(proposal.blurb)
                Divider()
                if (proposal.authorNotes.isNotBlank()) {
                    Text("Author's notes", style = AppTypography.titleMedium,)
                    Text(proposal.authorNotes)
                }
                Text(text = "${proposal.wordCount} words", style = AppTypography.labelMedium,)
                Divider()
                if (proposal.typeOfProject.isNotEmpty()) {
                    Text(proposal.typeOfProject.joinToString(", "), fontWeight = FontWeight.Bold)
                }
                Text("Genres", style = AppTypography.titleMedium,)
                TagCloud(tags = proposal.genres, action = null)
                Text("Trigger warnings:", style = AppTypography.titleMedium,)
                if (proposal.triggerWarnings.isEmpty()) {
                    Text("None")
                } else {
                    TagCloud(tags = proposal.triggerWarnings, action = null)
                }
                ReportAndBlockUser(
                    userToBlock = proposal.writerId,
                    user = user,
                    contentToReport = proposal,
                    contentToReportType = ContentToReportType.proposals,
                    questionId = null,
                    chatId = null
                )
            }
        }
    }
}