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

package com.makeappssimple.abhimanyu.barcodes.android.common.data.mapper

import com.makeappssimple.abhimanyu.barcodes.android.common.data.model.BarcodeDataModel
import com.makeappssimple.abhimanyu.barcodes.android.common.data.model.BarcodeSourceDataModel
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.model.BarcodeDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.model.BarcodeSourceDomainModel
import org.koin.core.annotation.Single

@Single
internal class BarcodeDataToDomainMapper {
    fun toDomainModel(
        barcodeDataModel: BarcodeDataModel,
    ): BarcodeDomainModel {
        return BarcodeDomainModel(
            source = toDomainModel(
                barcodeSourceDataModel = barcodeDataModel.source,
            ),
            format = barcodeDataModel.format,
            id = barcodeDataModel.id,
            timestamp = barcodeDataModel.timestamp,
            name = barcodeDataModel.name,
            value = barcodeDataModel.value,
        )
    }

    private fun toDomainModel(
        barcodeSourceDataModel: BarcodeSourceDataModel,
    ): BarcodeSourceDomainModel {
        return when (barcodeSourceDataModel) {
            BarcodeSourceDataModel.CREATED -> {
                BarcodeSourceDomainModel.CREATED
            }

            BarcodeSourceDataModel.SCANNED -> {
                BarcodeSourceDomainModel.SCANNED
            }
        }
    }
}
