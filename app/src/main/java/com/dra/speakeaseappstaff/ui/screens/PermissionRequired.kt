package com.dra.speakeaseappstaff.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequired(
    permission: String,
    rationale: String = "Camera access is required to use this feature.",
    onPermissionGranted: @Composable () -> Unit
) {
    val permissionState = rememberPermissionState(permission)

    when (permissionState.status) {
        is PermissionStatus.Granted -> {
            onPermissionGranted()
        }
        is PermissionStatus.Denied -> {
            val shouldShowRationale = (permissionState.status as PermissionStatus.Denied).shouldShowRationale

            LaunchedEffect(permissionState) {
                permissionState.launchPermissionRequest()
            }

            if (shouldShowRationale) {
                Text(rationale)
            }
        }
    }
}
