package hannah.bd.getitwrite.views.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetTheFamily(
    navigateBack: () -> Unit
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "👻 Meet the Family 👻",
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
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(R.drawable.family_photo),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                item {
                    Column {
                        Image(
                            painter = painterResource(R.drawable.ghost_gilly),
                            contentDescription = null,
                            modifier = Modifier.height(50.dp)
                        )
                        Text(
                            "Gilly",
                            fontFamily = FontFamily(Font(R.font.dynapuff))
                        )
                        Text("""
                            Gilly loves reading, and she sings to the silence when she thinks the library is empty.
                        """.trimIndent(),
                                fontFamily = FontFamily(Font(R.font.bellefairregularfont)),
                        )
                    }
                }
                item {
                    Column {
                        Image(
                            painter = painterResource(R.drawable.ghost_sid),
                            contentDescription = null,
                            modifier = Modifier.height(50.dp)
                        )
                        Text(
                            "Sid",
                            fontFamily = FontFamily(Font(R.font.dynapuff))
                        )
                        Text("""
                            Sid is too cool for school. He likes books, but he won't be telling his friends that.
                        """.trimIndent(),
                            fontFamily = FontFamily(Font(R.font.bellefairregularfont)),
                        )
                    }
                }
                item {
                    Column {
                        Image(
                            painter = painterResource(R.drawable.ghost_paula),
                            contentDescription = null,
                            modifier = Modifier.height(50.dp)
                        )
                        Text(
                            "Paula",
                            fontFamily = FontFamily(Font(R.font.dynapuff))
                        )
                        Text("""
                            Paula lives for books. The chunkier of the better.
                        """.trimIndent(),
                            fontFamily = FontFamily(Font(R.font.bellefairregularfont)),
                        )
                    }
                }
                item {
                    Column {
                        Image(
                            painter = painterResource(R.drawable.ghost_wizard),
                            contentDescription = null,
                            modifier = Modifier.height(50.dp)
                        )
                        Text(
                            "Wizley",
                            fontFamily = FontFamily(Font(R.font.dynapuff))
                        )
                        Text("""
                            Some say a spell gone wrong left him a ghost...
                        """.trimIndent(),
                            fontFamily = FontFamily(Font(R.font.bellefairregularfont)),
                        )
                    }
                }
                item {
                    Column {
                        Image(
                            painter = painterResource(R.drawable.ghost_marge),
                            contentDescription = null,
                            modifier = Modifier.height(50.dp)
                        )
                        Text(
                            "Margerie",
                            fontFamily = FontFamily(Font(R.font.dynapuff))
                        )
                        Text("""
                            She just wants to make sure you're looking after yourself during those long writing sessions.
                        """.trimIndent(),
                            fontFamily = FontFamily(Font(R.font.bellefairregularfont)),
                        )
                    }
                }
                item {
                    Column {
                        Image(
                            painter = painterResource(R.drawable.ghost_oldman),
                            contentDescription = null,
                            modifier = Modifier.height(50.dp)
                        )
                        Text(
                            "Gramps",
                            fontFamily = FontFamily(Font(R.font.dynapuff))
                        )
                        Text("""
                            He's a little grumpy on the outside, but he's a big old softy really.
                        """.trimIndent(),
                            fontFamily = FontFamily(Font(R.font.bellefairregularfont)),
                        )
                    }
                }
            }
        }
    }
}