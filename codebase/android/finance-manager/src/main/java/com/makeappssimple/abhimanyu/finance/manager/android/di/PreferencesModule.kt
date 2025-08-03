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

package com.makeappssimple.abhimanyu.finance.manager.android.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.MyPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.constants.DatastoreConstants
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.preferencesDataMigrations
import com.makeappssimple.abhimanyu.finance.manager.android.core.logger.LogKit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
public class PreferencesModule {
    @Single
    internal fun providePreferencesDataStore(
        appContext: Context,
        dispatcherProvider: DispatcherProvider,
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = {
                    emptyPreferences()
                },
            ),
            migrations = preferencesDataMigrations,
            scope = CoroutineScope(
                context = dispatcherProvider.io + SupervisorJob(),
            ),
            produceFile = {
                appContext.preferencesDataStoreFile(
                    name = DatastoreConstants.DATASTORE_FILE_NAME,
                )
            },
        )
    }

    @Single
    internal fun providesMyPreferencesDataSource(
        dataStore: DataStore<Preferences>,
        logKit: LogKit,
    ): MyPreferencesDataSource {
        return MyPreferencesDataSource(
            dataStore = dataStore,
            logKit = logKit,
        )
    }
}
