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

package com.makeappssimple.abhimanyu.finance.manager.android.core.common.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

public sealed interface MyResult<out T> {
    public data object Loading : MyResult<Nothing>

    public data class Error(
        val exception: Throwable? = null,
    ) : MyResult<Nothing>

    public data class Success<T>(
        val data: T,
    ) : MyResult<T>
}

public fun <T> Flow<T>.asResult(): Flow<MyResult<T>> {
    return this
        .map<T, MyResult<T>> {
            MyResult.Success(it)
        }
        .onStart {
            emit(MyResult.Loading)
        }
        .catch {
            emit(MyResult.Error(it))
        }
}
