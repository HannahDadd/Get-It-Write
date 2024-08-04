package hannah.bd.getitwrite.views.critiqueFrenzy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.views.components.ErrorText
import hannah.bd.getitwrite.views.components.SquareTileButton
import hannah.bd.getitwrite.views.proposals.GenreFeed
import hannah.bd.getitwrite.views.proposals.ProposalDetails
import hannah.bd.getitwrite.views.proposals.getProposalsByGenre

@Composable
fun QuickQueryCritique() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp)
    ) {
        Text(
            text = "Quick query critique",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondary
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SquareTileButton(
                    title = "To critique",
                    wordCount = "",
                    backgroundColour = MaterialTheme.colorScheme.primaryContainer,
                    textColour = MaterialTheme.colorScheme.onPrimaryContainer,
                    icon = Icons.Default.Email,
                    size = 150.dp,
                    onClick = {}
                )
            }
            item {
                SquareTileButton(
                    title = "Add your own.",
                    wordCount = "",
                    backgroundColour = MaterialTheme.colorScheme.secondaryContainer,
                    textColour = MaterialTheme.colorScheme.onSecondaryContainer,
                    icon = Icons.Default.Add,
                    size = 150.dp,
                    onClick = {}
                )
            }
            item {
                SquareTileButton(
                    title = "View more.",
                    wordCount = "",
                    backgroundColour = MaterialTheme.colorScheme.tertiaryContainer,
                    textColour = MaterialTheme.colorScheme.onTertiaryContainer,
                    icon = Icons.Default.ArrowForward,
                    size = 150.dp,
                    onClick = {}
                )
            }
        }
    }
}