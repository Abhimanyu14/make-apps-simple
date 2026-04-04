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

import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.app.BarcodesActivityViewModel
import com.makeappssimple.abhimanyu.barcodes.android.features.barcode_details.presentation.barcode_details.view_model.BarcodeDetailsScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.features.create_barcode.presentation.create_barcode.view_model.CreateBarcodeScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.features.home.presentation.home.view_model.HomeScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.features.scan_barcode.presentation.scan_barcode.view_model.ScanBarcodeScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.features.settings.presentation.credits.view_model.CreditsScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.features.settings.presentation.settings.view_model.SettingsScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.features.web_view.presentation.web_view.view_model.WebViewScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
import org.koin.dsl.module

internal fun initKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(
            modules = listOf(
                BarcodesAppModule().module,
                module {
                    viewModelOf(::BarcodesActivityViewModel)
                    viewModelOf(::HomeScreenViewModel)
                    viewModelOf(::CreateBarcodeScreenViewModel)
                    viewModelOf(::BarcodeDetailsScreenViewModel)
                    viewModelOf(::SettingsScreenViewModel)
                    viewModelOf(::CreditsScreenViewModel)
                    viewModelOf(::ScanBarcodeScreenViewModel)
                    viewModelOf(::WebViewScreenViewModel)
                },
            ),
        )
    }
}
