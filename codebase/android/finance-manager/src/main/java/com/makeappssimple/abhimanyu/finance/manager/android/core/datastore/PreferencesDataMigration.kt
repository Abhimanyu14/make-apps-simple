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

import androidx.datastore.core.DataMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.constants.DatastoreConstants

public val preferencesDataMigrations: List<DataMigration<Preferences>> = listOf(
    PreferencesDataMigration.MIGRATION_3_TO_4,
    PreferencesDataMigration.MIGRATION_2_TO_3,
    PreferencesDataMigration.MIGRATION_1_TO_2,
)

private object PreferencesDataMigration {
    val MIGRATION_3_TO_4 = object : DataMigration<Preferences> {
        override suspend fun shouldMigrate(
            currentData: Preferences,
        ): Boolean {
            return currentData[DataStoreConstants.CURRENT_VERSION_NUMBER].orZero() < DatastoreConstants.DATASTORE_CURRENT_VERSION_NUMBER
        }

        override suspend fun migrate(
            currentData: Preferences,
        ): Preferences {
            val emojiDataVersionNumberPreferencesKey: Preferences.Key<Int> =
                intPreferencesKey(
                    name = "emoji_data_version_number",
                )

            // Get mutable preferences
            val currentMutablePrefs = currentData.toMutablePreferences()

            // Remove existing key
            currentMutablePrefs.remove(emojiDataVersionNumberPreferencesKey)

            return currentMutablePrefs.toPreferences()
        }

        override suspend fun cleanUp() {}
    }

    val MIGRATION_2_TO_3 = object : DataMigration<Preferences> {
        override suspend fun shouldMigrate(
            currentData: Preferences,
        ): Boolean {
            return currentData[DataStoreConstants.CURRENT_VERSION_NUMBER].orZero() < DatastoreConstants.DATASTORE_CURRENT_VERSION_NUMBER
        }

        override suspend fun migrate(
            currentData: Preferences,
        ): Preferences {
            val transactionsDataVersionNumberPreferencesKey: Preferences.Key<Int> =
                intPreferencesKey(
                    name = "transactions_data_version_number",
                )

            // Get mutable preferences
            val currentMutablePrefs = currentData.toMutablePreferences()

            // Copy existing value
            val newValue =
                currentData[transactionsDataVersionNumberPreferencesKey].orZero()

            // Remove existing key
            currentMutablePrefs.remove(
                transactionsDataVersionNumberPreferencesKey
            )

            // Add existing value to new key
            currentMutablePrefs[DataStoreConstants.InitialDataVersionNumber.TRANSACTION] =
                newValue

            // Update data store version number
            currentMutablePrefs[DataStoreConstants.CURRENT_VERSION_NUMBER] = 3

            return currentMutablePrefs.toPreferences()
        }

        override suspend fun cleanUp() {}
    }

    val MIGRATION_1_TO_2 = object : DataMigration<Preferences> {
        override suspend fun shouldMigrate(
            currentData: Preferences,
        ): Boolean {
            return currentData[DataStoreConstants.CURRENT_VERSION_NUMBER].orZero() < DatastoreConstants.DATASTORE_CURRENT_VERSION_NUMBER
        }

        override suspend fun migrate(
            currentData: Preferences,
        ): Preferences {
            val defaultSourceIdPreferencesKey: Preferences.Key<Int> =
                intPreferencesKey(
                    name = "default_source_id",
                )

            // Get mutable preferences
            val currentMutablePrefs = currentData.toMutablePreferences()

            // Copy existing value
            val newValue = currentData[defaultSourceIdPreferencesKey].orZero()

            // Remove existing key
            currentMutablePrefs.remove(defaultSourceIdPreferencesKey)

            // Add existing value to new key
            currentMutablePrefs[DataStoreConstants.DefaultId.ACCOUNT] = newValue

            // Update data store version number
            currentMutablePrefs[DataStoreConstants.CURRENT_VERSION_NUMBER] =
                DatastoreConstants.DATASTORE_CURRENT_VERSION_NUMBER

            return currentMutablePrefs.toPreferences()
        }

        override suspend fun cleanUp() {}
    }
}
