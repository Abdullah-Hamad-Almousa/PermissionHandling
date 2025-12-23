package com.mysticbyte.permissionhandling

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController

@Composable
fun PermissionItem(
    label: String,
    state: PermissionState,
    onRequest: () -> Unit,
    controller: PermissionsController
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(label, color = Color.White)

        when (state) {
            PermissionState.Granted -> {
                Text("Granted", color = Color.Green)
            }

            PermissionState.DeniedAlways -> {
                Text("Permanently Denied", color = Color.Red)
                Button(onClick = { controller.openAppSettings() }) {
                    Text("Open Settings")
                }
            }

            else -> {
                Button(onClick = onRequest) {
                    Text("Request")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}