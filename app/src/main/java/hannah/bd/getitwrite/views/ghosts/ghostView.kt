package com.hannah.shelfify.views.ghosts

import android.R.attr.duration
import android.R.attr.text
import android.text.Layout
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hannah.bd.getitwrite.GlobalVariables
import hannah.bd.shelfify.MainActivity
import hannah.bd.getitwrite.R

@Composable
fun ghostView() {

    var quote by remember { mutableStateOf("") }

    val ghosts = remember {
        listOf(
            Ghost(
                imageRes = R.drawable.ghost_wizard,
                ghostName = "ghostly",
                comments = GlobalVariables.ghostlyGhostQuotes,
                width = 60.dp,
                height = 80.dp
            ),
            Ghost(
                imageRes = R.drawable.ghost_sid,
                ghostName = "sid",
                comments = GlobalVariables.sidGhostQuotes,
                width = 60.dp,
                height = 70.dp
            ),
            Ghost(
                imageRes = R.drawable.ghost_marge,
                ghostName = "margerie",
                comments = GlobalVariables.margerieGhostQuotes,
                width = 60.dp,
                height = 75.dp
            ),
            Ghost(
                imageRes = R.drawable.ghost_gilly,
                ghostName = "gilly",
                comments = GlobalVariables.gillyGhostQuotes,
                width = 60.dp,
                height = 60.dp
            ),
            Ghost(
                imageRes = R.drawable.ghost_oldman,
                ghostName = "gramp",
                comments = GlobalVariables.grampGhostQuotes,
                width = 60.dp,
                height = 85.dp
            ),
            Ghost(
                imageRes = R.drawable.ghost_paula,
                ghostName = "paula",
                comments = GlobalVariables.paulineGhostQuotes,
                width = 60.dp,
                height = 60.dp
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {

        ghosts.forEach { ghost ->

            SingleGhostView(
                ghost = ghost,
                onGhostTapped = {
                    quote = "hello"
                }
            )
        }

        if (quote.isNotEmpty()) {
            Text(
                text = quote,
                color = Color.Black,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White)
                    .clickable {
                        quote = ""
                    }
                    .padding(16.dp)
                    .wrapContentSize()
            )
        }
    }
}