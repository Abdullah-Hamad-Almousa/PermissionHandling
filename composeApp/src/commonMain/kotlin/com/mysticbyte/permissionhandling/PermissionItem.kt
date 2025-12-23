package com.mysticbyte.permissionhandling

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mysticbyte.permissionhandling.theme.AppColor
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

        Text(label, color = AppColor.grayColor)

        when (state) {
            PermissionState.Granted -> {
                Text("Granted", color = AppColor.greenColor)
            }

            PermissionState.DeniedAlways -> {
                Text("Permanently Denied", color = AppColor.redColor)
                Button(onClick = { controller.openAppSettings() }) {
                    Text(
                        "Open Settings",
                        color = AppColor.whiteColor)
                }
            }

            else -> {
                Button(onClick = onRequest) {
                    Text(
                        "Request")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}