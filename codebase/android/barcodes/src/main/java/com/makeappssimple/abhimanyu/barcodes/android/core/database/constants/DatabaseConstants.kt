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

package com.makeappssimple.abhimanyu.barcodes.android.core.database.constants

/**
 * General database constants.
 */
internal object DatabaseConstants {
    const val DATABASE_CURRENT_VERSION_NUMBER: Int = 1
    const val DATABASE_NAME: String = "barcodes_database"
}

/**
 * Constants for the BarcodeEntity Room table and columns.
 */
internal object BarcodeEntityConstants {
    const val COLUMN_FORMAT: String = "format"
    const val COLUMN_ID: String = "id"
    const val COLUMN_NAME: String = "name"
    const val COLUMN_SOURCE: String = "source"
    const val COLUMN_TIMESTAMP: String = "timestamp"
    const val COLUMN_VALUE: String = "value"
    const val TABLE_NAME: String = "barcode_table"
}
