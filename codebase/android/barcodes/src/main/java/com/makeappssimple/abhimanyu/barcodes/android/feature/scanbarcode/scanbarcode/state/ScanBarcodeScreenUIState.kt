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
