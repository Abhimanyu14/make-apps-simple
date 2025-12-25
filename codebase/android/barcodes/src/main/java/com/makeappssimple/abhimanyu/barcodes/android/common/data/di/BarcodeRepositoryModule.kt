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

package com.makeappssimple.abhimanyu.barcodes.android.common.data.di

import com.makeappssimple.abhimanyu.barcodes.android.common.data.barcode.BarcodeDao
import com.makeappssimple.abhimanyu.barcodes.android.common.data.mapper.BarcodeDataToDomainMapper
import com.makeappssimple.abhimanyu.barcodes.android.common.data.mapper.BarcodeDomainToDataMapper
import com.makeappssimple.abhimanyu.barcodes.android.common.data.repository.barcode.BarcodeRepositoryImpl
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.repository.BarcodeRepository
import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
internal class BarcodeRepositoryModule {
    @Single
    internal fun provideBarcodeDataToDomainMapper(): BarcodeDataToDomainMapper {
        return BarcodeDataToDomainMapper()
    }

    @Single
    internal fun provideBarcodeDomainToDataMapper(): BarcodeDomainToDataMapper {
        return BarcodeDomainToDataMapper()
    }

    @Single
    internal fun provideBarcodeRepository(
        barcodeDao: BarcodeDao,
        dispatcherProvider: DispatcherProvider,
        barcodeDataToDomainMapper: BarcodeDataToDomainMapper,
        barcodeDomainToDataMapper: BarcodeDomainToDataMapper,
    ): BarcodeRepository {
        return BarcodeRepositoryImpl(
            barcodeDao = barcodeDao,
            dispatcherProvider = dispatcherProvider,
            barcodeDataToDomainMapper = barcodeDataToDomainMapper,
            barcodeDomainToDataMapper = barcodeDomainToDataMapper,
        )
    }
}
