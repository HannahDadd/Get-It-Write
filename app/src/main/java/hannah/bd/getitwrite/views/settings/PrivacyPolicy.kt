package hannah.bd.getitwrite.views.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun privacyPolicyView(
    navigateBack: () -> Unit
) {
    var enabled by remember { mutableStateOf(true)}

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Privacy Policy",
                        fontFamily = FontFamily(Font(R.font.dynapuff)),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            buildAnnotatedString {
                                withLink(
                                    LinkAnnotation.Url(
                                        "https://writingapps5.wordpress.com/privacy-policy-writing-games/",
                                        TextLinkStyles(style = SpanStyle(color = Color.Blue))
                                    )
                                ) {
                                    append("Our Privacy Policy can be found on our website")
                                }
                            },
                            fontFamily = FontFamily(Font(R.font.bellefairregularfont)),
                        )
                    }
                }
            }
        }
    }
}