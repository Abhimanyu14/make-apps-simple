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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.constants.DatabaseConstants
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.converters.AmountConverter
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.converters.CategoryConverter
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.converters.IntListConverter
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.AccountDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.TransactionDataDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.migrations.manualDatabaseMigrations
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.AccountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.TransactionForEntity
import java.util.concurrent.Executors

@Database(
    version = DatabaseConstants.DATABASE_CURRENT_VERSION_NUMBER,
    entities = [
        AccountEntity::class,
        CategoryEntity::class,
        TransactionEntity::class,
        TransactionForEntity::class,
    ],
    autoMigrations = [],
    exportSchema = true,
)
@TypeConverters(
    AmountConverter::class,
    IntListConverter::class,
    CategoryConverter::class,
)
internal abstract class FinanceManagerRoomDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun transactionDataDao(): TransactionDataDao
    abstract fun transactionForDao(): TransactionForDao

    companion object {
        @Volatile
        private var instance: FinanceManagerRoomDatabase? = null

        /**
         * Reference
         * https://github.com/android/app-actions-samples/blob/fea0f48a6d7f1c43d47c3ad14bfd11ace4b5629c/fitness-biis/starter/app/src/main/java/com/devrel/android/fitactions/model/FitDatabase.kt#L34
         */
        internal fun getDatabase(
            context: Context,
            initialDatabasePopulator: InitialDatabasePopulator? = null,
        ): FinanceManagerRoomDatabase {
            val tempInstance: FinanceManagerRoomDatabase? = instance
            if (tempInstance.isNotNull()) {
                return tempInstance
            }
            return instance ?: synchronized(
                lock = this,
            ) {
                instance ?: buildDatabase(
                    context = context,
                    initialDatabasePopulator = initialDatabasePopulator
                ).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(
            context: Context,
            initialDatabasePopulator: InitialDatabasePopulator?,
        ): FinanceManagerRoomDatabase {
            val roomDatabaseCallback: Callback = object : Callback() {
                override fun onCreate(
                    db: SupportSQLiteDatabase,
                ) {
                    // do something after database has been created
                }

                override fun onOpen(
                    db: SupportSQLiteDatabase,
                ) {
                    /*
                    // TODO-Abhi: Using Work Manager
                    val oneTimeWorkRequest: OneTimeWorkRequest =
                        OneTimeWorkRequestBuilder<InitialDatabasePopulationWorker>()
                            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                            .build()
                    WorkManager
                        .getInstance(context)
                        .enqueue(oneTimeWorkRequest)
                    */

                    // do something every time database is open
                    Executors
                        .newSingleThreadScheduledExecutor()
                        .execute {
                            val myRoomDatabase = getDatabase(
                                context = context,
                            )
                            initialDatabasePopulator?.populateInitialDatabaseData(
                                financeManagerRoomDatabase = myRoomDatabase,
                            )
                        }
                }
            }

            return Room
                .databaseBuilder(
                    context = context.applicationContext,
                    klass = FinanceManagerRoomDatabase::class.java,
                    name = DatabaseConstants.DATABASE_NAME,
                )
                .addMigrations(
                    migrations = manualDatabaseMigrations,
                )
                .addCallback(
                    callback = roomDatabaseCallback,
                )
                .build()
        }
    }
}
