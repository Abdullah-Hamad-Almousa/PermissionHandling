package com.mysticbyte.permissionhandling.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import kotlinx.coroutines.launch

class PermissionsViewModel(
    private val controller: PermissionsController
) : ViewModel() {

    var audioState by mutableStateOf(PermissionState.NotDetermined)
        private set

    var cameraState by mutableStateOf(PermissionState.NotDetermined)
        private set

    var storageState by mutableStateOf(PermissionState.NotDetermined)
        private set

    var locationState by mutableStateOf(PermissionState.NotDetermined)
        private set

    init {
        viewModelScope.launch {
            audioState = controller.getPermissionState(Permission.RECORD_AUDIO)
            cameraState = controller.getPermissionState(Permission.CAMERA)
            storageState = controller.getPermissionState(Permission.STORAGE)
            locationState = controller.getPermissionState(Permission.LOCATION)
        }
    }

    fun request(permission: Permission) {
        viewModelScope.launch {
            try {
                controller.providePermission(permission)
                when (permission) {
                    Permission.RECORD_AUDIO -> audioState = PermissionState.Granted
                    Permission.CAMERA -> cameraState = PermissionState.Granted
                    Permission.STORAGE -> storageState = PermissionState.Granted
                    Permission.LOCATION -> locationState = PermissionState.Granted
                    else -> {
                        throw IllegalArgumentException("Unsupported permission: $permission")
                    }
                }

            } catch (e: DeniedAlwaysException) {
                when (permission) {
                    Permission.RECORD_AUDIO -> audioState = PermissionState.DeniedAlways
                    Permission.CAMERA -> cameraState = PermissionState.DeniedAlways
                    Permission.STORAGE -> storageState = PermissionState.DeniedAlways
                    Permission.LOCATION -> locationState = PermissionState.DeniedAlways
                    else -> {
                        throw IllegalArgumentException("Unsupported permission: $permission")
                    }
                }
            } catch (e: DeniedException) {
                when (permission) {
                    Permission.RECORD_AUDIO -> audioState = PermissionState.Denied
                    Permission.CAMERA -> cameraState = PermissionState.Denied
                    Permission.STORAGE -> storageState = PermissionState.Denied
                    Permission.LOCATION -> locationState = PermissionState.Denied
                    else -> {
                        throw IllegalArgumentException("Unsupported permission: $permission")
                    }
                }
            }
        }
    }
}