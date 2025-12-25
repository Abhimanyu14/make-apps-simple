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

package com.makeappssimple.abhimanyu.barcodes.android.common.ui.barcode_scanner.barcode_scanner

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class BarcodeAnalyser(
    private val dispatcherProvider: DispatcherProvider,
    private val getCurrentTimeMillis: () -> Long,
    private val logError: (message: String) -> Unit,
    private val onBarcodesDetected: (barcodes: List<Barcode>) -> Unit,
) : ImageAnalysis.Analyzer {
    private var currentTimestamp: Long = 0
    private val barcodeScannerOptions: BarcodeScannerOptions =
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
    private val barcodeScanner: BarcodeScanner =
        BarcodeScanning.getClient(barcodeScannerOptions)

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(
        imageProxy: ImageProxy,
    ) {
        logError("Inside analyze")

        currentTimestamp = getCurrentTimeMillis()
        imageProxy.image?.let { imageToAnalyze ->
            val imageToProcess = InputImage.fromMediaImage(
                imageToAnalyze,
                imageProxy.imageInfo.rotationDegrees,
            )
            barcodeScanner.process(imageToProcess)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        logError("Scanned: $barcodes")
                        onBarcodesDetected(barcodes)
                    } else {
                        logError("No barcode scanned")
                    }
                }
                .addOnFailureListener { exception ->
                    logError("BarcodeAnalyser: Something went wrong with exception: $exception")
                }
                .addOnCompleteListener {
                    CoroutineScope(
                        context = dispatcherProvider.io,
                    ).launch {
                        delay(
                            timeMillis = 1000 - (getCurrentTimeMillis() - currentTimestamp),
                        )
                        imageProxy.close()
                    }
                }
        }
    }
}
