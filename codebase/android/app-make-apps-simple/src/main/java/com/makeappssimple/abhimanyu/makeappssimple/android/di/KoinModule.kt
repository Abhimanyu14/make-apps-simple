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

package com.makeappssimple.abhimanyu.makeappssimple.android.di

import com.makeappssimple.abhimanyu.barcodes.android.di.AnalyticsModule
import com.makeappssimple.abhimanyu.barcodes.android.di.AppVersionKitModule
import com.makeappssimple.abhimanyu.barcodes.android.di.BarcodeGeneratorModule
import com.makeappssimple.abhimanyu.barcodes.android.di.BarcodeRepositoryModule
import com.makeappssimple.abhimanyu.barcodes.android.di.BarcodesAppModule
import com.makeappssimple.abhimanyu.barcodes.android.di.BuildConfigKitModule
import com.makeappssimple.abhimanyu.barcodes.android.di.ClipboardKitModule
import com.makeappssimple.abhimanyu.barcodes.android.di.CoroutineScopeModule
import com.makeappssimple.abhimanyu.barcodes.android.di.DaosModule
import com.makeappssimple.abhimanyu.barcodes.android.di.DateTimeKitModule
import com.makeappssimple.abhimanyu.barcodes.android.di.DispatcherProviderModule
import com.makeappssimple.abhimanyu.barcodes.android.di.FirebaseModule
import com.makeappssimple.abhimanyu.barcodes.android.di.LogKitModule
import com.makeappssimple.abhimanyu.barcodes.android.di.NavigationKitModule
import com.makeappssimple.abhimanyu.barcodes.android.di.PlatformModule
import com.makeappssimple.abhimanyu.barcodes.android.di.RoomModule
import com.makeappssimple.abhimanyu.barcodes.android.di.StringDecoderModule
import com.makeappssimple.abhimanyu.barcodes.android.di.StringEncoderModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

internal fun initKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(
            platformModule,
            viewModelModule,

            // Barcodes
            AnalyticsModule().module,
            AppVersionKitModule().module,
            BarcodesAppModule().module,
            BarcodeGeneratorModule().module,
            BarcodeRepositoryModule().module,
            BuildConfigKitModule().module,
            ClipboardKitModule().module,
            CoroutineScopeModule().module,
            DaosModule().module,
            DateTimeKitModule().module,
            DispatcherProviderModule().module,
            FirebaseModule().module,
            LogKitModule().module,
            NavigationKitModule().module,
            PlatformModule().module,
            RoomModule().module,
            StringDecoderModule().module,
            StringEncoderModule().module,
        )
    }
}
