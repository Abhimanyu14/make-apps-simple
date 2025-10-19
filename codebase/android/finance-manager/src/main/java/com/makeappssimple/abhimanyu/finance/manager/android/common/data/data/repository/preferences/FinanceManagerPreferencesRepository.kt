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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.preferences

import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.DataTimestamp
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.DefaultDataId
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

public interface FinanceManagerPreferencesRepository {
    public fun getDataTimestampFlow(): Flow<DataTimestamp?>

    public fun getDefaultDataIdFlow(): Flow<DefaultDataId?>

    public fun getInitialDataVersionNumberFlow(): Flow<InitialDataVersionNumber?>

    public fun getReminderFlow(): Flow<Reminder?>

    public suspend fun getDataTimestamp(): DataTimestamp?

    public suspend fun getDefaultDataId(): DefaultDataId?

    public suspend fun getInitialDataVersionNumber(): InitialDataVersionNumber?

    public suspend fun getReminder(): Reminder?

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
        accountId: Int,
    ): Boolean

    public suspend fun updateIsReminderEnabled(
        isReminderEnabled: Boolean,
    ): Boolean

    public suspend fun updateLastDataBackupTimestamp(): Boolean

    public suspend fun updateLastDataChangeTimestamp(): Boolean

    public suspend fun updateTransactionsDataVersionNumber(
        transactionsDataVersionNumber: Int,
    ): Boolean

    public suspend fun updateReminderTime(
        hour: Int,
        min: Int,
    ): Boolean
}
