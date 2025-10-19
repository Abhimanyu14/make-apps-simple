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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

internal object DataStoreConstants {
    val CURRENT_VERSION_NUMBER: Preferences.Key<Int> = intPreferencesKey(
        name = "datastore_version_number",
    )

    object DefaultId {
        val ACCOUNT: Preferences.Key<Int> = intPreferencesKey(
            name = "default_account_id",
        )
        val EXPENSE_CATEGORY: Preferences.Key<Int> = intPreferencesKey(
            name = "default_expense_category_id",
        )
        val INCOME_CATEGORY: Preferences.Key<Int> = intPreferencesKey(
            name = "default_income_category_id",
        )
        val INVESTMENT_CATEGORY: Preferences.Key<Int> =
            intPreferencesKey(
                name = "default_investment_category_id",
            )
    }

    object DataTimestamp {
        val LAST_DATA_BACKUP: Preferences.Key<Long> = longPreferencesKey(
            name = "last_data_backup_timestamp",
        )
        val LAST_DATA_CHANGE: Preferences.Key<Long> = longPreferencesKey(
            name = "last_data_change_timestamp",
        )
    }

    object InitialDataVersionNumber {
        val ACCOUNT: Preferences.Key<Int> = intPreferencesKey(
            name = "account_data_version_number",
        )
        val CATEGORY: Preferences.Key<Int> = intPreferencesKey(
            name = "category_data_version_number",
        )
        val TRANSACTION: Preferences.Key<Int> = intPreferencesKey(
            name = "transaction_data_version_number",
        )
        val TRANSACTION_FOR: Preferences.Key<Int> = intPreferencesKey(
            name = "transaction_for_data_version_number",
        )
    }

    object Reminder {
        val IS_REMINDER_ENABLED: Preferences.Key<Boolean> =
            booleanPreferencesKey(
                name = "is_reminder_enabled",
            )
        val HOUR: Preferences.Key<Int> = intPreferencesKey(
            name = "hour",
        )
        val MIN: Preferences.Key<Int> = intPreferencesKey(
            name = "min",
        )
    }
}
