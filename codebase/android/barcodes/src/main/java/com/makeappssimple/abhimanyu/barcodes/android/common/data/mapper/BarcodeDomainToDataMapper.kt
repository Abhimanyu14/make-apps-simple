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

package com.makeappssimple.abhimanyu.barcodes.android.common.data.mapper

import com.makeappssimple.abhimanyu.barcodes.android.common.data.model.BarcodeDataModel
import com.makeappssimple.abhimanyu.barcodes.android.common.data.model.BarcodeSourceDataModel
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.model.BarcodeDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.model.BarcodeSourceDomainModel
import org.koin.core.annotation.Single

@Single
internal class BarcodeDomainToDataMapper {
    fun toDataModel(
        barcodeDomainModel: BarcodeDomainModel,
    ): BarcodeDataModel {
        return BarcodeDataModel(
            source = toDataModel(
                barcodeSourceDomainModel = barcodeDomainModel.source,
            ),
            format = barcodeDomainModel.format,
            id = barcodeDomainModel.id,
            timestamp = barcodeDomainModel.timestamp,
            name = barcodeDomainModel.name,
            value = barcodeDomainModel.value,
        )
    }

    private fun toDataModel(
        barcodeSourceDomainModel: BarcodeSourceDomainModel,
    ): BarcodeSourceDataModel {
        return when (barcodeSourceDomainModel) {
            BarcodeSourceDomainModel.CREATED -> BarcodeSourceDataModel.CREATED
            BarcodeSourceDomainModel.SCANNED -> BarcodeSourceDataModel.SCANNED
        }
    }
}
