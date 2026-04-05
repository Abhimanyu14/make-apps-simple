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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.repository.preferences

import com.makeappssimple.abhimanyu.core.coroutines.CoroutineDispatcherProvider
import com.makeappssimple.abhimanyu.core.date.time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.datastore.FinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.DataTimestamp
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.DefaultDataId
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Reminder
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.preferences.FinanceManagerPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

internal class FinanceManagerPreferencesRepositoryImpl(
    private val dateTimeKit: DateTimeKit,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val financeManagerPreferencesDataSource: FinanceManagerPreferencesDataSource,
) : FinanceManagerPreferencesRepository {
    override fun getDataTimestampFlow(): Flow<DataTimestamp?> {
        return financeManagerPreferencesDataSource.getDataTimestamp()
    }

    override fun getDefaultDataIdFlow(): Flow<DefaultDataId?> {
        return financeManagerPreferencesDataSource.getDefaultDataId()
    }

    override fun getInitialDataVersionNumberFlow(): Flow<InitialDataVersionNumber?> {
        return financeManagerPreferencesDataSource.getInitialDataVersionNumber()
    }

    override fun getReminderFlow(): Flow<Reminder?> {
        return financeManagerPreferencesDataSource.getReminder()
    }

    override suspend fun getDataTimestamp(): DataTimestamp? {
        return coroutineDispatcherProvider.executeOnIoDispatcher {
            financeManagerPreferencesDataSource.getDataTimestamp().first()
        }
    }

    override suspend fun getDefaultDataId(): DefaultDataId? {
        return coroutineDispatcherProvider.executeOnIoDispatcher {
            financeManagerPreferencesDataSource.getDefaultDataId().first()
        }
    }

    override suspend fun getInitialDataVersionNumber(): InitialDataVersionNumber? {
        return coroutineDispatcherProvider.executeOnIoDispatcher {
            financeManagerPreferencesDataSource.getInitialDataVersionNumber()
                .first()
        }
    }

    override suspend fun getReminder(): Reminder? {
        return coroutineDispatcherProvider.executeOnIoDispatcher {
            financeManagerPreferencesDataSource.getReminder().first()
        }
    }

    override suspend fun updateCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ): Boolean {
        return coroutineDispatcherProvider.executeOnIoDispatcher {
            financeManagerPreferencesDataSource.updateCategoryDataVersionNumber(
                categoryDataVersionNumber = categoryDataVersionNumber,
            )
        }
    }

    override suspend fun updateDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ): Boolean {
        return coroutineDispatcherProvider.executeOnIoDispatcher {
            financeManagerPreferencesDataSource.updateDefaultExpenseCategoryId(
                defaultExpenseCategoryId = defaultExpenseCategoryId,
            )
        }
    }

    override suspend fun updateDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ): Boolean {
        return coroutineDispatcherProvider.executeOnIoDispatcher {
            financeManagerPreferencesDataSource.updateDefaultIncomeCategoryId(
                defaultIncomeCategoryId = defaultIncomeCategoryId,
            )
        }
    }

    override suspend fun updateDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ): Boolean {
        return coroutineDispatcherProvider.executeOnIoDispatcher {
            financeManagerPreferencesDataSource.updateDefaultInvestmentCategoryId(
                defaultInvestmentCategoryId = defaultInvestmentCategoryId,
            )
        }
    }

    override suspend fun updateDefaultAccountId(
        accountId: Int,
    ): Boolean {
        return coroutineDispatcherProvider.executeOnIoDispatcher {
            financeManagerPreferencesDataSource.updateDefaultAccountId(
                defaultAccountId = accountId,
            )
        }
    }

    override suspend fun updateIsReminderEnabled(
        isReminderEnabled: Boolean,
    ): Boolean {
        return coroutineDispatcherProvider.executeOnIoDispatcher {
            financeManagerPreferencesDataSource.updateIsReminderEnabled(
                isReminderEnabled = isReminderEnabled,
            )
        }
    }

    override suspend fun updateLastDataBackupTimestamp(): Boolean {
        return coroutineDispatcherProvider.executeOnIoDispatcher {
            financeManagerPreferencesDataSource.updateLastDataBackupTimestamp(
                lastDataBackupTimestamp = dateTimeKit.getCurrentTimeMillis(),
            )
        }
    }

    override suspend fun updateLastDataChangeTimestamp(): Boolean {
        return coroutineDispatcherProvider.executeOnIoDispatcher {
            financeManagerPreferencesDataSource.updateLastDataChangeTimestamp(
                lastDataChangeTimestamp = dateTimeKit.getCurrentTimeMillis(),
            )
        }
    }

    override suspend fun updateReminderTime(
        hour: Int,
        min: Int,
    ): Boolean {
        return coroutineDispatcherProvider.executeOnIoDispatcher {
            financeManagerPreferencesDataSource.updateReminderTime(
                hour = hour,
                min = min,
            )
        }
    }

    override suspend fun updateTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ): Boolean {
        return coroutineDispatcherProvider.executeOnIoDispatcher {
            financeManagerPreferencesDataSource.updateTransactionDataVersionNumber(
                transactionDataVersionNumber = transactionsDataVersionNumber,
            )
        }
    }
}
