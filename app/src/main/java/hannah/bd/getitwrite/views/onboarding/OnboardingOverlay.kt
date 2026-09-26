package hannah.bd.getitwrite.views.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.R
import hannah.bd.getitwrite.modals.OnboardingPage
import kotlin.collections.lastIndex

@Composable
fun OnboardingOverlay(
    pages: List<OnboardingPage>,
    onFinished: () -> Unit
) {
    var currentPage by remember { mutableIntStateOf(0) }

    val page = pages[currentPage]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row {

                Image(
                    painter = painterResource(R.drawable.ghost_gilly),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )

                Spacer(Modifier.height(24.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    OnboardingText(page.description)

                    if(page.description2 != "") {
                        OnboardingText(page.description2)
                    }
                }
            }

            Spacer(Modifier.height(48.dp))

            Row {
                Spacer(Modifier.height(32.dp))

                Image(
                    painter = painterResource(R.drawable.next_btn),
                    contentDescription = "Next button",
                    modifier = Modifier.size(75.dp)
                        .clickable {
                            if (currentPage < pages.lastIndex) {
                                currentPage++
                            } else {
                                onFinished()
                            }
                        }
                )
            }
        }
    }
}