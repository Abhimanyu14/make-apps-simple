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

package com.makeappssimple.abhimanyu.barcodes.android.common.ui.analytics

import org.koin.core.annotation.Single

// TODO(Abhi): Implement Firebase Analytics
@Single(
    binds = [
        AnalyticsKit::class,
    ],
)
internal class FirebaseAnalyticsKitImpl(
    // private val firebaseAnalytics: FirebaseAnalytics,
) : AnalyticsKit {
    override fun trackScreen(
        screenName: String,
    ) {
        /*
        firebaseAnalytics.logEvent(
            name = FirebaseAnalytics.Event.SCREEN_VIEW,
        ) {
            param(
                key = FirebaseAnalytics.Param.SCREEN_NAME,
                value = screenName,
            )
        }
        */
    }
}
