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

package com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.event

import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIEvent

internal sealed class BarcodeDetailsScreenUIEvent : ScreenUIEvent {
    data object OnTopAppBarNavigationButtonClick : BarcodeDetailsScreenUIEvent()

    sealed class OnBarcodeDetailsTopAppBar {
        data object DeleteBarcodeButtonClick : BarcodeDetailsScreenUIEvent()
        data class EditBarcodeButtonClick(
            val barcodeId: Int,
        ) : BarcodeDetailsScreenUIEvent()
    }

    sealed class OnBarcodeDetailsDeleteBarcodeDialog {
        data object ConfirmButtonClick : BarcodeDetailsScreenUIEvent()
        data object Dismiss : BarcodeDetailsScreenUIEvent()
        data object DismissButtonClick : BarcodeDetailsScreenUIEvent()
    }

    data class OnCopyBarcodeValueButtonClick(
        val barcodeValue: String,
    ) : BarcodeDetailsScreenUIEvent()
}
