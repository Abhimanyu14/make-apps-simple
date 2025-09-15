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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.local.database

import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.common.core.json_reader.JsonReaderKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.constants.FinanceManagerAppConstants
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.model.InitialDatabaseData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.util.sanitizeTransactions
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.datastore.FinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.InitialDataVersionNumber
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException

public interface InitialDatabasePopulator {
    public fun populateInitialDatabaseData(
        financeManagerRoomDatabase: FinanceManagerRoomDatabase,
    )
}

internal class InitialDatabasePopulatorImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val jsonReaderKit: JsonReaderKit,
    private val financeManagerPreferencesDataSource: FinanceManagerPreferencesDataSource,
) : InitialDatabasePopulator {
    override fun populateInitialDatabaseData(
        financeManagerRoomDatabase: FinanceManagerRoomDatabase,
    ) {
        // TODO(Abhi) - Change to use Work Manager
        // Reference - https://github.com/android/sunflower/blob/d7df7cb74b82a0c12064bca31acc2332e78c7c73/app/src/main/java/com/google/samples/apps/sunflower/data/AppDatabase.kt#L38
        financeManagerRoomDatabase.runInTransaction {
            CoroutineScope(
                context = dispatcherProvider.io + SupervisorJob(),
            ).launch {
                try {
                    val jsonString = jsonReaderKit.readJsonFromAssets(
                        fileName = FinanceManagerAppConstants.INITIAL_DATA_FILE_NAME,
                    ) ?: throw FileNotFoundException()
                    val initialDatabaseData =
                        Json.decodeFromString<InitialDatabaseData>(
                            string = jsonString,
                        )
                    val initialDataVersionNumber: InitialDataVersionNumber? =
                        financeManagerPreferencesDataSource.getInitialDataVersionNumber()
                            .first()

                    launch {
                        populateAccountsData(
                            initialDatabaseData = initialDatabaseData,
                            accountsInitialDataVersionNumber = initialDataVersionNumber?.account.orZero(),
                            financeManagerRoomDatabase = financeManagerRoomDatabase,
                        )
                    }
                    launch {
                        populateCategoriesData(
                            initialDatabaseData = initialDatabaseData,
                            categoriesInitialDataVersionNumber = initialDataVersionNumber?.category.orZero(),
                            financeManagerRoomDatabase = financeManagerRoomDatabase,
                        )
                    }
                    launch {
                        populateTransactionForValuesData(
                            initialDatabaseData = initialDatabaseData,
                            transactionForValuesInitialDataVersionNumber = initialDataVersionNumber?.transactionFor.orZero(),
                            financeManagerRoomDatabase = financeManagerRoomDatabase,
                        )
                    }
                    launch {
                        transactionsCleanUpIfRequired(
                            transactionsInitialDataVersionNumber = initialDataVersionNumber?.transaction.orZero(),
                            financeManagerRoomDatabase = financeManagerRoomDatabase,
                        )
                    }
                } catch (
                    fileNotFoundException: FileNotFoundException,
                ) {
                    fileNotFoundException.printStackTrace()
                } catch (
                    serializationException: SerializationException,
                ) {
                    serializationException.printStackTrace()
                } catch (
                    illegalArgumentException: IllegalArgumentException,
                ) {
                    illegalArgumentException.printStackTrace()
                }
            }
        }
    }

    private suspend fun populateAccountsData(
        initialDatabaseData: InitialDatabaseData,
        accountsInitialDataVersionNumber: Int,
        financeManagerRoomDatabase: FinanceManagerRoomDatabase,
    ) {
        if (accountsInitialDataVersionNumber < initialDatabaseData.defaultAccounts.versionNumber) {
            val accountDao = financeManagerRoomDatabase.accountDao()
            initialDatabaseData.defaultAccounts.versionedAccounts
                .filter {
                    it.versionNumber > accountsInitialDataVersionNumber
                }
                .forEach {
                    accountDao.insertAccounts(
                        accounts = it.accountsData.toTypedArray(),
                    )
                }
            financeManagerPreferencesDataSource.updateAccountDataVersionNumber(
                accountDataVersionNumber = initialDatabaseData.defaultAccounts.versionNumber,
            )
        }
    }

    private suspend fun populateCategoriesData(
        initialDatabaseData: InitialDatabaseData,
        categoriesInitialDataVersionNumber: Int,
        financeManagerRoomDatabase: FinanceManagerRoomDatabase,
    ) {
        if (categoriesInitialDataVersionNumber < initialDatabaseData.defaultCategories.versionNumber) {
            val categoryDao = financeManagerRoomDatabase.categoryDao()
            initialDatabaseData.defaultCategories.versionedCategories
                .filter {
                    it.versionNumber > categoriesInitialDataVersionNumber
                }
                .forEach {
                    categoryDao.insertCategories(
                        categories = it.categoriesData.toTypedArray(),
                    )
                }
            financeManagerPreferencesDataSource.updateCategoryDataVersionNumber(
                categoryDataVersionNumber = initialDatabaseData.defaultCategories.versionNumber,
            )
        }
    }

    private suspend fun populateTransactionForValuesData(
        initialDatabaseData: InitialDatabaseData,
        transactionForValuesInitialDataVersionNumber: Int,
        financeManagerRoomDatabase: FinanceManagerRoomDatabase,
    ) {
        if (transactionForValuesInitialDataVersionNumber < initialDatabaseData.defaultTransactionForValues.versionNumber) {
            val transactionForDao =
                financeManagerRoomDatabase.transactionForDao()
            initialDatabaseData.defaultTransactionForValues.versionedTransactionForValues
                .filter {
                    it.versionNumber > transactionForValuesInitialDataVersionNumber
                }
                .forEach {
                    transactionForDao.insertTransactionForValues(
                        transactionForValues = it.transactionForValuesData.toTypedArray(),
                    )
                }
            financeManagerPreferencesDataSource.updateTransactionForDataVersionNumber(
                transactionForDataVersionNumber = initialDatabaseData.defaultTransactionForValues.versionNumber,
            )
        }
    }

    private suspend fun transactionsCleanUpIfRequired(
        transactionsInitialDataVersionNumber: Int,
        financeManagerRoomDatabase: FinanceManagerRoomDatabase,
    ) {
        val currentTransactionsDataVersion = 1
        if (transactionsInitialDataVersionNumber < currentTransactionsDataVersion) {
            val transactionDao = financeManagerRoomDatabase.transactionDao()
            val transactions = transactionDao.getAllTransactionsFlow().first()
                .toImmutableList()
            transactionDao.deleteAllTransactions()
            transactionDao.insertTransactions(
                transactions = sanitizeTransactions(
                    transactions = transactions,
                ).toTypedArray()
            )
            financeManagerPreferencesDataSource.updateTransactionDataVersionNumber(
                transactionDataVersionNumber = currentTransactionsDataVersion,
            )
        }
    }
}
