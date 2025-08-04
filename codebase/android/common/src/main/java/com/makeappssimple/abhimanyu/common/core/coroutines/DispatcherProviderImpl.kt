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

package com.makeappssimple.abhimanyu.common.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

internal class DispatcherProviderImpl(
    defaultCoroutineDispatcher: CoroutineDispatcher,
    ioCoroutineDispatcher: CoroutineDispatcher,
    mainCoroutineDispatcher: CoroutineDispatcher,
    mainImmediateCoroutineDispatcher: CoroutineDispatcher,
    unconfinedCoroutineDispatcher: CoroutineDispatcher,
) : DispatcherProvider {
    override val default: CoroutineDispatcher = defaultCoroutineDispatcher
    override val io: CoroutineDispatcher = ioCoroutineDispatcher
    override val main: CoroutineDispatcher = mainCoroutineDispatcher
    override val mainImmediate: CoroutineDispatcher =
        mainImmediateCoroutineDispatcher
    override val unconfined: CoroutineDispatcher = unconfinedCoroutineDispatcher

    override suspend fun <T> executeOnIoDispatcher(
        block: suspend CoroutineScope.() -> T,
    ): T {
        return withContext(
            context = io,
            block = block,
        )
    }
}
