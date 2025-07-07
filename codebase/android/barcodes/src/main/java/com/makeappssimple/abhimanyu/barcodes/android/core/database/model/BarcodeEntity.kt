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

package com.makeappssimple.abhimanyu.barcodes.android.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.model.BarcodeSource
import com.google.android.gms.vision.barcode.Barcode as VisionBarcode

@Entity(tableName = BarcodeEntityConstants.TABLE_NAME)
public data class BarcodeEntity(
    @ColumnInfo(name = BarcodeEntityConstants.COLUMN_SOURCE)
    val source: BarcodeSource,

    @ColumnInfo(name = BarcodeEntityConstants.COLUMN_FORMAT)
    val format: Int = VisionBarcode.QR_CODE,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BarcodeEntityConstants.COLUMN_ID)
    val id: Int = 0,

    @ColumnInfo(name = BarcodeEntityConstants.COLUMN_TIMESTAMP)
    val timestamp: Long,

    @ColumnInfo(name = BarcodeEntityConstants.COLUMN_NAME)
    val name: String? = null,

    @ColumnInfo(name = BarcodeEntityConstants.COLUMN_VALUE)
    val value: String,
)

internal fun BarcodeEntity.asExternalModel(): Barcode {
    return Barcode(
        source = source,
        format = format,
        id = id,
        timestamp = timestamp,
        name = name,
        value = value,
    )
}

internal fun Barcode.asEntity(): BarcodeEntity {
    return BarcodeEntity(
        source = source,
        format = format,
        id = id,
        timestamp = timestamp,
        name = name,
        value = value,
    )
}

internal object BarcodeEntityConstants {
    const val TABLE_NAME: String = "barcode_table"

    const val COLUMN_SOURCE: String = "source"
    const val COLUMN_FORMAT: String = "format"
    const val COLUMN_ID: String = "id"
    const val COLUMN_TIMESTAMP: String = "timestamp"
    const val COLUMN_NAME: String = "name"
    const val COLUMN_VALUE: String = "value"
}
