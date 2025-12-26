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

import com.makeappssimple.abhimanyu.common.di.AppVersionKitModule
import com.makeappssimple.abhimanyu.common.di.BuildConfigKitModule
import com.makeappssimple.abhimanyu.common.di.CoroutineScopeModule
import com.makeappssimple.abhimanyu.common.di.DispatcherProviderModule
import com.makeappssimple.abhimanyu.common.di.JsonReaderKitModule
import com.makeappssimple.abhimanyu.common.di.JsonWriterKitModule
import com.makeappssimple.abhimanyu.common.di.LogKitModule
import com.makeappssimple.abhimanyu.common.di.UriDecoderModule
import com.makeappssimple.abhimanyu.common.di.UriEncoderModule
import com.makeappssimple.abhimanyu.finance.manager.android.platform.di.AlarmKitModule
import com.makeappssimple.abhimanyu.finance.manager.android.platform.di.NotificationKitModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
    includes = [
        AlarmKitModule::class,
        AppKitModule::class,
        AppVersionKitModule::class,
        BuildConfigKitModule::class,
        ConfigKitModule::class,
        CoroutineScopeModule::class,
        DaosModule::class,
        DatabaseTransactionProviderModule::class,
        DataSourceModule::class,
        DataUseCaseModule::class,
        DateTimeKitModule::class,
        DispatcherProviderModule::class,
        FeatureUseCaseModule::class,
        JsonReaderKitModule::class,
        JsonWriterKitModule::class,
        LogKitModule::class,
        NavigationKitModule::class,
        NotificationKitModule::class,
        PreferencesModule::class,
        RepositoryModule::class,
        RoomModule::class,
        UriDecoderModule::class,
        UriEncoderModule::class,
    ],
)
@ComponentScan(
    "com.makeappssimple.abhimanyu.finance.manager.android",
)
public class FinanceManagerAppModule
