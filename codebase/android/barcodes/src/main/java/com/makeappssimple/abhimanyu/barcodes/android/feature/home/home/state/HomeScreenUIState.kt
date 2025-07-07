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

package com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.barcodes.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.barcodes.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.bottomsheet.HomeScreenBottomSheetType
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.screen.HomeScreenUIData

@Stable
internal class HomeScreenUIState(
    data: MyResult<HomeScreenUIData>?,
    private val unwrappedData: HomeScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    val isLoading: Boolean = unwrappedData.isNull(),
    val allBarcodes: List<Barcode> = unwrappedData?.allBarcodes.orEmpty(),
    val barcodeFormattedTimestamps: List<String> = unwrappedData?.barcodeFormattedTimestamps.orEmpty(),
    val screenBottomSheetType: HomeScreenBottomSheetType,
    val setScreenBottomSheetType: (HomeScreenBottomSheetType) -> Unit,
    val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(HomeScreenBottomSheetType.None)
    },
) : ScreenUIState

@Composable
internal fun rememberHomeScreenUIState(
    data: MyResult<HomeScreenUIData>?,
): HomeScreenUIState {
    var screenBottomSheetType: HomeScreenBottomSheetType by remember {
        mutableStateOf(
            value = HomeScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedScreenBottomSheetType: HomeScreenBottomSheetType ->
            screenBottomSheetType = updatedScreenBottomSheetType
        }
    return remember(
        data,
        screenBottomSheetType,
        setScreenBottomSheetType,
    ) {
        HomeScreenUIState(
            data = data,
            screenBottomSheetType = screenBottomSheetType,
            setScreenBottomSheetType = setScreenBottomSheetType,
        )
    }
}
