package com.example.getitwrite.views.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getitwrite.modals.User
import com.example.getitwrite.views.components.DetailHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(user: User, navigateUp: () -> Unit) {
    var bottomSheetContent by remember { mutableStateOf(BottomSheetContent.none) }
    val sheetState = rememberModalBottomSheetState()
    Column {
        DetailHeader(title = "Settings", navigateUp = navigateUp)
        if (bottomSheetContent != BottomSheetContent.none) {
            ModalBottomSheet(
                onDismissRequest = {
                    bottomSheetContent = BottomSheetContent.none
                },
                sheetState = sheetState
            ) {
                if (bottomSheetContent == BottomSheetContent.tsAndCs) {
                    TsAndCsView()
                } else if (bottomSheetContent == BottomSheetContent.changeEmail) {
                    ReAuthBottomSheetNavComponent(nextTask = PostReAuthTask.changeEmail)
                } else if (bottomSheetContent == BottomSheetContent.changePassword) {
                    ReAuthBottomSheetNavComponent(nextTask = PostReAuthTask.changePassword)
                } else {
                    PrivacyPolicyView()
                }
            }
        }
        TextButton(onClick = { bottomSheetContent = BottomSheetContent.tsAndCs }) {
            Text("Terms of Use", fontSize = 18.sp)
        }
        TextButton(onClick = { bottomSheetContent = BottomSheetContent.privacyPolicy }) {
            Text("Privacy Policy", fontSize = 18.sp)
        }
        TextButton(onClick = { bottomSheetContent = BottomSheetContent.changeEmail }) {
            Text("Change email", fontSize = 18.sp)
        }
        TextButton(onClick = { bottomSheetContent = BottomSheetContent.changePassword }) {
            Text("Change password", fontSize = 18.sp)
        }
    }
}

private enum class BottomSheetContent {
    none,
    tsAndCs,
    privacyPolicy,
    changeEmail,
    changePassword
}