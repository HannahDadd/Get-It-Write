package com.hannah.shelfify.views.ghosts

import androidx.compose.ui.unit.Dp
import java.util.UUID

data class Ghost(
    val id: String = UUID.randomUUID().toString(),
    val imageRes: Int,
    val ghostName: String,
    val comments: List<String>,
    val width: Dp,
    val height: Dp
)