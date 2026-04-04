/*
 * Copyright 2025-2026 Abhimanyu
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

package com.makeappssimple.abhimanyu.barcodes.android.features.scan_barcode.presentation.navigation

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.provider.Settings
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
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.makeappssimple.abhimanyu.barcodes.android.core.domain.model.BarcodeFormatDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.BarcodesScreen
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.constants.DeeplinkUrl.BASE_URL
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.constants.DeeplinkUrl.BROWSER_BASE_URL
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.barcodes.android.features.scan_barcode.presentation.scan_barcode.view_model.ScanBarcodeScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.features.scan_barcode.ui.barcode_scanner.barcode_scanner.BarcodeAnalyser
import com.makeappssimple.abhimanyu.barcodes.android.features.scan_barcode.ui.barcode_scanner.camera.BarcodeScannerPreview
import com.makeappssimple.abhimanyu.barcodes.android.features.scan_barcode.ui.scan_barcode.screen.ScanBarcodeScreen
import com.makeappssimple.abhimanyu.barcodes.android.shared.ui.constants.DeeplinkConstants.BARCODE_FORMAT
import com.makeappssimple.abhimanyu.barcodes.android.shared.ui.constants.DeeplinkConstants.BARCODE_VALUE
import com.makeappssimple.abhimanyu.barcodes.android.shared.ui.permissions.PermissionStatus
import com.makeappssimple.abhimanyu.barcodes.android.shared.ui.permissions.rememberCameraPermissionStatus
import kotlinx.coroutines.awaitCancellation
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

internal fun NavGraphBuilder.scanBarcodeNavGraph() {
    composable(
        route = "${BarcodesScreen.ScanBarcode.route}?${NavigationArguments.DEEPLINK}={${NavigationArguments.DEEPLINK}}",
        arguments = listOf(
            navArgument(NavigationArguments.DEEPLINK) {
                type = NavType.BoolType
                defaultValue = false
            },
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern =
                    "$BROWSER_BASE_URL/${BarcodesScreen.ScanBarcode.route}/?${NavigationArguments.DEEPLINK}={${NavigationArguments.DEEPLINK}}"
            },
            navDeepLink {
                uriPattern =
                    "$BASE_URL/${BarcodesScreen.ScanBarcode.route}/?${NavigationArguments.DEEPLINK}={${NavigationArguments.DEEPLINK}}"
            },
        ),
    ) {
        val context = LocalContext.current
        val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
        val activity = context as? Activity
        val cameraPermissionRequest = rememberCameraPermissionStatus()
        val screenViewModel: ScanBarcodeScreenViewModel = koinViewModel()

        val handleActivityResult: (barcodeFormat: Int, barcodeValue: String) -> Unit = { barcodeFormat, barcodeValue ->
            val resultIntent = Intent().apply {
                putExtra(
                    BARCODE_VALUE,
                    barcodeValue,
                )
                putExtra(
                    BARCODE_FORMAT,
                    barcodeFormat,
                )
            }
            activity?.setResult(
                RESULT_OK,
                resultIntent,
            )
            activity?.finish()
        }
        val handleActivityResultCanceled: () -> Unit = {
            activity?.setResult(RESULT_CANCELED)
            activity?.finish()
        }
        val openAppSettings: () -> Unit = {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts(
                    "package",
                    context.packageName,
                    null,
                ),
            )
            activity?.startActivity(intent)
        }

        ScanBarcodeScreen(
            screenViewModel = screenViewModel,
            cameraPermissionRequest = cameraPermissionRequest,
            handleActivityResult = handleActivityResult,
            handleActivityResultCanceled = handleActivityResultCanceled,
            openAppSettings = openAppSettings,
            cameraContent = { isScanning, onBarcodeScanned ->
                var surfaceRequest by remember {
                    mutableStateOf<SurfaceRequest?>(
                        null,
                    )
                }

                LaunchedEffect(
                    key1 = cameraPermissionRequest.permissionStatus,
                ) {
                    screenViewModel.onCameraPermissionChanged(
                        isCameraPermissionGranted = cameraPermissionRequest.permissionStatus == PermissionStatus.Granted,
                    )
                }

                val koin = getKoin()

                LaunchedEffect(
                    key1 = lifecycleOwner,
                    key2 = cameraPermissionRequest.permissionStatus,
                    key3 = isScanning,
                ) {
                    if (cameraPermissionRequest.permissionStatus != PermissionStatus.Granted || !isScanning) {
                        return@LaunchedEffect
                    }
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

                    val barcodeScanner: BarcodeScanner = koin.get()
                    val barcodeAnalyser = BarcodeAnalyser(
                        coroutineDispatcherProvider = screenViewModel.coroutineDispatcherProvider,
                        barcodeScanner = barcodeScanner,
                        dateTimeKit = screenViewModel.dateTimeKit,
                        logKit = screenViewModel.logKit,
                        onBarcodesDetected = { barcodes ->
                            barcodes.forEach { barcode ->
                                barcode.rawValue?.let { barcodeValue ->
                                    screenViewModel.logError(
                                        message = "BarcodeDomainModel value detected: $barcodeValue.",
                                    )

                                    screenViewModel.setIsScanning(false)
                                    BarcodeFormatDomainModel.fromValue(barcode.format)
                                        ?.let { barcodeFormat ->
                                            onBarcodeScanned(
                                                barcodeFormat,
                                                barcodeValue,
                                            )
                                        }
                                }
                            }
                        },
                    )
                    val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(
                                cameraExecutor,
                                barcodeAnalyser,
                            )
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

                    try {
                        awaitCancellation()
                    } finally {
                        processCameraProvider.unbindAll()
                    }
                }

                surfaceRequest?.let {
                    BarcodeScannerPreview(
                        surfaceRequest = it,
                    )
                }
            },
        )
    }
}
