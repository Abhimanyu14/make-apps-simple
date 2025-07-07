package com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.event

import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIEvent
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.bottomsheet.HomeMenuBottomSheetEvent

internal sealed class HomeScreenUIEvent : ScreenUIEvent {
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
        data object SettingsButtonClick : HomeScreenUIEvent()
    }
}
