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

package com.makeappssimple.abhimanyu.barcodes.android.feature.barcode_details.barcode_details.view_model

import android.os.Build
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.barcodes.android.core.analytics.AnalyticsKit
import com.makeappssimple.abhimanyu.barcodes.android.core.barcode_generator.BarcodeGenerator
import com.makeappssimple.abhimanyu.barcodes.android.core.data.repository.BarcodeRepository
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.theme.MyColor
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcode_details.barcode_details.state.BarcodeDetailsScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcode_details.barcode_details.state.BarcodeDetailsScreenUIStateEvents
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcode_details.navigation.BarcodeDetailsScreenArgs
import com.makeappssimple.abhimanyu.common.core.build_config.BuildConfigKit
import com.makeappssimple.abhimanyu.common.core.clipboard.ClipboardKit
import com.makeappssimple.abhimanyu.common.core.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.common.core.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.common.log_kit.LogKit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BarcodeDetailsScreenViewModel(
    analyticsKit: AnalyticsKit,
    coroutineScope: CoroutineScope,
    logKit: LogKit,
    navigationKit: NavigationKit,
    savedStateHandle: SavedStateHandle,
    private val barcodeGenerator: BarcodeGenerator,
    private val barcodeRepository: BarcodeRepository,
    private val buildConfigKit: BuildConfigKit,
    private val clipboardKit: ClipboardKit,
    private val dateTimeKit: DateTimeKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    analyticsKit = analyticsKit,
    logKit = logKit,
    navigationKit = navigationKit,
    screen = Screen.BarcodeDetails,
) {
    // region screen args
    private val screenArgs = BarcodeDetailsScreenArgs(
        savedStateHandle = savedStateHandle,
    )
    // endregion

    // region state
    private val barcodeBitmapSize = MutableStateFlow(
        value = 0,
    )
    private val barcode: MutableStateFlow<Barcode?> = MutableStateFlow(
        value = null,
    )
    private val isDeleteBarcodeDialogVisible = MutableStateFlow(
        value = false,
    )
    private val barcodeBitmap: StateFlow<ImageBitmap?> = combine(
        barcode,
        barcodeBitmapSize,
    ) {
            barcode,
            barcodeBitmapSize,
        ->
        if (barcode == null || barcodeBitmapSize == 0) {
            null
        } else {
            barcodeGenerator.generateBarcode(
                data = barcode.value,
                visionBarcodeFormat = barcode.format,
                width = barcodeBitmapSize,
                height = barcodeBitmapSize,
                barcodeColor = MyColor.ON_BACKGROUND.color,
                backgroundColor = MyColor.BACKGROUND.color,
            )
        }
    }.defaultObjectStateIn(
        scope = viewModelScope,
        initialValue = null,
    )
    // endregion

    // region uiState and uiStateEvents
    val uiState: StateFlow<BarcodeDetailsScreenUIState> = combine(
        barcode,
        barcodeBitmap,
        isDeleteBarcodeDialogVisible,
    ) {
            barcode,
            barcodeBitmap,
            isDeleteBarcodeDialogVisible,
        ->
        if (barcode == null) {
            BarcodeDetailsScreenUIState(
                isLoading = true,
            )
        } else {
            BarcodeDetailsScreenUIState(
                barcodeSource = barcode.source,
                isDeleteBarcodeDialogVisible = isDeleteBarcodeDialogVisible,
                barcodeId = barcode.id,
                barcodeName = barcode.name,
                barcodeValue = barcode.value,
                formattedTimestamp = dateTimeKit.getFormattedDateAndTime(
                    timestamp = barcode.timestamp,
                ),
                barcodeImageBitmap = barcodeBitmap,
            )
        }
    }.defaultObjectStateIn(
        scope = viewModelScope,
        initialValue = BarcodeDetailsScreenUIState(
            isLoading = true,
        ),
    )
    val uiStateEvents: BarcodeDetailsScreenUIStateEvents =
        BarcodeDetailsScreenUIStateEvents(
            updateBarcodeBitmapSize = ::updateBarcodeBitmapSize,
            updateIsDeleteBarcodeDialogVisible = ::updateIsDeleteBarcodeDialogVisible,
        )
    // endregion

    override fun fetchData(): Job {
        return viewModelScope.launch {
            fetchBarcode()
        }
    }

    fun deleteBarcode() {
        viewModelScope.launch {
            barcode.value?.let {
                barcodeRepository.deleteBarcodes(
                    barcodes = arrayOf(it),
                )
            }
        }
        navigateToHomeScreen()
    }

    fun copyToClipboard(
        label: String,
        text: String,
    ): Boolean {
        return clipboardKit.copyToClipboard(
            label = label,
            text = text,
        )
    }

    fun shouldShowCopiedToClipboardToastMessage(): Boolean {
        return !buildConfigKit.isAndroidApiEqualToOrAbove(Build.VERSION_CODES.TIRAMISU)
    }

    // region state events
    private fun updateBarcodeBitmapSize(
        updatedBarcodeBitmapSize: Int,
    ) {
        barcodeBitmapSize.update {
            updatedBarcodeBitmapSize
        }
    }

    private fun updateIsDeleteBarcodeDialogVisible(
        updatedIsDeleteBarcodeDialogVisible: Boolean,
    ) {
        isDeleteBarcodeDialogVisible.update {
            updatedIsDeleteBarcodeDialogVisible
        }
    }
    // endregion

    private suspend fun fetchBarcode() {
        screenArgs.originalBarcodeId?.let {
            barcode.value = barcodeRepository.getBarcode(
                id = it,
            )
        }
    }
}
