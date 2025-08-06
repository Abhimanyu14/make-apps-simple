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

package com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.event

import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIEvent
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.bottom_sheet.HomeMenuBottomSheetEvent

internal sealed class HomeScreenUIEvent : ScreenUIEvent {
    data object OnAddFloatingActionButtonClick : HomeScreenUIEvent()
    data object OnBottomSheetDismiss : HomeScreenUIEvent()

    data class OnHomeMenuBottomSheetEvent(
        val event: HomeMenuBottomSheetEvent,
    ) : HomeScreenUIEvent()

    sealed class OnBarcodeDeletedSnackbar {
        data class ActionButtonClick(
            val barcode: Barcode,
        ) : HomeScreenUIEvent()
    }

    sealed class OnHomeDeleteBarcodeDialog {
        data class ConfirmButtonClick(
            val barcodes: List<Barcode>,
        ) : HomeScreenUIEvent()

        data object Dismiss : HomeScreenUIEvent()
        data object DismissButtonClick : HomeScreenUIEvent()
    }

    sealed class OnListItem {
        data class Click(
            val barcode: Barcode,
        ) : HomeScreenUIEvent()

        data class SwipeToEnd(
            val barcodes: List<Barcode>,
        ) : HomeScreenUIEvent()
    }

    sealed class OnTopAppBar {
        data object DeleteBarcodeButtonClick : HomeScreenUIEvent()
        data object SettingsButtonClick : HomeScreenUIEvent()
    }
}
