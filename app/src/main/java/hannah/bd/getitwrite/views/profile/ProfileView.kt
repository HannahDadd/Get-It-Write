package hannah.bd.getitwrite.views.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.modals.ContentToReportType
import hannah.bd.getitwrite.modals.RequestPositivity
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.theme.AppTypography
import hannah.bd.getitwrite.views.account.StatsSection
import hannah.bd.getitwrite.views.components.CheckInput
import hannah.bd.getitwrite.views.components.DetailHeader
import hannah.bd.getitwrite.views.components.ErrorText
import hannah.bd.getitwrite.views.components.ReportAndBlockUser
import hannah.bd.getitwrite.views.components.TagCloud
import hannah.bd.getitwrite.views.positivityCorner.getRandPeice
import hannah.bd.getitwrite.views.proposals.sendAuthorMessage

@Composable
fun ProfilePopUp(user: User, navController: NavController, closeAction: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatsSection(user)
        Text("Bio", fontWeight = FontWeight.Bold)
        Text(user.bio)
        Text("Favourite authors", fontWeight = FontWeight.Bold)
        TagCloud(tags = user.authors, action = null)
        Text("Writing", fontWeight = FontWeight.Bold)
        Text(user.writing)
        Text("Writing Genres", fontWeight = FontWeight.Bold)
        TagCloud(tags = user.writingGenres, action = null)
        Text("Critique Style", fontWeight = FontWeight.Bold)
        Text(user.critiqueStyle)
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                closeAction()
                navController.navigate("usersProposals/${user.id}")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                "View Proposals by this Writer",
                Modifier.padding(10.dp),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Composable
fun ProfileView(navController: NavController, ownProfile: Boolean,
                loggedInUser: User, user: User, navigateUp: () -> Unit) {
    Column {
        DetailHeader(title = user.displayName, navigateUp = navigateUp)
        Column(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Bio", fontWeight = FontWeight.Bold)
            Text(user.bio)
            Text("Favourite authors", fontWeight = FontWeight.Bold)
            TagCloud(tags = user.authors, action = null)
            Text("Writing", fontWeight = FontWeight.Bold)
            Text(user.writing)
            Text("Writing Genres", fontWeight = FontWeight.Bold)
            TagCloud(tags = user.writingGenres, action = null)
            Text("Critique Style", fontWeight = FontWeight.Bold)
            Text(user.critiqueStyle)
            if (ownProfile) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navController.navigate("editProfile")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary)
                ) {
                    Text("Edit Profile", Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}