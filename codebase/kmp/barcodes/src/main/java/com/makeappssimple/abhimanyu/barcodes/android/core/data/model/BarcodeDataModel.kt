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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makeappssimple.abhimanyu.barcodes.android.core.data.database.constants.BarcodeEntityConstants

/**
 * Room entity for barcode_table.
 */
@Entity(tableName = BarcodeEntityConstants.TABLE_NAME)
internal actual data class BarcodeDataModel actual constructor(
    @ColumnInfo(name = BarcodeEntityConstants.COLUMN_SOURCE)
    actual val source: BarcodeSourceDataModel,

    @ColumnInfo(name = BarcodeEntityConstants.COLUMN_FORMAT)
    actual val format: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BarcodeEntityConstants.COLUMN_ID)
    actual val id: Int,

    @ColumnInfo(name = BarcodeEntityConstants.COLUMN_TIMESTAMP)
    actual val timestamp: Long,

    @ColumnInfo(name = BarcodeEntityConstants.COLUMN_NAME)
    actual val name: String?,

    @ColumnInfo(name = BarcodeEntityConstants.COLUMN_VALUE)
    actual val value: String,
)
