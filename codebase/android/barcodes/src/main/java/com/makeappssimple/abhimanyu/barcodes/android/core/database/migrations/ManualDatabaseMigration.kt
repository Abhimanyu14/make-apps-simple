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

package com.makeappssimple.abhimanyu.barcodes.android.core.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.makeappssimple.abhimanyu.barcodes.android.core.database.model.BarcodeEntityConstants

internal val manualDatabaseMigrations: Array<Migration> = arrayOf(
)

private object ManualDatabaseMigration {
    /**
     * New Column added
     * Table - [BarcodeEntityConstants.TABLE_NAME]
     * Column - [BarcodeEntityConstants.COLUMN_TIMESTAMP]
     *
     * Required field
     * Set data for existing records to current timestamp when migrating
     */
    val MIGRATION_19_20 = object : Migration(19, 20) {
        override fun migrate(
            db: SupportSQLiteDatabase,
        ) {
            // Add column with a default value
            db.execSQL(
                """
                ALTER TABLE barcode_table 
                ADD COLUMN `timestamp` INTEGER NOT NULL
            """.trimIndent()
            )

            // Update data
            db.execSQL(
                """
                UPDATE account_table 
                SET `minimum_account_balance_amount` = CASE type 
                WHEN 'BANK' THEN '{"currency":"INR","value":0}' 
                ELSE NULL
                END
            """.trimIndent()
            )
        }
    }

}
