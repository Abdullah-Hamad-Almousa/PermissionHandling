package com.mysticbyte.permissionhandling

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mysticbyte.permissionhandling.viewmodel.PermissionsViewModel
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

    Box(){

        val factory = rememberPermissionsControllerFactory()
        val controller = remember(factory){
            factory.createPermissionsController()
        }

        BindEffect(controller)

        val viewModel = viewModel {
            PermissionsViewModel(controller)
        }

        Column(

            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            when(viewModel.state) {
                PermissionState.Granted -> {
                    Text("Record Audio Permission Granted!")
                }
                PermissionState.DeniedAlways -> {

                    Text("Permission Was Permanently Declined!")
                    Button(onClick = {

                        controller.openAppSettings()

                    }){
                        Text("Open App Settings")
                    }
                }
                else -> {
                    Button(onClick = {
                        viewModel.provideOrRequestAudioPermissions()
                    }){
                        Text("Request Permission")
                    }
                }
            }

        }

    }

}