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

package com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.viewmodel

import com.makeappssimple.abhimanyu.barcodes.android.core.common.state.common.ScreenUICommonState
import kotlinx.coroutines.Job

internal class WebViewScreenUIStateDelegateImpl(
    private val screenUICommonState: ScreenUICommonState,
) : WebViewScreenUIStateDelegate {
    // region UI state
    override var screenTitle: String = ""
    // endregion

    // region state events
    override fun updateScreenTitle(
        updatedScreenTitle: String,
        shouldRefresh: Boolean,
    ): Job? {
        screenTitle = updatedScreenTitle
        if (shouldRefresh) {
            return screenUICommonState.refresh()
        }
        return null
    }
    // endregion
}
