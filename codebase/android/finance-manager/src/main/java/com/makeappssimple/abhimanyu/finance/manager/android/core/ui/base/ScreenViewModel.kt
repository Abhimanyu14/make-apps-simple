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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

public abstract class ScreenViewModel(
    private val coroutineScope: CoroutineScope,
    private val logKit: LogKit,
    private val navigationKit: NavigationKit,
    private val screenUIStateDelegate: ScreenUIStateDelegate,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit,
    NavigationKit by navigationKit,
    ScreenUIStateDelegate by screenUIStateDelegate {
    internal fun initViewModel() {
        observeForRefreshSignal()
        coroutineScope.launch {
            fetchData().join()
            completeLoading().join()
            observeData()
        }
    }

    /**
     * Updates the UI state and state events.
     * This method should be implemented by subclasses to define how the UI state
     * and state events are updated based on the current data.
     */
    public abstract fun updateUiStateAndStateEvents()

    /**
     * Fetches initial data required for the screen.
     */
    public open fun fetchData(): Job {
        return Job().apply {
            complete()
        }
    }

    /**
     * Observes data changes and updates the UI state accordingly.
     * This method can be overridden to implement specific data observation logic.
     * By default, it does nothing.
     */
    public open fun observeData() {}

    private fun observeForRefreshSignal(): Job {
        return coroutineScope.launch {
            refreshSignal.collectLatest {
                updateUiStateAndStateEvents()
            }
        }
    }

    // region common
    internal fun getCompletedJob(): Job {
        return Job().apply {
            complete()
        }
    }

    internal fun refreshIfRequired(
        shouldRefresh: Boolean,
    ): Job {
        if (shouldRefresh) {
            return refresh()
        }
        return getCompletedJob()
    }
    // endregion
}
