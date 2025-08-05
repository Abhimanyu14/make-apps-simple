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

package com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.common

import android.net.Uri
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.jsonreader.JsonReaderKit
import com.makeappssimple.abhimanyu.common.logger.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.model.BackupData
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.model.DatabaseData
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.model.DatastoreData
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.util.sanitizeAccounts
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.util.sanitizeTransactions
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Transaction
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json

public class RestoreDataUseCase(
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val jsonReaderKit: JsonReaderKit,
    private val logKit: LogKit,
    private val transactionRepository: TransactionRepository,
) {
    public suspend operator fun invoke(
        uri: Uri,
    ): Boolean {
        val jsonString = jsonReaderKit.readJsonFromFile(
            uri = uri,
        )
        if (jsonString.isNull()) {
            logKit.logError(
                message = "Restore Data: Error reading file",
            )
            return false
        }

        val backupData = Json.decodeFromString<BackupData>(
            string = jsonString,
        )
        if (backupData.databaseData.isNull()) {
            logKit.logError(
                message = "Restore Data: Error in file database data",
            )
            return false
        }
        if (backupData.datastoreData.isNull()) {
            logKit.logError(
                message = "Restore Data: Error in file datastore data",
            )
            return false
        }

        return restoreDatabaseData(
            databaseData = backupData.databaseData,
        ) && restoreDatastoreData(
            datastoreData = backupData.datastoreData,
        )
    }

    private suspend fun restoreDatabaseData(
        databaseData: DatabaseData,
    ): Boolean {
        val accounts = sanitizeAccounts(
            accounts = databaseData.accounts.map(
                transform = Account::asEntity,
            ),
        ).map(
            transform = AccountEntity::asExternalModel,
        )
        val transactions = sanitizeTransactions(
            transactions = databaseData.transactions.map(
                transform = Transaction::asEntity,
            ),
        ).map(
            transform = TransactionEntity::asExternalModel,
        )
        transactionRepository.restoreData(
            categories = databaseData.categories.toImmutableList(),
            accounts = accounts,
            transactions = transactions,
            transactionForValues = databaseData.transactionForValues.toImmutableList(),
        )
        // TODO(Abhi): Add return value for database methods and propagate them to the usages.
        return true
    }

    private suspend fun restoreDatastoreData(
        datastoreData: DatastoreData,
    ): Boolean {
        return coroutineScope {
            with(
                receiver = financeManagerPreferencesRepository,
            ) {
                awaitAll(
                    async {
                        updateCategoryDataVersionNumber(
                            categoryDataVersionNumber = datastoreData.initialDataVersionNumber.category,
                        )
                    },
                    async {
                        updateDefaultExpenseCategoryId(
                            defaultExpenseCategoryId = datastoreData.defaultDataId.expenseCategory,
                        )
                    },
                    async {
                        updateDefaultIncomeCategoryId(
                            defaultIncomeCategoryId = datastoreData.defaultDataId.incomeCategory,
                        )
                    },
                    async {
                        updateDefaultInvestmentCategoryId(
                            defaultInvestmentCategoryId = datastoreData.defaultDataId.investmentCategory,
                        )
                    },
                    async {
                        updateDefaultAccountId(
                            accountId = datastoreData.defaultDataId.account,
                        )
                    },
                    async {
                        updateIsReminderEnabled(
                            isReminderEnabled = datastoreData.reminder.isEnabled,
                        )
                    },
                    async {
                        updateTransactionsDataVersionNumber(
                            transactionsDataVersionNumber = datastoreData.initialDataVersionNumber.transaction,
                        )
                    },
                    async {
                        updateLastDataChangeTimestamp()
                    },
                    async {
                        updateLastDataBackupTimestamp()
                    },
                )
            }.all { it }
        }
    }
}
