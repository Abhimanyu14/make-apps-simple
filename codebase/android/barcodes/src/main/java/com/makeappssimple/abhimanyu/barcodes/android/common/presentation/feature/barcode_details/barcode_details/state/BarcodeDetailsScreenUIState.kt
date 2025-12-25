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

package com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.barcode_details.barcode_details.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.ImageBitmap
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.model.BarcodeSourceDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.base.ScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.barcode_details.barcode_details.snackbar.BarcodeDetailsScreenSnackbarType

@Stable
internal data class BarcodeDetailsScreenUIState(
    val barcodeSource: BarcodeSourceDomainModel = BarcodeSourceDomainModel.CREATED,
    val isDeleteBarcodeDialogVisible: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val screenSnackbarType: BarcodeDetailsScreenSnackbarType = BarcodeDetailsScreenSnackbarType.None,
    val barcodeId: Int = -1,
    val barcodeName: String? = null,
    val barcodeValue: String = "",
    val formattedTimestamp: String = "",
    val formattedTimestampLabelId: Int? = null,
    val barcodeImageBitmap: ImageBitmap? = null,
) : ScreenUIState
