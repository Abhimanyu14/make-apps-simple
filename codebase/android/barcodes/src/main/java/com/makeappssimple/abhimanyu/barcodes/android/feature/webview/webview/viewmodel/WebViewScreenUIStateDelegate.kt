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

import kotlinx.coroutines.Job

internal interface WebViewScreenUIStateDelegate {
    // region UI state
    val screenTitle: String
    // endregion

    // region state events
    fun navigateUp(): Job

    fun updateScreenTitle(
        updatedScreenTitle: String,
        shouldRefresh: Boolean = true,
    ): Job?
    // endregion
}
