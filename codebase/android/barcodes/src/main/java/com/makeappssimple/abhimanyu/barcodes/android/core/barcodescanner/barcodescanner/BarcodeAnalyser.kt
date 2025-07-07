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

package com.makeappssimple.abhimanyu.barcodes.android.core.barcodescanner.barcodescanner

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.makeappssimple.abhimanyu.barcodes.android.core.logger.LogKit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class BarcodeAnalyser(
    private val logKit: LogKit,
    private val onBarcodesDetected: (barcodes: List<Barcode>) -> Unit,
) : ImageAnalysis.Analyzer {
    private var currentTimestamp: Long = 0
    private val barcodeScannerOptions: BarcodeScannerOptions =
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
    private val barcodeScanner: BarcodeScanner =
        BarcodeScanning.getClient(barcodeScannerOptions)

    override fun analyze(
        imageProxy: ImageProxy,
    ) {
        logKit.logError(
            message = "Inside analyze",
        )

        currentTimestamp = System.currentTimeMillis()
        imageProxy.image?.let { imageToAnalyze ->
            val imageToProcess = InputImage.fromMediaImage(
                imageToAnalyze,
                imageProxy.imageInfo.rotationDegrees,
            )
            barcodeScanner.process(imageToProcess)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        logKit.logError(
                            message = "Scanned: $barcodes",
                        )
                        onBarcodesDetected(barcodes)
                    } else {
                        logKit.logError(
                            message = "No barcode scanned",
                        )
                    }
                }
                .addOnFailureListener { exception ->
                    logKit.logError(
                        message = "BarcodeAnalyser: Something went wrong with exception: $exception",
                    )
                }
                .addOnCompleteListener {
                    CoroutineScope(Dispatchers.IO).launch { // TODO(Abhi) - Inject this dispatcher
                        delay(1000 - (System.currentTimeMillis() - currentTimestamp))
                        imageProxy.close()
                    }
                }
        }
    }
}
