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

package com.makeappssimple.abhimanyu.barcodes.android.common.domain.use_case.barcode

import com.makeappssimple.abhimanyu.barcodes.android.common.domain.model.BarcodeDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.model.BarcodeSourceDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.repository.BarcodeRepository
import com.makeappssimple.abhimanyu.common.core.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.common.core.result.MyResult
import org.koin.core.annotation.Single

@Single
internal class InsertBarcodesUseCase(
    private val barcodeRepository: BarcodeRepository,
    private val dateTimeKit: DateTimeKit,
) {
    suspend operator fun invoke(
        source: BarcodeSourceDomainModel,
        format: Int,
        id: Int = 0,
        timestamp: Long? = null,
        name: String? = null,
        value: String,
    ): MyResult<Long> {
        return barcodeRepository.insertBarcode(
            BarcodeDomainModel(
                source = source,
                format = format,
                id = id,
                timestamp = timestamp ?: dateTimeKit.getCurrentTimeMillis(),
                name = name,
                value = value,
            ),
        )
    }
}
