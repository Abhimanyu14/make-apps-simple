/*
 * Copyright 2025-2025 Abhimanyu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.makeappssimple.abhimanyu.barcodes.android.common.ui.permissions

import android.content.pm.PackageManager
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
internal fun rememberRequiredPermissionStatus(
    requiredPermission: String,
): PermissionRequest {
    val context = LocalContext.current
    val activity = LocalActivity.current
    var permissionStatus by remember(
        key1 = requiredPermission,
    ) {
        val selfPermissionStatus = ContextCompat.checkSelfPermission(
            context,
            requiredPermission
        )
        val isGranted =
            selfPermissionStatus == PackageManager.PERMISSION_GRANTED
        mutableStateOf(
            value = if (isGranted) {
                PermissionStatus.GRANTED
            } else {
                PermissionStatus.UNKNOWN
            },
        )
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner,
        key2 = requiredPermission,
    ) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val isGranted = ContextCompat.checkSelfPermission(
                    context,
                    requiredPermission,
                ) == PackageManager.PERMISSION_GRANTED

                if (isGranted) {
                    permissionStatus = PermissionStatus.GRANTED
                } else if (permissionStatus == PermissionStatus.GRANTED) {
                    permissionStatus = PermissionStatus.UNKNOWN
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val permissionRequestLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { isPermissionGrantedValue: Boolean ->
        permissionStatus = if (isPermissionGrantedValue) {
            PermissionStatus.GRANTED
        } else {
            activity?.let {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        requiredPermission
                    )
                ) {
                    PermissionStatus.DENIED
                } else {
                    PermissionStatus.PERMANENTLY_DENIED
                }
            } ?: PermissionStatus.DENIED
        }
    }

    val launchPermissionRequest: () -> Unit = {
        permissionRequestLauncher.launch(
            input = requiredPermission,
        )
    }

    return PermissionRequest(
        permissionStatus = permissionStatus,
        requestPermission = launchPermissionRequest,
    )
}
