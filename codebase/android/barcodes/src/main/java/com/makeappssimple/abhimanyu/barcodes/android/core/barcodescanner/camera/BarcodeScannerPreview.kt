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

package com.makeappssimple.abhimanyu.barcodes.android.core.barcodescanner.camera

import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.makeappssimple.abhimanyu.barcodes.android.core.barcodescanner.barcodescanner.BarcodeAnalyser
import com.makeappssimple.abhimanyu.barcodes.android.core.common.datetime.DateTimeKitImpl
import com.makeappssimple.abhimanyu.barcodes.android.core.logger.LocalLogKit
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.model.BarcodeSource
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
public fun BarcodeScannerPreview(
    modifier: Modifier = Modifier,
    onBarcodeScanned: (barcode: Barcode) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val myLogger = LocalLogKit.current

    AndroidView(
        factory = { androidViewContext ->
            PreviewView(androidViewContext).apply {
                scaleType = PreviewView.ScaleType.FILL_CENTER
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                // Preview is incorrectly scaled in Compose on some devices without this
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }
        },
        update = { previewView ->
            val cameraSelector: CameraSelector =
                CameraSelector.DEFAULT_BACK_CAMERA
            val cameraExecutor: ExecutorService =
                Executors.newSingleThreadExecutor()
            val cameraProviderFuture =
                ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                val preview: Preview = Preview.Builder()
                    .build()
                    .also {
                        // Attach the viewfinder's surface provider to preview use case
                        it.surfaceProvider = previewView.surfaceProvider
                    }
                val cameraProvider: ProcessCameraProvider =
                    cameraProviderFuture.get()
                val barcodeAnalyser = BarcodeAnalyser(
                    dateTimeKit = DateTimeKitImpl(), // TODO(Abhi): Inject this
                    logKit = myLogger,
                ) { barcodes ->
                    barcodes.forEach { barcode ->
                        barcode.rawValue?.let { barcodeValue ->
                            myLogger.logError(
                                message = "Barcode value detected: ${barcodeValue}.",
                            )
                            cameraProvider.unbindAll()

                            onBarcodeScanned(
                                Barcode(
                                    source = BarcodeSource.SCANNED,
                                    format = barcode.format,
                                    timestamp = System.currentTimeMillis(), // TODO(Abhi): Inject this to support testing
                                    value = barcodeValue,
                                ),
                            )
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
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                } catch (
                    exception: Exception,
                ) {
                    myLogger.logError(
                        message = "Use case binding failed with exception : $exception",
                    )
                }
            }, ContextCompat.getMainExecutor(context))
        },
        modifier = modifier
            .fillMaxSize(),
    )
}
