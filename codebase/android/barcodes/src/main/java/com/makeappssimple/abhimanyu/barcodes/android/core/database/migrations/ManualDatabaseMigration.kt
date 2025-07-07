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
