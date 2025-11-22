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

package com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.common

import android.net.Uri
import com.makeappssimple.abhimanyu.common.core.json_writer.JsonWriterKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.model.BackupData
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.model.DatabaseData
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.model.DatastoreData
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.DataTimestamp
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.DefaultDataId
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Reminder
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.transaction.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.transaction_for.GetAllTransactionForValuesUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json

internal class BackupDataUseCase(
    private val dateTimeKit: DateTimeKit,
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val jsonWriterKit: JsonWriterKit,
) {
    suspend operator fun invoke(
        uri: Uri,
    ): Boolean {
        val backupData = BackupData(
            lastBackupTime = dateTimeKit.getReadableDateAndTime(),
            lastBackupTimestamp = dateTimeKit.getCurrentTimeMillis().toString(),
            databaseData = getDatabaseData(),
            datastoreData = getDatastoreData(),
        )
        val jsonString = Json.encodeToString(
            value = backupData,
        )
        financeManagerPreferencesRepository.updateLastDataBackupTimestamp()
        return jsonWriterKit.writeJsonToFile(
            uri = uri,
            jsonString = jsonString,
        )
    }

    private suspend fun getDatabaseData(): DatabaseData {
        return coroutineScope {
            val categories = async {
                getAllCategoriesUseCase()
            }
            val accounts = async {
                getAllAccountsUseCase()
            }
            val transactionForValues = async {
                getAllTransactionForValuesUseCase()
            }
            val transactions = async {
                getAllTransactionsUseCase()
            }

            DatabaseData(
                categories = categories.await(),
                accounts = accounts.await(),
                transactionForValues = transactionForValues.await(),
                transactions = transactions.await(),
            )
        }
    }

    private suspend fun getDatastoreData(): DatastoreData {
        return DatastoreData(
            defaultDataId = financeManagerPreferencesRepository.getDefaultDataId()
                ?: DefaultDataId(),
            initialDataVersionNumber = financeManagerPreferencesRepository.getInitialDataVersionNumber()
                ?: InitialDataVersionNumber(),
            dataTimestamp = financeManagerPreferencesRepository.getDataTimestamp()
                ?: DataTimestamp(),
            reminder = financeManagerPreferencesRepository.getReminder()
                ?: Reminder(),
        )
    }
}
