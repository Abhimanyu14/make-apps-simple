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

package com.makeappssimple.abhimanyu.barcodes.android.core.barcodegenerator

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.makeappssimple.abhimanyu.barcodes.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.barcodes.android.core.common.extensions.isNull
import kotlinx.coroutines.withContext

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
            )?.asImageBitmap()
        }
        return imageBitmap
    }

    private fun getBitMatrix(
        data: String,
        visionBarcodeFormat: Int,
        height: Int,
        width: Int
    ): BitMatrix? {
        val barcodeFormat = getZxingBarcodeFormat(
            visionBarcodeFormat = visionBarcodeFormat,
        )
        return try {
            MultiFormatWriter().encode(data, barcodeFormat, width, height, null)
        } catch (
            _: IllegalArgumentException,
        ) {
            null
        }
    }

    private fun getPixels(
        bitMatrix: BitMatrix,
        barcodeColor: Int,
        backgroundColor: Int
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
    ): Bitmap? {
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            .also {
                it.setPixels(pixels, 0, width, 0, 0, width, height)
            }
    }

    private fun getZxingBarcodeFormat(
        visionBarcodeFormat: Int,
    ): BarcodeFormat {
        when (visionBarcodeFormat) {
            1 -> {
                return BarcodeFormat.CODE_128
            }

            2 -> {
                return BarcodeFormat.CODE_39
            }

            4 -> {
                return BarcodeFormat.CODE_93
            }

            8 -> {
                return BarcodeFormat.CODABAR
            }

            16 -> {
                return BarcodeFormat.DATA_MATRIX
            }

            32 -> {
                return BarcodeFormat.EAN_13
            }

            64 -> {
                return BarcodeFormat.EAN_8
            }

            128 -> {
                return BarcodeFormat.ITF
            }

            256 -> {
                return BarcodeFormat.QR_CODE
            }

            512 -> {
                return BarcodeFormat.UPC_A
            }

            1024 -> {
                return BarcodeFormat.UPC_E
            }

            2048 -> {
                return BarcodeFormat.PDF_417
            }

            4096 -> {
                return BarcodeFormat.AZTEC
            }

            else -> {
                return BarcodeFormat.QR_CODE
            }
        }
    }

    private fun getBarcodeFormatInt(
        barcodeFormat: BarcodeFormat,
    ): Int {
        when (barcodeFormat) {
            BarcodeFormat.CODE_128 -> {
                return 1
            }

            BarcodeFormat.CODE_39 -> {
                return 2
            }

            BarcodeFormat.CODE_93 -> {
                return 4
            }

            BarcodeFormat.CODABAR -> {
                return 8
            }

            BarcodeFormat.DATA_MATRIX -> {
                return 16
            }

            BarcodeFormat.EAN_13 -> {
                return 32
            }

            BarcodeFormat.EAN_8 -> {
                return 64
            }

            BarcodeFormat.ITF -> {
                return 128
            }

            BarcodeFormat.QR_CODE -> {
                return 256
            }

            BarcodeFormat.UPC_A -> {
                return 512
            }

            BarcodeFormat.UPC_E -> {
                return 1024
            }

            BarcodeFormat.PDF_417 -> {
                return 2048
            }

            BarcodeFormat.AZTEC -> {
                return 4096
            }

            BarcodeFormat.MAXICODE -> {
            }

            BarcodeFormat.RSS_14 -> {
            }

            BarcodeFormat.RSS_EXPANDED -> {
            }

            BarcodeFormat.UPC_EAN_EXTENSION -> {
            }
        }

        return 256
    }
}
