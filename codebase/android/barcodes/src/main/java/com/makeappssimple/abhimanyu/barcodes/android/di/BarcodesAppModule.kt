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

package com.makeappssimple.abhimanyu.barcodes.android.di

import com.makeappssimple.abhimanyu.common.di.AppVersionKitModule
import com.makeappssimple.abhimanyu.common.di.BuildConfigKitModule
import com.makeappssimple.abhimanyu.common.di.ClipboardKitModule
import com.makeappssimple.abhimanyu.common.di.CoroutineScopeModule
import com.makeappssimple.abhimanyu.common.di.DateTimeKitModule
import com.makeappssimple.abhimanyu.common.di.DispatcherProviderModule
import com.makeappssimple.abhimanyu.common.di.LogKitModule
import com.makeappssimple.abhimanyu.common.di.StringDecoderModule
import com.makeappssimple.abhimanyu.common.di.StringEncoderModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
    includes = [
        AnalyticsModule::class,
        AppVersionKitModule::class,
        BarcodeGeneratorModule::class,
        BarcodeRepositoryModule::class,
        BuildConfigKitModule::class,
        ClipboardKitModule::class,
        CoroutineScopeModule::class,
        DaosModule::class,
        DateTimeKitModule::class,
        DispatcherProviderModule::class,
        FirebaseModule::class,
        LogKitModule::class,
        NavigationKitModule::class,
        RoomModule::class,
        StringDecoderModule::class,
        StringEncoderModule::class,
    ],
)
@ComponentScan("com.makeappssimple.abhimanyu.barcodes.android")
public class BarcodesAppModule
