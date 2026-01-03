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

package com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.home.home.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.base.ScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.home.home.snackbar.HomeScreenSnackbarType
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.model.BarcodeUiModel
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.feature.home.home.bottom_sheet.HomeCosmosBottomSheetType

@Stable
internal class HomeScreenUIState(
    val isDeleteBarcodeDialogVisible: Boolean = false,
    val isLoading: Boolean = false,
    val screenBottomSheetType: HomeCosmosBottomSheetType = HomeCosmosBottomSheetType.None,
    val screenSnackbarType: HomeScreenSnackbarType = HomeScreenSnackbarType.None,
    val allBarcodes: List<BarcodeUiModel> = emptyList(),
    val barcodeFormattedTimestamps: List<String> = emptyList(),

    val isModalBottomSheetVisible: Boolean = screenBottomSheetType != HomeCosmosBottomSheetType.None,
    val isBackHandlerEnabled: Boolean = screenBottomSheetType != HomeCosmosBottomSheetType.None,
) : ScreenUIState
