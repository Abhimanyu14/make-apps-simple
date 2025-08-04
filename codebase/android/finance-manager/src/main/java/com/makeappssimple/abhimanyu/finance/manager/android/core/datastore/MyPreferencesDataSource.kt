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

package com.makeappssimple.abhimanyu.finance.manager.android.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.makeappssimple.abhimanyu.common.core.extensions.orFalse
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.common.logger.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Reminder
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.ReminderConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private object MyPreferencesDataSourceConstants {
    const val DEFAULT_ID = -1
    const val DEFAULT_TIMESTAMP = -1L
}

public class MyPreferencesDataSource(
    private val dataStore: DataStore<Preferences>,
    private val logKit: LogKit,
) {
    private val preferences: Flow<Preferences> = dataStore.data
        .catch { exception ->
            logKit.logError(
                message = "Error reading preferences. ${exception.localizedMessage}",
            )
            emit(
                value = emptyPreferences(),
            )
        }

    public fun getDataTimestamp(): Flow<DataTimestamp?> {
        return preferences.map {
            DataTimestamp(
                lastBackup = it[DataStoreConstants.DataTimestamp.LAST_DATA_BACKUP]
                    ?: MyPreferencesDataSourceConstants.DEFAULT_TIMESTAMP,
                lastChange = it[DataStoreConstants.DataTimestamp.LAST_DATA_CHANGE]
                    ?: MyPreferencesDataSourceConstants.DEFAULT_TIMESTAMP,
            )
        }
    }

    public fun getDefaultDataId(): Flow<DefaultDataId?> {
        return preferences.map {
            DefaultDataId(
                expenseCategory = it[DataStoreConstants.DefaultId.EXPENSE_CATEGORY]
                    ?: MyPreferencesDataSourceConstants.DEFAULT_ID,
                incomeCategory = it[DataStoreConstants.DefaultId.INCOME_CATEGORY]
                    ?: MyPreferencesDataSourceConstants.DEFAULT_ID,
                investmentCategory = it[DataStoreConstants.DefaultId.INVESTMENT_CATEGORY]
                    ?: MyPreferencesDataSourceConstants.DEFAULT_ID,
                account = it[DataStoreConstants.DefaultId.ACCOUNT]
                    ?: MyPreferencesDataSourceConstants.DEFAULT_ID,
            )
        }
    }

    public fun getInitialDataVersionNumber(): Flow<InitialDataVersionNumber?> {
        return preferences.map {
            InitialDataVersionNumber(
                account = it[DataStoreConstants.InitialDataVersionNumber.ACCOUNT].orZero(),
                category = it[DataStoreConstants.InitialDataVersionNumber.CATEGORY].orZero(),
                transaction = it[DataStoreConstants.InitialDataVersionNumber.TRANSACTION].orZero(),
                transactionFor = it[DataStoreConstants.InitialDataVersionNumber.TRANSACTION_FOR].orZero(),
            )
        }
    }

    public fun getReminder(): Flow<Reminder?> {
        return preferences.map {
            Reminder(
                isEnabled = it[DataStoreConstants.Reminder.IS_REMINDER_ENABLED].orFalse(),
                hour = it[DataStoreConstants.Reminder.HOUR]
                    ?: ReminderConstants.DEFAULT_REMINDER_HOUR,
                min = it[DataStoreConstants.Reminder.MIN]
                    ?: ReminderConstants.DEFAULT_REMINDER_MIN,
            )
        }
    }

    public suspend fun updateAccountDataVersionNumber(
        accountDataVersionNumber: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.InitialDataVersionNumber.ACCOUNT] =
                    accountDataVersionNumber
            }
        }
    }

    public suspend fun updateCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.InitialDataVersionNumber.CATEGORY] =
                    categoryDataVersionNumber
            }
        }
    }

    public suspend fun updateDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.DefaultId.EXPENSE_CATEGORY] =
                    defaultExpenseCategoryId
            }
        }
    }

    public suspend fun updateDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.DefaultId.INCOME_CATEGORY] =
                    defaultIncomeCategoryId
            }
        }
    }

    public suspend fun updateDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.DefaultId.INVESTMENT_CATEGORY] =
                    defaultInvestmentCategoryId
            }
        }
    }

    public suspend fun updateDefaultAccountId(
        defaultAccountId: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.DefaultId.ACCOUNT] = defaultAccountId
            }
        }
    }

    public suspend fun updateIsReminderEnabled(
        isReminderEnabled: Boolean,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.Reminder.IS_REMINDER_ENABLED] =
                    isReminderEnabled
            }
        }
    }

    public suspend fun updateLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.DataTimestamp.LAST_DATA_BACKUP] =
                    lastDataBackupTimestamp
            }
        }
    }

    public suspend fun updateLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.DataTimestamp.LAST_DATA_CHANGE] =
                    lastDataChangeTimestamp
            }
        }
    }

    public suspend fun updateReminderTime(
        hour: Int,
        min: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.Reminder.HOUR] = hour
                it[DataStoreConstants.Reminder.MIN] = min
            }
        }
    }

    public suspend fun updateTransactionDataVersionNumber(
        transactionDataVersionNumber: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.InitialDataVersionNumber.TRANSACTION] =
                    transactionDataVersionNumber
            }
        }
    }

    public suspend fun updateTransactionForDataVersionNumber(
        transactionForDataVersionNumber: Int,
    ): Boolean {
        return tryDataStoreEdit {
            dataStore.edit {
                it[DataStoreConstants.InitialDataVersionNumber.TRANSACTION_FOR] =
                    transactionForDataVersionNumber
            }
        }
    }

    private suspend fun tryDataStoreEdit(
        block: suspend () -> Unit,
    ): Boolean {
        return try {
            block()
            true
        } catch (
            ioException: IOException,
        ) {
            false
        }
    }
}
