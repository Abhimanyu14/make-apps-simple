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

package com.makeappssimple.abhimanyu.common.core.util

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

private const val DEFAULT_TIMEOUT_MILLIS = 5000L

public fun Flow<Boolean>.defaultBooleanStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(
        stopTimeoutMillis = DEFAULT_TIMEOUT_MILLIS,
    ),
): StateFlow<Boolean> {
    return this.stateIn(
        scope = scope,
        started = started,
        initialValue = false,
    )
}

public fun Flow<Long>.defaultLongStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(
        stopTimeoutMillis = DEFAULT_TIMEOUT_MILLIS,
    ),
): StateFlow<Long> {
    return this.stateIn(
        scope = scope,
        started = started,
        initialValue = 0L,
    )
}

public fun <T> Flow<ImmutableList<T>>.defaultImmutableListStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(
        stopTimeoutMillis = DEFAULT_TIMEOUT_MILLIS,
    ),
): StateFlow<ImmutableList<T>> {
    return this.stateIn(
        scope = scope,
        started = started,
        initialValue = persistentListOf(),
    )
}

public fun <K, V> Flow<ImmutableMap<K, V>>.defaultMapStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(
        stopTimeoutMillis = DEFAULT_TIMEOUT_MILLIS,
    ),
): StateFlow<ImmutableMap<K, V>> {
    return this.stateIn(
        scope = scope,
        started = started,
        initialValue = persistentMapOf(),
    )
}

public fun <T> Flow<List<T>>.defaultListStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(
        stopTimeoutMillis = DEFAULT_TIMEOUT_MILLIS,
    ),
): StateFlow<List<T>> {
    return this.stateIn(
        scope = scope,
        started = started,
        initialValue = emptyList(),
    )
}

public fun <T> Flow<T>.defaultNullableObjectStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(
        stopTimeoutMillis = DEFAULT_TIMEOUT_MILLIS,
    ),
    initialValue: T? = null,
): StateFlow<T?> {
    return this.stateIn(
        scope = scope,
        started = started,
        initialValue = initialValue,
    )
}

public fun <T> Flow<T>.defaultObjectStateIn(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(
        stopTimeoutMillis = DEFAULT_TIMEOUT_MILLIS,
    ),
    initialValue: T,
): StateFlow<T> {
    return this.stateIn(
        scope = scope,
        started = started,
        initialValue = initialValue,
    )
}
