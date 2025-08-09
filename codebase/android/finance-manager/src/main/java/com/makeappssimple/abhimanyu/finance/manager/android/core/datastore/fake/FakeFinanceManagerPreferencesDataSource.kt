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

package com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.fake

import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.FinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Reminder
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.ReminderConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class FakeFinanceManagerPreferencesDataSource() :
    FinanceManagerPreferencesDataSource {
    private val defaultId = -1
    private val defaultTimestamp = -1L

    private val dataTimestampFlow = MutableStateFlow(
        value = DataTimestamp(
            lastBackup = defaultTimestamp,
            lastChange = defaultTimestamp,
        ),
    )

    private val defaultDataIdFlow = MutableStateFlow(
        value = DefaultDataId(
            expenseCategory = defaultId,
            incomeCategory = defaultId,
            investmentCategory = defaultId,
            account = defaultId,
        ),
    )

    private val initialDataVersionNumberFlow = MutableStateFlow(
        value = InitialDataVersionNumber(
            account = 0,
            category = 0,
            transaction = 0,
            transactionFor = 0,
        ),
    )

    private val reminderFlow = MutableStateFlow(
        value = Reminder(
            isEnabled = false,
            hour = ReminderConstants.DEFAULT_REMINDER_HOUR,
            min = ReminderConstants.DEFAULT_REMINDER_MIN,
        ),
    )

    override fun getDataTimestamp(): Flow<DataTimestamp> = dataTimestampFlow

    override fun getDefaultDataId(): Flow<DefaultDataId> = defaultDataIdFlow

    override fun getInitialDataVersionNumber(): Flow<InitialDataVersionNumber> =
        initialDataVersionNumberFlow

    override fun getReminder(): Flow<Reminder> = reminderFlow

    override suspend fun updateAccountDataVersionNumber(
        accountDataVersionNumber: Int,
    ): Boolean {
        initialDataVersionNumberFlow.update { currentValue ->
            currentValue.copy(
                account = accountDataVersionNumber,
            )
        }
        return true
    }

    override suspend fun updateCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ): Boolean {
        initialDataVersionNumberFlow.update { currentValue ->
            currentValue.copy(
                category = categoryDataVersionNumber,
            )
        }
        return true
    }

    override suspend fun updateDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ): Boolean {
        defaultDataIdFlow.update { currentValue ->
            currentValue.copy(
                expenseCategory = defaultExpenseCategoryId,
            )
        }
        return true
    }

    override suspend fun updateDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ): Boolean {
        defaultDataIdFlow.update { currentValue ->
            currentValue.copy(
                incomeCategory = defaultIncomeCategoryId,
            )
        }
        return true
    }

    override suspend fun updateDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ): Boolean {
        defaultDataIdFlow.update { currentValue ->
            currentValue.copy(
                investmentCategory = defaultInvestmentCategoryId,
            )
        }
        return true
    }

    override suspend fun updateDefaultAccountId(
        defaultAccountId: Int,
    ): Boolean {
        defaultDataIdFlow.update { currentValue ->
            currentValue.copy(
                account = defaultAccountId,
            )
        }
        return true
    }

    override suspend fun updateIsReminderEnabled(
        isReminderEnabled: Boolean,
    ): Boolean {
        reminderFlow.update { currentValue ->
            currentValue.copy(
                isEnabled = isReminderEnabled,
            )
        }
        return true
    }

    override suspend fun updateLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ): Boolean {
        dataTimestampFlow.update { currentValue ->
            currentValue.copy(
                lastBackup = lastDataBackupTimestamp,
            )
        }
        return true
    }

    override suspend fun updateLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ): Boolean {
        dataTimestampFlow.update { currentValue ->
            currentValue.copy(
                lastChange = lastDataChangeTimestamp,
            )
        }
        return true
    }

    override suspend fun updateReminderTime(
        hour: Int,
        min: Int,
    ): Boolean {
        reminderFlow.update { currentValue ->
            currentValue.copy(
                hour = hour,
                min = min,
            )
        }
        return true
    }

    override suspend fun updateTransactionDataVersionNumber(
        transactionDataVersionNumber: Int,
    ): Boolean {
        initialDataVersionNumberFlow.update { currentValue ->
            currentValue.copy(
                transaction = transactionDataVersionNumber,
            )
        }
        return true
    }

    override suspend fun updateTransactionForDataVersionNumber(
        transactionForDataVersionNumber: Int,
    ): Boolean {
        initialDataVersionNumberFlow.update { currentValue ->
            currentValue.copy(
                transactionFor = transactionForDataVersionNumber,
            )
        }
        return true
    }
}
