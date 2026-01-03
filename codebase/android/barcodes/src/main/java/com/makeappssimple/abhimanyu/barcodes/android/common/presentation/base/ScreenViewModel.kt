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

package com.makeappssimple.abhimanyu.barcodes.android.common.presentation.base

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.NavigationKit
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.analytics.AnalyticsKit
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

internal abstract class ScreenViewModel(
    coroutineScope: CoroutineScope,
    private val analyticsKit: AnalyticsKit,
    private val logKit: LogKit,
    private val navigationKit: NavigationKit,
    private val screen: Screen,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit, NavigationKit by navigationKit {
    open fun initViewModel() {
        trackScreen()
        fetchData()
        observeData()
    }

    open fun fetchData(): Job {
        return Job().apply {
            complete()
        }
    }

    open fun observeData() {}

    private fun trackScreen() {
        analyticsKit.trackScreen(
            screenName = screen.route,
        )
    }
}
