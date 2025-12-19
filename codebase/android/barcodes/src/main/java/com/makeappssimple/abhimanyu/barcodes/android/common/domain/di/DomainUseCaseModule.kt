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

package com.makeappssimple.abhimanyu.barcodes.android.common.domain.di

import com.makeappssimple.abhimanyu.barcodes.android.common.domain.repository.BarcodeRepository
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.use_case.barcode.DeleteBarcodesUseCase
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.use_case.barcode.GetAllBarcodesFlowUseCase
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.use_case.barcode.GetBarcodeByIdUseCase
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.use_case.barcode.InsertBarcodesUseCase
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.use_case.barcode.UpdateBarcodesUseCase
import com.makeappssimple.abhimanyu.common.core.date_time.DateTimeKit
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
internal class DomainUseCaseModule {
    @Single
    internal fun provideDeleteBarcodesUseCase(
        barcodeRepository: BarcodeRepository,
    ): DeleteBarcodesUseCase {
        return DeleteBarcodesUseCase(
            barcodeRepository = barcodeRepository,
        )
    }

    @Single
    internal fun provideGetAllBarcodesFlowUseCase(
        barcodeRepository: BarcodeRepository,
    ): GetAllBarcodesFlowUseCase {
        return GetAllBarcodesFlowUseCase(
            barcodeRepository = barcodeRepository,
        )
    }

    @Single
    internal fun provideGetBarcodeByIdUseCase(
        barcodeRepository: BarcodeRepository,
    ): GetBarcodeByIdUseCase {
        return GetBarcodeByIdUseCase(
            barcodeRepository = barcodeRepository,
        )
    }

    @Single
    internal fun provideInsertBarcodesUseCase(
        barcodeRepository: BarcodeRepository,
        dateTimeKit: DateTimeKit,
    ): InsertBarcodesUseCase {
        return InsertBarcodesUseCase(
            barcodeRepository = barcodeRepository,
            dateTimeKit = dateTimeKit,
        )
    }

    @Single
    internal fun provideUpdateBarcodesUseCase(
        barcodeRepository: BarcodeRepository,
    ): UpdateBarcodesUseCase {
        return UpdateBarcodesUseCase(
            barcodeRepository = barcodeRepository,
        )
    }
}
