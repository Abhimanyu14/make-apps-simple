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

package com.makeappssimple.abhimanyu.barcodes.android.common.data.model

internal sealed class BarcodeFormatDataModel(
    val value: Int,
) {
    object AllFormats : BarcodeFormatDataModel(0)
    object Code128 : BarcodeFormatDataModel(1)
    object Code39 : BarcodeFormatDataModel(2)
    object Code93 : BarcodeFormatDataModel(4)
    object Codabar : BarcodeFormatDataModel(8)
    object DataMatrix : BarcodeFormatDataModel(16)
    object Ean13 : BarcodeFormatDataModel(32)
    object Ean8 : BarcodeFormatDataModel(64)
    object Itf : BarcodeFormatDataModel(128)
    object QrCode : BarcodeFormatDataModel(256)
    object UpcA : BarcodeFormatDataModel(512)
    object UpcE : BarcodeFormatDataModel(1024)
    object Pdf417 : BarcodeFormatDataModel(2048)
    object Aztec : BarcodeFormatDataModel(4096)

    companion object Companion {
        fun fromValue(
            value: Int,
        ): BarcodeFormatDataModel? {
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
