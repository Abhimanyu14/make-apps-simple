package com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.barcodes.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.barcodes.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.screen.CreateBarcodeScreenUIData

@Stable
internal class CreateBarcodeScreenUIState(
    data: MyResult<CreateBarcodeScreenUIData>?,
    private val unwrappedData: CreateBarcodeScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    val isBarcodeValueEditable: Boolean = unwrappedData?.isBarcodeValueEditable.orFalse(),
    val barcodeValue: String = unwrappedData?.barcodeValue.orEmpty(),
    val barcodeName: String = unwrappedData?.barcodeName.orEmpty(),
) : ScreenUIState

@Composable
internal fun rememberCreateBarcodeScreenUIState(
    data: MyResult<CreateBarcodeScreenUIData>?,
): CreateBarcodeScreenUIState {
    return remember(
        data,
    ) {
        CreateBarcodeScreenUIState(
            data = data,
        )
    }
}
