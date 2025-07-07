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

package com.makeappssimple.abhimanyu.barcodes.android.core.database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.makeappssimple.abhimanyu.barcodes.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.barcodes.android.core.database.constants.DatabaseConstants
import com.makeappssimple.abhimanyu.barcodes.android.core.database.converters.BarcodeFormatConverter
import com.makeappssimple.abhimanyu.barcodes.android.core.database.dao.BarcodeDao
import com.makeappssimple.abhimanyu.barcodes.android.core.database.migrations.manualDatabaseMigrations
import com.makeappssimple.abhimanyu.barcodes.android.core.database.model.BarcodeEntity

@Database(
    version = DatabaseConstants.DATABASE_CURRENT_VERSION_NUMBER,
    entities = [
        BarcodeEntity::class,
    ],
    autoMigrations = [
    ],
    exportSchema = true,
)
@TypeConverters(
    BarcodeFormatConverter::class,
)
public abstract class MyRoomDatabase : RoomDatabase() {
    public abstract fun barcodeDao(): BarcodeDao

    public companion object {
        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        public fun getDatabase(
            context: Context,
        ): MyRoomDatabase {
            val tempInstance: MyRoomDatabase? = INSTANCE
            if (tempInstance.isNotNull()) {
                return tempInstance
            }
            return INSTANCE ?: synchronized(
                lock = this,
            ) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(
            context: Context,
        ): MyRoomDatabase {
            return Room
                .databaseBuilder(
                    context = context.applicationContext,
                    klass = MyRoomDatabase::class.java,
                    name = DatabaseConstants.DATABASE_NAME,
                )
                .addMigrations(
                    migrations = manualDatabaseMigrations,
                )
                .build()
        }
    }
}
