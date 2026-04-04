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

package com.makeappssimple.abhimanyu.barcodes.android.features.settings.presentation.settings.view_model

import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.base.ScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.BarcodesNavigationKit
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.BarcodesScreen
import com.makeappssimple.abhimanyu.barcodes.android.shared.ui.analytics.AnalyticsKit
import com.makeappssimple.abhimanyu.common.log_kit.LogKit
import kotlinx.coroutines.CoroutineScope

internal class SettingsScreenViewModel(
    analyticsKit: AnalyticsKit,
    barcodesNavigationKit: BarcodesNavigationKit,
    coroutineScope: CoroutineScope,
    logKit: LogKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    analyticsKit = analyticsKit,
    barcodesNavigationKit = barcodesNavigationKit,
    barcodesScreen = BarcodesScreen.Settings,
    logKit = logKit,
)
