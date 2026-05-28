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

package com.makeappssimple.abhimanyu.core.coroutines.di

import com.makeappssimple.abhimanyu.core.coroutines.DISPATCHER_DEFAULT
import com.makeappssimple.abhimanyu.core.coroutines.DISPATCHER_IO
import com.makeappssimple.abhimanyu.core.coroutines.DISPATCHER_MAIN
import com.makeappssimple.abhimanyu.core.coroutines.DISPATCHER_MAIN_IMMEDIATE
import com.makeappssimple.abhimanyu.core.coroutines.DISPATCHER_UNCONFINED
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@ComponentScan(
    "com.makeappssimple.abhimanyu.core.coroutines",
)
public class DispatcherProviderModule {
    @Single
    @Named(DISPATCHER_DEFAULT)
    internal fun providesDefaultCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Single
    @Named(DISPATCHER_IO)
    internal fun providesIoCoroutineDispatcher(): CoroutineDispatcher {
        // TODO(Abhi): This is a fallback for Android to CMP migration
        //  This has to be fixed for the app to be production ready for Android
        //  Dispatchers.IO is not available on JS/Wasm; Default is a safe shared fallback.
        return Dispatchers.Default
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
}
