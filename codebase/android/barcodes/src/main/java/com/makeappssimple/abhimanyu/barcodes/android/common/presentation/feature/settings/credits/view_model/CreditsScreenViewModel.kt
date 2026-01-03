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

package com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.settings.credits.view_model

import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.base.ScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.NavigationKit
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.analytics.AnalyticsKit
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import kotlinx.coroutines.CoroutineScope
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class CreditsScreenViewModel(
    analyticsKit: AnalyticsKit,
    coroutineScope: CoroutineScope,
    logKit: LogKit,
    navigationKit: NavigationKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    analyticsKit = analyticsKit,
    logKit = logKit,
    navigationKit = navigationKit,
    screen = Screen.Credits,
)
