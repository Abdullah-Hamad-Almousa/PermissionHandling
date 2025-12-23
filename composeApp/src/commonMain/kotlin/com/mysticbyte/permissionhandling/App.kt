package com.mysticbyte.permissionhandling

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mysticbyte.permissionhandling.theme.IdTextApp
import com.mysticbyte.permissionhandling.viewmodel.PermissionsViewModel
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

    Box(
        modifier = Modifier
            .background(Color(0xFF232323))
            .fillMaxSize()
    ){

        val factory = rememberPermissionsControllerFactory()
        val controller = remember(factory){
            factory.createPermissionsController()
        }

        BindEffect(controller)

        val viewModel = viewModel {
            PermissionsViewModel(controller)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){

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

            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(
                    text = IdTextApp.idTextApp,
                    fontSize = 12.sp,
                    color = Color(0xFF9A9A9A),
                    modifier = Modifier.padding(6.dp),
                    textDecoration = TextDecoration.Underline
                )
            }

        }

    }

}