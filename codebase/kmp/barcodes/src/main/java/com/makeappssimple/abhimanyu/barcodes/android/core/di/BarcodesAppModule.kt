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

package com.makeappssimple.abhimanyu.barcodes.android.core.di

import com.makeappssimple.abhimanyu.barcode.generator.android.di.BarcodeGeneratorModule
import com.makeappssimple.abhimanyu.barcodes.android.core.data.database.di.RoomModule
import com.makeappssimple.abhimanyu.barcodes.android.core.data.di.DaosModule
import com.makeappssimple.abhimanyu.barcodes.android.features.scan_barcode.ui.barcode_scanner.di.BarcodeScannerModule
import com.makeappssimple.abhimanyu.common.app_version.di.AppVersionKitModule
import com.makeappssimple.abhimanyu.common.build_config.di.BuildConfigKitModule
import com.makeappssimple.abhimanyu.common.clipboard.di.ClipboardKitModule
import com.makeappssimple.abhimanyu.common.coroutines.di.CoroutineScopeModule
import com.makeappssimple.abhimanyu.common.coroutines.di.DispatcherProviderModule
import com.makeappssimple.abhimanyu.common.log_kit.di.LogKitModule
import com.makeappssimple.abhimanyu.common.uri_decoder.di.UriDecoderModule
import com.makeappssimple.abhimanyu.common.uri_encoder.di.UriEncoderModule
import com.makeappssimple.abhimanyu.core.date.time.di.DateTimeKitModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
    includes = [
        AppVersionKitModule::class,
        BarcodeGeneratorModule::class,
        BarcodeScannerModule::class,
        BuildConfigKitModule::class,
        ClipboardKitModule::class,
        CoroutineScopeModule::class,
        DaosModule::class,
        DateTimeKitModule::class,
        DispatcherProviderModule::class,
        FirebaseModule::class,
        LogKitModule::class,
        RoomModule::class,
        UriDecoderModule::class,
        UriEncoderModule::class,
    ],
)
@ComponentScan(
    "com.makeappssimple.abhimanyu.barcodes.android",
)
public class BarcodesAppModule
