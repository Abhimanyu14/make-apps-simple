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

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.remoteConfig
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

private object ConfigModuleConstants {
    const val SECONDS_IN_AN_HOUR = 3600L
}

@Module
public class ConfigKitModule {
    @Single
    public fun provideFirebaseRemoteConfigSettings(): FirebaseRemoteConfigSettings {/*
        // TODO(Abhi): Use this after adding dependency on debug build checks
        val remoteConfigFetchInterval: Long = if (isDebugBuild()) {
            0L
        } else {
            3600L
        }
        */
        val remoteConfigFetchInterval = ConfigModuleConstants.SECONDS_IN_AN_HOUR
        return FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(remoteConfigFetchInterval)
            .build()
    }

    @Single
    public fun provideFirebaseRemoteConfig(
        firebaseRemoteConfigSettings: FirebaseRemoteConfigSettings,
    ): FirebaseRemoteConfig {
        val firebaseRemoteConfig = Firebase.remoteConfig

        // Set firebase remote config settings
        firebaseRemoteConfig.setConfigSettingsAsync(firebaseRemoteConfigSettings)

        // Set default firebase remote config values
        firebaseRemoteConfig.setDefaultsAsync(R.xml.finance_manager_remote_config_defaults)

        return firebaseRemoteConfig
    }
}
