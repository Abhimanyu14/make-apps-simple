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

package com.makeappssimple.abhimanyu.barcodes.android.core.database.converters

import com.google.common.truth.Truth.assertThat
import com.google.zxing.BarcodeFormat
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test

internal class BarcodeFormatConverterTest {
    private lateinit var barcodeFormatConverter: BarcodeFormatConverter

    @Before
    fun setUp() {
        barcodeFormatConverter = BarcodeFormatConverter()
    }

    @Test
    fun barcodeFormatToString_and_stringToBarcodeFormat_roundTrip() {
        val barcodeFormat = BarcodeFormat.QR_CODE

        val string = barcodeFormatConverter.barcodeFormatToString(
            barcodeFormat = barcodeFormat,
        )
        val result = barcodeFormatConverter.stringToBarcodeFormat(
            value = string,
        )

        assertThat(result).isEqualTo(barcodeFormat)
    }

    @Test
    fun stringToBarcodeFormat_validString_returnsBarcodeFormat() {
        val barcodeFormat = BarcodeFormat.CODE_128

        val string = Json.encodeToString(
            value = barcodeFormat,
        )
        val result = barcodeFormatConverter.stringToBarcodeFormat(
            value = string,
        )

        assertThat(result).isEqualTo(barcodeFormat)
    }

    @Test
    fun barcodeFormatToString_validBarcodeFormat_returnsString() {
        val barcodeFormat = BarcodeFormat.EAN_13

        val result = barcodeFormatConverter.barcodeFormatToString(
            barcodeFormat = barcodeFormat,
        )
        val expected = Json.encodeToString(
            value = barcodeFormat,
        )

        assertThat(result).isEqualTo(expected)
    }
} 
