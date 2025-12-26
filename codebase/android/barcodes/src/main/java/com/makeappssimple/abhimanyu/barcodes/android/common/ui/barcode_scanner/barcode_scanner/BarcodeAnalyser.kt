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
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val MINIMUM_ANALYSIS_INTERVAL_MS = 1000L

internal class BarcodeAnalyser(
    dispatcherProvider: DispatcherProvider,
    private val barcodeScanner: BarcodeScanner,
    private val dateTimeKit: DateTimeKit,
    private val logKit: LogKit,
    private val onBarcodesDetected: (barcodes: List<Barcode>) -> Unit,
) : ImageAnalysis.Analyzer {
    private var currentTimestamp: Long = 0
    private val coroutineScope: CoroutineScope = CoroutineScope(
        context = SupervisorJob() + dispatcherProvider.io,
    )

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(
        imageProxy: ImageProxy,
    ) {
        val imageToAnalyze = imageProxy.image
        if (imageToAnalyze == null) {
            imageProxy.close()
            return
        }

        currentTimestamp = dateTimeKit.getCurrentTimeMillis()
        val imageToProcess = InputImage.fromMediaImage(
            imageToAnalyze,
            imageProxy.imageInfo.rotationDegrees,
        )

        barcodeScanner.process(imageToProcess)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isNotEmpty()) {
                    onBarcodesDetected(barcodes)
                }
            }
            .addOnFailureListener { exception ->
                logKit.logError(
                    message = "BarcodeAnalyser: Failed to process image with exception: $exception",
                )
            }
            .addOnCompleteListener {
                closeImageProxyAfterDelay(
                    imageProxy = imageProxy,
                )
            }
    }

    private fun closeImageProxyAfterDelay(
        imageProxy: ImageProxy,
    ) {
        coroutineScope.launch {
            val elapsedTime =
                dateTimeKit.getCurrentTimeMillis() - currentTimestamp
            val remainingDelay = MINIMUM_ANALYSIS_INTERVAL_MS - elapsedTime
            if (remainingDelay > 0) {
                delay(
                    timeMillis = remainingDelay,
                )
            }
            imageProxy.close()
        }
    }
}
