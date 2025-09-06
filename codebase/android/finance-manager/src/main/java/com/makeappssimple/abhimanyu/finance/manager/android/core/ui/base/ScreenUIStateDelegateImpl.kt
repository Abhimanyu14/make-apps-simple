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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

public open class ScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
) : ScreenUIStateDelegate {
    override var isLoading: Boolean = true
    override val refreshSignal: MutableSharedFlow<Unit> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 0,
    )

    override fun refresh(): Job {
        return coroutineScope.launch {
            refreshSignal.emit(
                value = Unit,
            )
        }
    }

    override fun completeLoading(
        shouldRefresh: Boolean,
    ): Job {
        isLoading = false
        if (shouldRefresh) {
            return refresh()
        }
        return getCompletedJob()
    }

    override fun getCompletedJob(): CompletableJob {
        return Job().apply {
            complete()
        }
    }

    override fun startLoading(
        shouldRefresh: Boolean,
    ): Job {
        isLoading = true
        if (shouldRefresh) {
            return refresh()
        }
        return getCompletedJob()
    }

    override fun <T> withLoading(
        block: () -> T,
    ): T {
        startLoading()
        val result = block()
        completeLoading()
        return result
    }

    override suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T {
        startLoading()
        try {
            return block()
        } finally {
            completeLoading()
        }
    }
}
