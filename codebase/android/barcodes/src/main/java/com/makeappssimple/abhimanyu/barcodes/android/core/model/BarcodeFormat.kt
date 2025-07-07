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

package com.makeappssimple.abhimanyu.barcodes.android.core.model

/**
 * Sealed class representing supported barcode formats.
 */
internal sealed class BarcodeFormat(
    val value: Int,
) {
    object AllFormats : BarcodeFormat(0)
    object Code128 : BarcodeFormat(1)
    object Code39 : BarcodeFormat(2)
    object Code93 : BarcodeFormat(4)
    object Codabar : BarcodeFormat(8)
    object DataMatrix : BarcodeFormat(16)
    object Ean13 : BarcodeFormat(32)
    object Ean8 : BarcodeFormat(64)
    object Itf : BarcodeFormat(128)
    object QrCode : BarcodeFormat(256)
    object UpcA : BarcodeFormat(512)
    object UpcE : BarcodeFormat(1024)
    object Pdf417 : BarcodeFormat(2048)
    object Aztec : BarcodeFormat(4096)

    companion object {
        fun fromValue(
            value: Int,
        ): BarcodeFormat? {
            return when (value) {
                0 -> AllFormats
                1 -> Code128
                2 -> Code39
                4 -> Code93
                8 -> Codabar
                16 -> DataMatrix
                32 -> Ean13
                64 -> Ean8
                128 -> Itf
                256 -> QrCode
                512 -> UpcA
                1024 -> UpcE
                2048 -> Pdf417
                4096 -> Aztec
                else -> null
            }
        }
    }
}
