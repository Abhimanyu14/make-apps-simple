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

import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow

// TODO(Abhi): To delete
public interface ScreenUIStateDelegate {
    public val isLoading: Boolean
    public val refreshSignal: MutableSharedFlow<Unit>

    public fun completeLoading(
        shouldRefresh: Boolean = true,
    ): Job

    public fun getCompletedJob(): CompletableJob

    public fun refresh(): Job

    public fun startLoading(
        shouldRefresh: Boolean = true,
    ): Job

    public fun <T> withLoading(
        block: () -> T,
    ): T

    public suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T
}
