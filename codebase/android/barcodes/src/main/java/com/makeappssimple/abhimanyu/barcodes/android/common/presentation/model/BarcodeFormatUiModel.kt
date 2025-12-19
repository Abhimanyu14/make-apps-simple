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

package com.makeappssimple.abhimanyu.barcodes.android.common.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed class BarcodeFormatUiModel(
    val value: Int,
) {
    object AllFormats : BarcodeFormatUiModel(0)
    object Code128 : BarcodeFormatUiModel(1)
    object Code39 : BarcodeFormatUiModel(2)
    object Code93 : BarcodeFormatUiModel(4)
    object Codabar : BarcodeFormatUiModel(8)
    object DataMatrix : BarcodeFormatUiModel(16)
    object Ean13 : BarcodeFormatUiModel(32)
    object Ean8 : BarcodeFormatUiModel(64)
    object Itf : BarcodeFormatUiModel(128)
    object QrCode : BarcodeFormatUiModel(256)
    object UpcA : BarcodeFormatUiModel(512)
    object UpcE : BarcodeFormatUiModel(1024)
    object Pdf417 : BarcodeFormatUiModel(2048)
    object Aztec : BarcodeFormatUiModel(4096)

    companion object Companion {
        fun fromValue(
            value: Int,
        ): BarcodeFormatUiModel? {
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
