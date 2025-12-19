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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makeappssimple.abhimanyu.barcodes.android.common.data.database.constants.BarcodeEntityConstants

/**
 * Room entity for barcode_table.
 */
@Entity(tableName = BarcodeEntityConstants.TABLE_NAME)
internal data class BarcodeDataModel(
    @ColumnInfo(name = BarcodeEntityConstants.COLUMN_SOURCE)
    val source: BarcodeSourceDataModel,

    @ColumnInfo(name = BarcodeEntityConstants.COLUMN_FORMAT)
    val format: Int = BarcodeFormatDataModel.QrCode.value,

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
