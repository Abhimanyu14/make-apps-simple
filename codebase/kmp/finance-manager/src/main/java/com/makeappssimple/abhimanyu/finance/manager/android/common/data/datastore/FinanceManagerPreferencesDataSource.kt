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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.datastore

import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.DataTimestamp
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.DefaultDataId
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

internal interface FinanceManagerPreferencesDataSource {
    fun getDataTimestamp(): Flow<DataTimestamp?>

    fun getDefaultDataId(): Flow<DefaultDataId?>

    fun getInitialDataVersionNumber(): Flow<InitialDataVersionNumber?>

    fun getReminder(): Flow<Reminder?>

    suspend fun updateAccountDataVersionNumber(
        accountDataVersionNumber: Int,
    ): Boolean

    suspend fun updateCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ): Boolean

    suspend fun updateDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ): Boolean

    suspend fun updateDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ): Boolean

    suspend fun updateDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ): Boolean

    suspend fun updateDefaultAccountId(
        defaultAccountId: Int,
    ): Boolean

    suspend fun updateIsReminderEnabled(
        isReminderEnabled: Boolean,
    ): Boolean

    suspend fun updateLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ): Boolean

    suspend fun updateLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ): Boolean

    suspend fun updateReminderTime(
        hour: Int,
        min: Int,
    ): Boolean

    suspend fun updateTransactionDataVersionNumber(
        transactionDataVersionNumber: Int,
    ): Boolean

    suspend fun updateTransactionForDataVersionNumber(
        transactionForDataVersionNumber: Int,
    ): Boolean
}
