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

package com.makeappssimple.abhimanyu.barcodes.android.di

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.installations.FirebaseInstallations
import com.makeappssimple.abhimanyu.barcodes.android.core.analytics.FirebaseAnalyticsEventLogger
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
public class FirebaseModule {
    @Single
    public fun provideFirebaseAnalytics(): FirebaseAnalytics {
        return Firebase.analytics
    }

    @Single
    public fun provideFirebaseInstallations(): FirebaseInstallations {
        return FirebaseInstallations.getInstance()
    }

    @Single
    public fun provideFirebaseAnalyticsEventLogger(
        // firebaseAnalytics: FirebaseAnalytics,
    ): FirebaseAnalyticsEventLogger {
        return FirebaseAnalyticsEventLogger(
            // firebaseAnalytics = firebaseAnalytics,
        )
    }
}
