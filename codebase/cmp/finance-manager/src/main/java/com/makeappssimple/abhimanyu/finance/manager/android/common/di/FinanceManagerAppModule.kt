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

package com.makeappssimple.abhimanyu.finance.manager.android.common.di

import com.makeappssimple.abhimanyu.core.app.version.di.AppVersionKitModule
import com.makeappssimple.abhimanyu.core.app.version.AppVersionKit
import com.makeappssimple.abhimanyu.core.app.version.AppVersionKitImpl
import com.makeappssimple.abhimanyu.core.build_config.BuildConfigKit
import com.makeappssimple.abhimanyu.core.build_config.BuildConfigKitImpl
import com.makeappssimple.abhimanyu.core.build_config.di.BuildConfigKitModule
import com.makeappssimple.abhimanyu.core.coroutines.CoroutineDispatcherProvider
import com.makeappssimple.abhimanyu.core.coroutines.CoroutineDispatcherProviderImpl
import com.makeappssimple.abhimanyu.core.coroutines.DISPATCHER_DEFAULT
import com.makeappssimple.abhimanyu.core.coroutines.DISPATCHER_IO
import com.makeappssimple.abhimanyu.core.coroutines.DISPATCHER_MAIN
import com.makeappssimple.abhimanyu.core.coroutines.DISPATCHER_MAIN_IMMEDIATE
import com.makeappssimple.abhimanyu.core.coroutines.DISPATCHER_UNCONFINED
import com.makeappssimple.abhimanyu.core.coroutines.di.CoroutineScopeModule
import com.makeappssimple.abhimanyu.core.coroutines.di.DispatcherProviderModule
import com.makeappssimple.abhimanyu.core.json.kit.JsonReaderKit
import com.makeappssimple.abhimanyu.core.json.kit.JsonReaderKitImpl
import com.makeappssimple.abhimanyu.core.json.kit.JsonWriterKit
import com.makeappssimple.abhimanyu.core.json.kit.JsonWriterKitImpl
import com.makeappssimple.abhimanyu.core.json.kit.di.JsonKitModule
import com.makeappssimple.abhimanyu.core.log.kit.LogKit
import com.makeappssimple.abhimanyu.core.log.kit.LogKitImpl
import com.makeappssimple.abhimanyu.core.log.kit.di.LogKitModule
import com.makeappssimple.abhimanyu.core.uri.kit.UriDecoder
import com.makeappssimple.abhimanyu.core.uri.kit.UriDecoderImpl
import com.makeappssimple.abhimanyu.core.uri.kit.UriEncoder
import com.makeappssimple.abhimanyu.core.uri.kit.UriEncoderImpl
import com.makeappssimple.abhimanyu.core.uri.kit.di.UriKitModule
import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.qualifier.named
import com.makeappssimple.abhimanyu.finance.manager.android.platform.di.AlarmKitModule
import com.makeappssimple.abhimanyu.finance.manager.android.platform.di.NotificationKitModule
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ksp.generated.*

public class FinanceManagerAppModule {
    public val module: Module
        get() = module {
            includes(
                coreBindings,
                com_makeappssimple_abhimanyu_finance_manager_android_platform_di_AlarmKitModule,
                com_makeappssimple_abhimanyu_finance_manager_android_common_di_AppKitModule,
                com_makeappssimple_abhimanyu_finance_manager_android_common_di_ConfigKitModule,
                com_makeappssimple_abhimanyu_finance_manager_android_common_di_DaosModule,
                com_makeappssimple_abhimanyu_finance_manager_android_common_di_DatabaseTransactionProviderModule,
                com_makeappssimple_abhimanyu_finance_manager_android_common_di_DataSourceModule,
                com_makeappssimple_abhimanyu_finance_manager_android_common_di_DataUseCaseModule,
                com_makeappssimple_abhimanyu_finance_manager_android_common_di_DateTimeKitModule,
                com_makeappssimple_abhimanyu_finance_manager_android_common_di_FeatureUseCaseModule,
                com_makeappssimple_abhimanyu_finance_manager_android_common_di_NavigationKitModule,
                com_makeappssimple_abhimanyu_finance_manager_android_platform_di_NotificationKitModule,
                com_makeappssimple_abhimanyu_finance_manager_android_common_di_PreferencesModule,
                com_makeappssimple_abhimanyu_finance_manager_android_common_di_RepositoryModule,
                com_makeappssimple_abhimanyu_finance_manager_android_common_di_RoomModule,
            )
        }

    private val coreBindings: Module = module {
        single<BuildConfigKit> { BuildConfigKitImpl() }
        single<AppVersionKit> { AppVersionKitImpl(get(), get()) }
        single(named(DISPATCHER_DEFAULT)) { kotlinx.coroutines.Dispatchers.Default }
        single(named(DISPATCHER_IO)) { kotlinx.coroutines.Dispatchers.Default }
        single(named(DISPATCHER_MAIN)) { kotlinx.coroutines.Dispatchers.Main }
        single(named(DISPATCHER_MAIN_IMMEDIATE)) { kotlinx.coroutines.Dispatchers.Main.immediate }
        single(named(DISPATCHER_UNCONFINED)) { kotlinx.coroutines.Dispatchers.Unconfined }
        single<CoroutineDispatcherProvider> {
            CoroutineDispatcherProviderImpl(
                defaultCoroutineDispatcher = get(named(DISPATCHER_DEFAULT)),
                ioCoroutineDispatcher = get(named(DISPATCHER_IO)),
                mainCoroutineDispatcher = get(named(DISPATCHER_MAIN)),
                mainImmediateCoroutineDispatcher = get(named(DISPATCHER_MAIN_IMMEDIATE)),
                unconfinedCoroutineDispatcher = get(named(DISPATCHER_UNCONFINED)),
            )
        }
        factory { kotlinx.coroutines.CoroutineScope(get<CoroutineDispatcherProvider>().mainImmediate + kotlinx.coroutines.SupervisorJob()) }
        single<JsonReaderKit> { JsonReaderKitImpl(get<Context>()) }
        single<JsonWriterKit> { JsonWriterKitImpl(get<Context>()) }
        single<UriDecoder> { UriDecoderImpl() }
        single<UriEncoder> { UriEncoderImpl() }
        single<LogKit> { LogKitImpl() }
    }
}
