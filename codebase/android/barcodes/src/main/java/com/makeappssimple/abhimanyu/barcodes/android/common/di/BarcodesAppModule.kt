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

package com.makeappssimple.abhimanyu.barcodes.android.common.di

import com.makeappssimple.abhimanyu.barcodes.android.common.data.database.di.RoomModule
import com.makeappssimple.abhimanyu.barcodes.android.common.data.di.BarcodeRepositoryModule
import com.makeappssimple.abhimanyu.barcodes.android.common.data.di.DaosModule
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.di.DomainUseCaseModule
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.di.BarcodePresentationModule
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.di.NavigationKitModule
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.analytics.di.AnalyticsModule
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.barcode_generator.di.BarcodeGeneratorModule
import com.makeappssimple.abhimanyu.common.di.AppVersionKitModule
import com.makeappssimple.abhimanyu.common.di.BuildConfigKitModule
import com.makeappssimple.abhimanyu.common.di.ClipboardKitModule
import com.makeappssimple.abhimanyu.common.di.CoroutineScopeModule
import com.makeappssimple.abhimanyu.common.di.DateTimeKitModule
import com.makeappssimple.abhimanyu.common.di.DispatcherProviderModule
import com.makeappssimple.abhimanyu.common.di.LogKitModule
import com.makeappssimple.abhimanyu.common.di.UriDecoderModule
import com.makeappssimple.abhimanyu.common.di.UriEncoderModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
    includes = [
        AnalyticsModule::class,
        AppVersionKitModule::class,
        BarcodeGeneratorModule::class,
        BarcodePresentationModule::class,
        BarcodeRepositoryModule::class,
        BuildConfigKitModule::class,
        ClipboardKitModule::class,
        CoroutineScopeModule::class,
        DaosModule::class,
        DomainUseCaseModule::class,
        DateTimeKitModule::class,
        DispatcherProviderModule::class,
        FirebaseModule::class,
        LogKitModule::class,
        NavigationKitModule::class,
        RoomModule::class,
        UriDecoderModule::class,
        UriEncoderModule::class,
    ],
)
@ComponentScan("com.makeappssimple.abhimanyu.barcodes.android")
public class BarcodesAppModule
