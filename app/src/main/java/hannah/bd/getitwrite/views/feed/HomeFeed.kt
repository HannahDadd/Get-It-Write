package hannah.bd.getitwrite.views.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.modals.Proposal
import hannah.bd.getitwrite.modals.Question
import hannah.bd.getitwrite.modals.RequestCritique
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.views.components.SquareTileButton

@Composable
fun HomeFeed(user: User, questions: List<Question>, critiqueFrenzy: List<RequestCritique>, toCritiques: List<RequestCritique>,
             proposals: List<Proposal>, selectProposal: (Proposal) -> Unit,
             selectChat: (String, String, String) -> Unit, selectCritiqueRequest: (String) -> Unit,
             selectQuestion: (String) -> Unit, selectFrenzy: (String) -> Unit) {
    LazyColumn {
        item {
            WorkToCritique(user.displayName, toCritiques)
        }
        item {
            CritiquedWord()
        }
        item {
            RecomendedCritiquers()
        }
        item {
            AIPromo()
        }
        item {
            JoinTheConvo(questions)
        }
        item {
            FindPartnersByAudience()
        }
        item {
            QuickQueryCritique()
        }
        item {
            FreeForAll()
        }
        item {
            PositiveFeedback()
        }
        item {
            FindPartnersByGenre()
        }
    }
}

