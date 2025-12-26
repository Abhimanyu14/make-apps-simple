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

package com.makeappssimple.abhimanyu.barcodes.android.common.presentation.mapper

import com.makeappssimple.abhimanyu.barcodes.android.common.domain.model.BarcodeDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.model.BarcodeSourceDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.model.BarcodeFormatUiModel
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.model.BarcodeSourceUiModel
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.model.BarcodeUiModel
import org.koin.core.annotation.Single

@Single
internal class BarcodeDomainToUiMapper {
    fun toUiModel(
        barcodeDomainModel: BarcodeDomainModel,
    ): BarcodeUiModel {
        return BarcodeUiModel(
            source = toUiModel(
                barcodeSourceDomainModel = barcodeDomainModel.source,
            ),
            format = BarcodeFormatUiModel.fromValue(
                value = barcodeDomainModel.format,
            ) ?: BarcodeFormatUiModel.QrCode,
            id = barcodeDomainModel.id,
            timestamp = barcodeDomainModel.timestamp,
            name = barcodeDomainModel.name,
            value = barcodeDomainModel.value,
        )
    }

    private fun toUiModel(
        barcodeSourceDomainModel: BarcodeSourceDomainModel,
    ): BarcodeSourceUiModel {
        return when (barcodeSourceDomainModel) {
            BarcodeSourceDomainModel.CREATED -> BarcodeSourceUiModel.CREATED
            BarcodeSourceDomainModel.SCANNED -> BarcodeSourceUiModel.SCANNED
        }
    }
}
