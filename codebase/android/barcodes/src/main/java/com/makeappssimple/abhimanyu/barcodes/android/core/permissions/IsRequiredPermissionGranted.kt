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

package com.makeappssimple.abhimanyu.barcodes.android.core.permissions

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
internal fun rememberIsRequiredPermissionGranted(
    requiredPermission: String,
    permissionDeniedMessage: String,
): Boolean? {
    val context = LocalContext.current
    val isPermissionGranted: MutableState<Boolean?> = remember {
        mutableStateOf(
            value = null,
        )
    }

    val requestPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { isPermissionGrantedValue: Boolean ->
        if (isPermissionGrantedValue) {
            isPermissionGranted.value = true
        } else {
            Toast.makeText(
                context,
                permissionDeniedMessage,
                Toast.LENGTH_SHORT,
            ).show()
            isPermissionGranted.value = false
        }
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        val isRequiredPermissionGranted = ContextCompat.checkSelfPermission(
            context,
            requiredPermission
        ) == PackageManager.PERMISSION_GRANTED
        if (isRequiredPermissionGranted) {
            isPermissionGranted.value = true
        } else {
            requestPermissionResultLauncher.launch(requiredPermission)
        }
    }

    return remember(
        key1 = isPermissionGranted.value,
    ) {
        isPermissionGranted.value
    }
}
