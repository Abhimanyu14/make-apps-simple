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

package com.makeappssimple.abhimanyu.finance.manager.android.di

import com.makeappssimple.abhimanyu.finance.manager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.coroutines.DispatcherProviderImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

private const val DISPATCHER_DEFAULT = "DISPATCHER_DEFAULT"
private const val DISPATCHER_IO = "DISPATCHER_IO"
private const val DISPATCHER_MAIN = "DISPATCHER_MAIN"
private const val DISPATCHER_MAIN_IMMEDIATE = "DISPATCHER_MAIN_IMMEDIATE"
private const val DISPATCHER_UNCONFINED = "DISPATCHER_UNCONFINED"

@Module
public class DispatcherProviderModule {
    @Single
    @Named(DISPATCHER_DEFAULT)
    internal fun providesDefaultCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Single
    @Named(DISPATCHER_IO)
    internal fun providesIoCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Single
    @Named(DISPATCHER_MAIN)
    internal fun providesMainCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    @Single
    @Named(DISPATCHER_MAIN_IMMEDIATE)
    internal fun providesMainImmediateCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main.immediate
    }

    @Single
    @Named(DISPATCHER_UNCONFINED)
    internal fun providesUnconfinedCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.Unconfined
    }

    @Single
    internal fun providesDispatcherProvider(
        @Named(DISPATCHER_DEFAULT) defaultCoroutineDispatcher: CoroutineDispatcher,
        @Named(DISPATCHER_IO) ioCoroutineDispatcher: CoroutineDispatcher,
        @Named(DISPATCHER_MAIN) mainCoroutineDispatcher: CoroutineDispatcher,
        @Named(DISPATCHER_MAIN_IMMEDIATE) mainImmediateCoroutineDispatcher: CoroutineDispatcher,
        @Named(DISPATCHER_UNCONFINED) unconfinedCoroutineDispatcher: CoroutineDispatcher,
    ): DispatcherProvider {
        return DispatcherProviderImpl(
            defaultCoroutineDispatcher = defaultCoroutineDispatcher,
            ioCoroutineDispatcher = defaultCoroutineDispatcher,
            mainCoroutineDispatcher = defaultCoroutineDispatcher,
            mainImmediateCoroutineDispatcher = defaultCoroutineDispatcher,
            unconfinedCoroutineDispatcher = defaultCoroutineDispatcher,
        )
    }
}
