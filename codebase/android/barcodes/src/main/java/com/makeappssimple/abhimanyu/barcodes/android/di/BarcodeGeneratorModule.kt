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

import com.makeappssimple.abhimanyu.barcodes.android.core.barcodegenerator.BarcodeGenerator
import com.makeappssimple.abhimanyu.barcodes.android.core.barcodegenerator.BarcodeGeneratorImpl
import com.makeappssimple.abhimanyu.barcodes.android.core.common.coroutines.DispatcherProvider
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
public class BarcodeGeneratorModule {
    @Single
    internal fun provideBarcodeGenerator(
        dispatcherProvider: DispatcherProvider,
    ): BarcodeGenerator {
        return BarcodeGeneratorImpl(
            dispatcherProvider = dispatcherProvider,
        )
    }
}
