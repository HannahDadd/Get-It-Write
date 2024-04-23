package com.example.getitwrite.views.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getitwrite.modals.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun ReportAndBlockUser(userToBlock: String, user: User) {
    var showButtons = remember { mutableStateOf(false) }
    val shouldShowDialog = remember { mutableStateOf(false) }
    if (shouldShowDialog.value) {
        MyAlertDialog(shouldShowDialog = shouldShowDialog, user = user, blockUserId = userToBlock)
    }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            Spacer(modifier = Modifier.weight(1.0f))
            TextButton(onClick = { showButtons.value = !showButtons.value }) {
                Icon(Icons.Filled.Info, contentDescription = "", Modifier.padding(end = 10.dp))
            }
        }
        if (showButtons.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
            ) {
                TextButton(onClick = {  }) {
                    Row {
                        Icon(Icons.Filled.Warning, contentDescription = "", Modifier.padding(end = 10.dp))
                        Text("Report content",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Red)
                    }
                }
                Spacer(modifier = Modifier.weight(1.0f))
                TextButton(onClick = { shouldShowDialog.value = true }) {
                    Text("Block user",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun MyAlertDialog(shouldShowDialog: MutableState<Boolean>, user: User, blockUserId: String) {
    var lowerTextString = remember { mutableStateOf("You cannot undo this.") }
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            title = { Text(text = "Are you sure you want to block this user?") },
            text = { Text(text = lowerTextString.value) },
            confirmButton = {
                Button(
                    onClick = {
                        val blockedUserIds = user.blockedUserIds
                        blockedUserIds.add(blockUserId)
                        Firebase.firestore.collection("users").document(user.id)
                            .set(
                                User(
                                    id = user.id,
                                    displayName = user.displayName,
                                    bio = user.bio,
                                    writing = user.writing,
                                    critiqueStyle = user.critiqueStyle,
                                    authors = user.authors,
                                    writingGenres = user.writingGenres,
                                    colour = user.colour,
                                    blockedUserIds = blockedUserIds
                                )
                            )
                            .addOnSuccessListener {
                                shouldShowDialog.value = false
                            }
                            .addOnFailureListener { e ->
                                lowerTextString.value = "There's been a problem. Please try again later."
                            }
                    }
                ) {
                    Text(
                        text = "Confirm",
                        color = Color.White
                    )
                }
            }
        )
    }
}