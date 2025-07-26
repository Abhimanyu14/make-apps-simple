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

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.barcodes.android.core.barcodescanner.barcodescanner.BarcodeAnalyser
import com.makeappssimple.abhimanyu.barcodes.android.core.common.constants.DeeplinkConstants.BARCODE_FORMAT
import com.makeappssimple.abhimanyu.barcodes.android.core.common.constants.DeeplinkConstants.BARCODE_VALUE
import com.makeappssimple.abhimanyu.barcodes.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.barcodes.android.core.model.BarcodeFormat
import com.makeappssimple.abhimanyu.barcodes.android.core.permissions.rememberIsCameraPermissionGranted
import com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.scanbarcode.event.ScanBarcodeScreenUIEventHandler
import com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.scanbarcode.state.rememberScanBarcodeScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.scanbarcode.viewmodel.ScanBarcodeScreenViewModel
import kotlinx.coroutines.awaitCancellation
import org.koin.compose.viewmodel.koinViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
internal fun ScanBarcodeScreen(
    screenViewModel: ScanBarcodeScreenViewModel = koinViewModel(),
) {
    screenViewModel.logError(
        message = "Inside ScanBarcodeScreen",
    )

    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val activity = context as? Activity
    val isCameraPermissionGranted = rememberIsCameraPermissionGranted()

    val screenUIData: MyResult<ScanBarcodeScreenUIData>? by screenViewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberScanBarcodeScreenUIState(
        data = screenUIData,
    )
    val onBarcodeScanned =
        { barcodeFormat: BarcodeFormat, barcodeValue: String ->
            if (uiState.isDeeplink) {
                activity?.let {
                    handleActivityResult(
                        activity = activity,
                        barcodeFormat = barcodeFormat.value,
                        barcodeValue = barcodeValue,
                    )
                }
            } else {
                screenViewModel.saveBarcode(
                    barcodeValue = barcodeValue,
                    barcodeFormat = barcodeFormat.value,
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
            Unit
        }
    }

    val screenUIEventHandler = remember(
        key1 = onTopAppBarNavigationButtonClick,
    ) {
        ScanBarcodeScreenUIEventHandler(
            onTopAppBarNavigationButtonClick = onTopAppBarNavigationButtonClick,
        )
    }

    // Used to set up a link between the Camera and your UI.
    var surfaceRequest by remember {
        mutableStateOf<SurfaceRequest?>(
            null
        )
    }

    LaunchedEffect(
        key1 = lifecycleOwner,
    ) {
        val cameraExecutor: ExecutorService =
            Executors.newSingleThreadExecutor()
        val processCameraProvider = ProcessCameraProvider.awaitInstance(
            context = context,
        )
        val cameraPreviewUseCase = Preview.Builder().build().apply {
            setSurfaceProvider { newSurfaceRequest ->
                surfaceRequest = newSurfaceRequest
            }
        }

        val barcodeAnalyser = BarcodeAnalyser(
            logError = screenViewModel::logError,
            getCurrentTimeMillis = screenViewModel::getCurrentTimeMillis,
        ) { barcodes ->
            barcodes.forEach { barcode ->
                barcode.rawValue?.let { barcodeValue ->
                    screenViewModel.logError(
                        message = "Barcode value detected: ${barcodeValue}.",
                    )

                    processCameraProvider.unbindAll()
                    BarcodeFormat.fromValue(barcode.format)
                        ?.let { barcodeFormat ->
                            onBarcodeScanned(barcodeFormat, barcodeValue)
                        }
                }
            }
        }
        val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, barcodeAnalyser)
            }

        try {
            processCameraProvider.unbindAll()
            processCameraProvider.bindToLifecycle(
                lifecycleOwner = lifecycleOwner,
                cameraSelector = DEFAULT_BACK_CAMERA,
                cameraPreviewUseCase,
                imageAnalysis,
            )
        } catch (
            exception: Exception,
        ) {
            screenViewModel.logError(
                message = "Use case binding failed with exception : $exception",
            )
        }

        // Cancellation signals we're done with the camera
        try {
            awaitCancellation()
        } finally {
            processCameraProvider.unbindAll()
        }
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
        surfaceRequest = surfaceRequest,
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
