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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.datastore

import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Reminder
import kotlinx.coroutines.flow.Flow

public interface FinanceManagerPreferencesDataSource {
    public fun getDataTimestamp(): Flow<DataTimestamp?>

    public fun getDefaultDataId(): Flow<DefaultDataId?>

    public fun getInitialDataVersionNumber(): Flow<InitialDataVersionNumber?>

    public fun getReminder(): Flow<Reminder?>

    public suspend fun updateAccountDataVersionNumber(
        accountDataVersionNumber: Int,
    ): Boolean

    public suspend fun updateCategoryDataVersionNumber(
        categoryDataVersionNumber: Int,
    ): Boolean

    public suspend fun updateDefaultExpenseCategoryId(
        defaultExpenseCategoryId: Int,
    ): Boolean

    public suspend fun updateDefaultIncomeCategoryId(
        defaultIncomeCategoryId: Int,
    ): Boolean

    public suspend fun updateDefaultInvestmentCategoryId(
        defaultInvestmentCategoryId: Int,
    ): Boolean

    public suspend fun updateDefaultAccountId(
        defaultAccountId: Int,
    ): Boolean

    public suspend fun updateIsReminderEnabled(
        isReminderEnabled: Boolean,
    ): Boolean

    public suspend fun updateLastDataBackupTimestamp(
        lastDataBackupTimestamp: Long,
    ): Boolean

    public suspend fun updateLastDataChangeTimestamp(
        lastDataChangeTimestamp: Long,
    ): Boolean

    public suspend fun updateReminderTime(
        hour: Int,
        min: Int,
    ): Boolean

    public suspend fun updateTransactionDataVersionNumber(
        transactionDataVersionNumber: Int,
    ): Boolean

    public suspend fun updateTransactionForDataVersionNumber(
        transactionForDataVersionNumber: Int,
    ): Boolean
}
