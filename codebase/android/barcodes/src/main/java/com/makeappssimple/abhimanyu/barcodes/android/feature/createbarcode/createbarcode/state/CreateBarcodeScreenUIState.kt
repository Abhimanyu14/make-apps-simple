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

package com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.barcodes.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.barcodes.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.screen.CreateBarcodeScreenUIData

@Stable
internal class CreateBarcodeScreenUIState(
    data: MyResult<CreateBarcodeScreenUIData>?,
    private val unwrappedData: CreateBarcodeScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    val isBarcodeValueEditable: Boolean = unwrappedData?.isBarcodeValueEditable.orFalse(),
    val barcodeValue: String = unwrappedData?.barcodeValue.orEmpty(),
    val barcodeName: String = unwrappedData?.barcodeName.orEmpty(),
) : ScreenUIState

@Composable
internal fun rememberCreateBarcodeScreenUIState(
    data: MyResult<CreateBarcodeScreenUIData>?,
): CreateBarcodeScreenUIState {
    return remember(
        data,
    ) {
        CreateBarcodeScreenUIState(
            data = data,
        )
    }
}
