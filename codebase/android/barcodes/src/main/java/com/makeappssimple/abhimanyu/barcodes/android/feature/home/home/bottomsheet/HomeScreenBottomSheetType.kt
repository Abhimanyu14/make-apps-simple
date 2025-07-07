package com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.bottomsheet

import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenBottomSheetType

internal sealed class HomeScreenBottomSheetType : ScreenBottomSheetType {
    data object Menu : HomeScreenBottomSheetType()
    data object None : HomeScreenBottomSheetType()
}
