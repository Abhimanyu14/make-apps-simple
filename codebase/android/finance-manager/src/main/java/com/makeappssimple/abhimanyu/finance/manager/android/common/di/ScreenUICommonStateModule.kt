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

package com.makeappssimple.abhimanyu.finance.manager.android.common.di

import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.state.common.ScreenUICommonStateImpl
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.state.loading.ScreenUIStateLoadingImpl
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.state.refresh.ScreenUIStateRefreshImpl
import kotlinx.coroutines.CoroutineScope
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
public class ScreenUICommonStateModule {
    @Single
    internal fun providesScreenUICommonState(
        coroutineScope: CoroutineScope,
    ): ScreenUICommonState {
        return ScreenUICommonStateImpl(
            screenUIStateLoading = ScreenUIStateLoadingImpl(
                screenUIStateRefresh = ScreenUIStateRefreshImpl(
                    coroutineScope = coroutineScope,
                ),
            ),
        )
    }
}
