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

package com.makeappssimple.abhimanyu.barcode.generator.android

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.makeappssimple.abhimanyu.barcode.generator.android.model.BarcodeFormatModel
import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(
    binds = [
        BarcodeGenerator::class,
    ],
)
internal class BarcodeGeneratorImpl(
    private val dispatcherProvider: DispatcherProvider,
) : BarcodeGenerator {
    override suspend fun generateBarcode(
        data: String,
        visionBarcodeFormat: Int,
        width: Int,
        height: Int,
        barcodeColor: Int,
        backgroundColor: Int,
    ): ImageBitmap? {
        var imageBitmap: ImageBitmap? = null
        withContext(
            context = dispatcherProvider.default,
        ) {
            val bitMatrix: BitMatrix? = getBitMatrix(
                data = data,
                visionBarcodeFormat = visionBarcodeFormat,
                width = width,
                height = height,
            )
            if (bitMatrix.isNull()) {
                return@withContext
            }
            val pixels = getPixels(
                bitMatrix = bitMatrix,
                barcodeColor = barcodeColor,
                backgroundColor = backgroundColor,
            )
            imageBitmap = createBitmap(
                width = bitMatrix.width,
                height = bitMatrix.height,
                pixels = pixels,
            ).asImageBitmap()
        }
        return imageBitmap
    }

    private fun getBitMatrix(
        data: String,
        visionBarcodeFormat: Int,
        height: Int,
        width: Int,
    ): BitMatrix? {
        val barcodeFormat = getZxingBarcodeFormat(
            visionBarcodeFormat = visionBarcodeFormat,
        )
        return try {
            MultiFormatWriter().encode(
                data,
                barcodeFormat,
                width,
                height,
                null
            )
        } catch (
            _: IllegalArgumentException,
        ) {
            null
        }
    }

    private fun getPixels(
        bitMatrix: BitMatrix,
        barcodeColor: Int,
        backgroundColor: Int,
    ): IntArray {
        val bitMatrixWidth = bitMatrix.width
        val bitMatrixHeight = bitMatrix.height
        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)
        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth
            for (x in 0 until bitMatrixWidth) {
                pixels[offset + x] = if (bitMatrix[x, y]) {
                    barcodeColor
                } else {
                    backgroundColor
                }
            }
        }
        return pixels
    }

    private fun createBitmap(
        width: Int,
        height: Int,
        pixels: IntArray,
    ): Bitmap {
        return Bitmap.createBitmap(
            width,
            height,
            Bitmap.Config.ARGB_8888
        )
            .also {
                it.setPixels(
                    pixels,
                    0,
                    width,
                    0,
                    0,
                    width,
                    height
                )
            }
    }

    private fun getZxingBarcodeFormat(
        visionBarcodeFormat: Int,
    ): BarcodeFormat {
        return when (visionBarcodeFormat) {
            BarcodeFormatModel.Code128.value -> {
                BarcodeFormat.CODE_128
            }

            BarcodeFormatModel.Code39.value -> {
                BarcodeFormat.CODE_39
            }

            BarcodeFormatModel.Code93.value -> {
                BarcodeFormat.CODE_93
            }

            BarcodeFormatModel.Codabar.value -> {
                BarcodeFormat.CODABAR
            }

            BarcodeFormatModel.DataMatrix.value -> {
                BarcodeFormat.DATA_MATRIX
            }

            BarcodeFormatModel.Ean13.value -> {
                BarcodeFormat.EAN_13
            }

            BarcodeFormatModel.Ean8.value -> {
                BarcodeFormat.EAN_8
            }

            BarcodeFormatModel.Itf.value -> {
                BarcodeFormat.ITF
            }

            BarcodeFormatModel.QrCode.value -> {
                BarcodeFormat.QR_CODE
            }

            BarcodeFormatModel.UpcA.value -> {
                BarcodeFormat.UPC_A
            }

            BarcodeFormatModel.UpcE.value -> {
                BarcodeFormat.UPC_E
            }

            BarcodeFormatModel.Pdf417.value -> {
                BarcodeFormat.PDF_417
            }

            BarcodeFormatModel.Aztec.value -> {
                BarcodeFormat.AZTEC
            }

            else -> {
                BarcodeFormat.QR_CODE
            }
        }
    }
}
