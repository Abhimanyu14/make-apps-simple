package com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.state

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.makeappssimple.abhimanyu.barcodes.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.screen.BarcodeDetailsScreenUIData

@Stable
internal class BarcodeDetailsScreenUIState(
    data: MyResult<BarcodeDetailsScreenUIData>?,
    private val unwrappedData: BarcodeDetailsScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    val barcode: Barcode? = unwrappedData?.barcode,
    val formattedTimestamp: String = unwrappedData?.formattedTimestamp.orEmpty(),
    val bitmap: Bitmap? = unwrappedData?.bitmap,
    val isDeleteBarcodeDialogVisible: Boolean,
    val setIsDeleteBarcodeDialogVisible: (Boolean) -> Unit,
) : ScreenUIState

@Composable
internal fun rememberBarcodeDetailsScreenUIState(
    data: MyResult<BarcodeDetailsScreenUIData>?,
): BarcodeDetailsScreenUIState {
    val (isDeleteBarcodeDialogVisible: Boolean, setIsDeleteBarcodeDialogVisible: (Boolean) -> Unit) = rememberSaveable {
        mutableStateOf(false)
    }

    return remember(
        data,
        isDeleteBarcodeDialogVisible,
        setIsDeleteBarcodeDialogVisible,
    ) {
        BarcodeDetailsScreenUIState(
            data = data,
            isDeleteBarcodeDialogVisible = isDeleteBarcodeDialogVisible,
            setIsDeleteBarcodeDialogVisible = setIsDeleteBarcodeDialogVisible,
        )
    }
}
