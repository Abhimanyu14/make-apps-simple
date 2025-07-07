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

package com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.scanbarcode.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.barcodes.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.barcodes.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.scanbarcode.screen.ScanBarcodeScreenUIData

@Stable
internal class ScanBarcodeScreenUIState(
    data: MyResult<ScanBarcodeScreenUIData>?,
    private val unwrappedData: ScanBarcodeScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    val isDeeplink: Boolean = unwrappedData?.isDeeplink.orFalse(),
) : ScreenUIState

@Composable
internal fun rememberScanBarcodeScreenUIState(
    data: MyResult<ScanBarcodeScreenUIData>?,
): ScanBarcodeScreenUIState {
    return remember(
        data,
    ) {
        ScanBarcodeScreenUIState(
            data = data,
        )
    }
}
