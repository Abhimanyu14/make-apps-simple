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

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.bottomsheet.HomeScreenBottomSheetType

@Stable
internal class HomeScreenUIState(
    val isDeleteBarcodeDialogVisible: Boolean = false,
    val isLoading: Boolean = false,
    val screenBottomSheetType: HomeScreenBottomSheetType = HomeScreenBottomSheetType.None,
    val allBarcodes: List<Barcode> = emptyList(),
    val barcodeFormattedTimestamps: List<String> = emptyList(),

    val isModalBottomSheetVisible: Boolean = screenBottomSheetType != HomeScreenBottomSheetType.None,
    val isBackHandlerEnabled: Boolean = screenBottomSheetType != HomeScreenBottomSheetType.None,
) : ScreenUIState
