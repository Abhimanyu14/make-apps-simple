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

package com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.scanbarcode.screen

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.barcodes.android.R
import com.makeappssimple.abhimanyu.barcodes.android.core.common.constants.DeeplinkConstants.BARCODE_FORMAT
import com.makeappssimple.abhimanyu.barcodes.android.core.common.constants.DeeplinkConstants.BARCODE_VALUE
import com.makeappssimple.abhimanyu.barcodes.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.scanbarcode.event.ScanBarcodeScreenUIEvent
import com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.scanbarcode.event.ScanBarcodeScreenUIEventHandler
import com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.scanbarcode.state.rememberScanBarcodeScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.scanbarcode.viewmodel.ScanBarcodeScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ScanBarcodeScreen(
    screenViewModel: ScanBarcodeScreenViewModel = koinViewModel(),
) {
    screenViewModel.logKit.logError(
        message = "Inside ScanBarcodeScreen",
    )

    val context = LocalContext.current
    val activity = context as? Activity
    val isCameraPermissionGranted = rememberIsCameraPermissionGranted()

    val screenUIData: MyResult<ScanBarcodeScreenUIData>? by screenViewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberScanBarcodeScreenUIState(
        data = screenUIData,
    )
    val onBarcodeScanned: (ScanBarcodeScreenUIEvent.OnBarcodeScanned) -> Unit? =
        { uiEvent: ScanBarcodeScreenUIEvent.OnBarcodeScanned ->
            if (uiState.isDeeplink) {
                activity?.let {
                    handleActivityResult(
                        activity = activity,
                        barcodeFormat = uiEvent.barcodeFormat.value,
                        barcodeValue = uiEvent.barcodeValue,
                    )
                }
            } else {
                screenViewModel.saveBarcode(
                    barcodeValue = uiEvent.barcodeValue,
                    barcodeFormat = uiEvent.barcodeFormat.value,
                )
            }
        }
    val onTopAppBarNavigationButtonClick: () -> Unit? = {
        if (uiState.isDeeplink) {
            activity?.let {
                handleActivityResultCanceled(
                    activity = activity,
                )
            }
        } else {
            screenViewModel.navigateUp()
        }
    }

    val screenUIEventHandler = remember(
        key1 = onBarcodeScanned,
        key2 = onTopAppBarNavigationButtonClick,
    ) {
        ScanBarcodeScreenUIEventHandler(
            onBarcodeScanned = onBarcodeScanned,
            onTopAppBarNavigationButtonClick = onTopAppBarNavigationButtonClick,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    LaunchedEffect(
        key1 = isCameraPermissionGranted,
    ) {
        if (isCameraPermissionGranted == false) {
            screenViewModel.navigateUp()
        }
    }

    BackHandler(
        enabled = uiState.isDeeplink,
    ) {
        activity?.let {
            handleActivityResultCanceled(
                activity = activity,
            )
        }
    }

    ScanBarcodeScreenUI(
        isCameraPermissionGranted = isCameraPermissionGranted == true,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}

private fun handleActivityResult(
    activity: Activity,
    barcodeFormat: Int,
    barcodeValue: String,
) {
    val resultIntent = Intent().apply {
        putExtra(BARCODE_VALUE, barcodeValue)
        putExtra(BARCODE_FORMAT, barcodeFormat)
    }
    activity.setResult(RESULT_OK, resultIntent)
    activity.finish()
}

private fun handleActivityResultCanceled(
    activity: Activity,
) {
    activity.setResult(RESULT_CANCELED)
    activity.finish()
}

@Composable
private fun rememberIsCameraPermissionGranted(): Boolean? {
    val context = LocalContext.current
    val isCameraPermissionGranted: MutableState<Boolean?> = remember {
        mutableStateOf(
            value = null,
        )
    }

    val cameraPermissionDeniedMessage = stringResource(
        id = R.string.screen_scan_barcode_toast_message_camera_permissions_denied,
    )

    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            isCameraPermissionGranted.value = true
        } else {
            Toast.makeText(
                context,
                cameraPermissionDeniedMessage,
                Toast.LENGTH_SHORT
            ).show()
            isCameraPermissionGranted.value = false
        }
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        val areRequiredPermissionsGranted = arrayOf(
            Manifest.permission.CAMERA,
        ).all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
        if (areRequiredPermissionsGranted) {
            isCameraPermissionGranted.value = true
        } else {
            cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA)
        }
    }
    return remember(
        key1 = isCameraPermissionGranted.value,
    ) {
        isCameraPermissionGranted.value
    }
}
