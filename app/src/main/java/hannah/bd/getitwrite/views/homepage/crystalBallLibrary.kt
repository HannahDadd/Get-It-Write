package hannah.bd.shelfify.views.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hannah.shelfify.views.ghosts.ghostView
import hannah.bd.getitwrite.R
import hannah.bd.shelfify.modals.AppDatabase
import hannah.bd.shelfify.modals.UserPreferences
import hannah.bd.shelfify.modals.onboardingPages
import hannah.bd.shelfify.views.onboarding.OnboardingOverlay
import kotlinx.coroutines.launch

@Composable
fun crystalBallLibrary(
    backAction: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        backGroundView()
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painter = painterResource(R.drawable.library_21),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }


        Column(
            modifier = Modifier
                .padding(24.dp)
                .padding(bottom = 70.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.6f)
                ),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    "The library completes at 100k words.",
                    fontFamily = FontFamily(Font(R.font.bellefairregularfont)),
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1.0f))

            Row {
                Spacer(modifier = Modifier.weight(1.0f))
                Image(
                    painter = painterResource(R.drawable.crystal_btn),
                    contentDescription = "Menu",
                    modifier = Modifier
                        .size(75.dp)
                        .clickable {
                            backAction()
                        }
                )
            }
        }
    }
}