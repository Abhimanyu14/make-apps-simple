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

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Barcode(
    @SerialName(value = BarcodeConstants.SOURCE)
    public val source: BarcodeSource,

    @SerialName(value = BarcodeConstants.FORMAT)
    public val format: Int = BarcodeFormat.QrCode.value,

    @SerialName(value = BarcodeConstants.ID)
    public val id: Int = 0,

    @SerialName(value = BarcodeConstants.TIMESTAMP)
    public val timestamp: Long,

    @SerialName(value = BarcodeConstants.NAME)
    public val name: String? = null,

    @SerialName(value = BarcodeConstants.VALUE)
    public val value: String,
)

private object BarcodeConstants {
    const val SOURCE: String = "source"
    const val FORMAT: String = "format"
    const val ID: String = "id"
    const val TIMESTAMP: String = "timestamp"
    const val NAME: String = "name"
    const val VALUE: String = "value"
}
