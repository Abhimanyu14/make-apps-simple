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

package com.makeappssimple.abhimanyu.barcodes.android.core.data.model

internal expect class BarcodeDataModel(
    source: BarcodeSourceDataModel,
    format: Int = BarcodeFormatDataModel.QrCode.value,
    id: Int = 0,
    timestamp: Long,
    name: String? = null,
    value: String,
) {
    val source: BarcodeSourceDataModel
    val format: Int
    val id: Int
    val timestamp: Long
    val name: String?
    val value: String
}
