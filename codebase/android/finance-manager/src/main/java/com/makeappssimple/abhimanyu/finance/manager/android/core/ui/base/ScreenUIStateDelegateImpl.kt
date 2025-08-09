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

import kotlinx.coroutines.flow.MutableSharedFlow

public open class ScreenUIStateDelegateImpl : ScreenUIStateDelegate {
    override var isLoading: Boolean = true
    override val refreshSignal: MutableSharedFlow<Unit> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
    )

    override fun refresh(): Boolean {
        return refreshSignal.tryEmit(Unit)
    }

    override fun completeLoading(
        shouldRefresh: Boolean,
    ) {
        isLoading = false
        if (shouldRefresh) {
            refresh()
        }
    }

    override fun startLoading(
        shouldRefresh: Boolean,
    ) {
        isLoading = true
        if (shouldRefresh) {
            refresh()
        }
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
