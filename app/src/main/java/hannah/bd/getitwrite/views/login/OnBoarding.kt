package hannah.bd.getitwrite.views.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ShowOpeningPage(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Welcome to Get It Write!",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "What are you looking for most?",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        FilledTonalButton(onClick = { navController.navigate("enterUsernameCosyCorner") },
            modifier = Modifier.padding(16.dp)) {
            Text("A supportive community of writers.",
                style = MaterialTheme.typography.bodyMedium)
        }
        FilledTonalButton(onClick = { navController.navigate("enterUsernameKillerCritiquer") },
            modifier = Modifier.padding(16.dp)) {
            Text("Honest critiques of your work.",
                style = MaterialTheme.typography.bodyMedium)
            }
    }
}