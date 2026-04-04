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

package com.makeappssimple.abhimanyu.barcode.generator.android.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed class BarcodeFormatModel(
    val value: Int,
) {
    object AllFormats : BarcodeFormatModel(
        value = 0,
    )

    object Code128 : BarcodeFormatModel(
        value = 1,
    )

    object Code39 : BarcodeFormatModel(
        value = 2,
    )

    object Code93 : BarcodeFormatModel(
        value = 4,
    )

    object Codabar : BarcodeFormatModel(
        value = 8,
    )

    object DataMatrix : BarcodeFormatModel(
        value = 16,
    )

    object Ean13 : BarcodeFormatModel(
        value = 32,
    )

    object Ean8 : BarcodeFormatModel(
        value = 64,
    )

    object Itf : BarcodeFormatModel(
        value = 128,
    )

    object QrCode : BarcodeFormatModel(
        value = 256,
    )

    object UpcA : BarcodeFormatModel(
        value = 512,
    )

    object UpcE : BarcodeFormatModel(
        value = 1024,
    )

    object Pdf417 : BarcodeFormatModel(
        value = 2048,
    )

    object Aztec : BarcodeFormatModel(
        value = 4096,
    )

    companion object Companion {
        fun fromValue(
            value: Int,
        ): BarcodeFormatModel? {
            return when (value) {
                0 -> {
                    AllFormats
                }

                1 -> {
                    Code128
                }

                2 -> {
                    Code39
                }

                4 -> {
                    Code93
                }

                8 -> {
                    Codabar
                }

                16 -> {
                    DataMatrix
                }

                32 -> {
                    Ean13
                }

                64 -> {
                    Ean8
                }

                128 -> {
                    Itf
                }

                256 -> {
                    QrCode
                }

                512 -> {
                    UpcA
                }

                1024 -> {
                    UpcE
                }

                2048 -> {
                    Pdf417
                }

                4096 -> {
                    Aztec
                }

                else -> {
                    null
                }
            }
        }
    }
}
