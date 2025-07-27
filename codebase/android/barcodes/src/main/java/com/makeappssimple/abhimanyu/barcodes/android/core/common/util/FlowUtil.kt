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

package com.makeappssimple.abhimanyu.barcodes.android.core.common.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

internal fun Flow<Boolean>.defaultBooleanStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(
        stopTimeoutMillis = 5000,
    ),
): StateFlow<Boolean> {
    return this.stateIn(
        scope = scope,
        started = started,
        initialValue = false,
    )
}

internal fun <T> Flow<List<T>>.defaultListStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(
        stopTimeoutMillis = 5000,
    ),
): StateFlow<List<T>> {
    return this.stateIn(
        scope = scope,
        started = started,
        initialValue = emptyList(),
    )
}

internal fun <T> Flow<T>.defaultNullableObjectStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(
        stopTimeoutMillis = 5000,
    ),
    initialValue: T? = null,
): StateFlow<T?> {
    return this.stateIn(
        scope = scope,
        started = started,
        initialValue = initialValue,
    )
}

internal fun <T> Flow<T>.defaultObjectStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(
        stopTimeoutMillis = 5000,
    ),
    initialValue: T,
): StateFlow<T> {
    return this.stateIn(
        scope = scope,
        started = started,
        initialValue = initialValue,
    )
}
