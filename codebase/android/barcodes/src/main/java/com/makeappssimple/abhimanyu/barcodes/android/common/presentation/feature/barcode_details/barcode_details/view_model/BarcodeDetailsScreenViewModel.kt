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

package com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.barcode_details.barcode_details.view_model

import android.os.Build
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.model.BarcodeDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.use_case.barcode.DeleteBarcodesUseCase
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.use_case.barcode.GetBarcodeByIdUseCase
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.base.ScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.barcode_details.barcode_details.state.BarcodeDetailsScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.barcode_details.barcode_details.state.BarcodeDetailsScreenUIStateEvents
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.barcode_details.navigation.BarcodeDetailsScreenArgs
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.NavigationKit
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.analytics.AnalyticsKit
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.barcode_generator.BarcodeGenerator
import com.makeappssimple.abhimanyu.common.core.build_config.BuildConfigKit
import com.makeappssimple.abhimanyu.common.core.clipboard.ClipboardKit
import com.makeappssimple.abhimanyu.common.core.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.result.MyResult
import com.makeappssimple.abhimanyu.common.core.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosColor
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
    private val buildConfigKit: BuildConfigKit,
    private val clipboardKit: ClipboardKit,
    private val dateTimeKit: DateTimeKit,
    private val deleteBarcodesUseCase: DeleteBarcodesUseCase,
    private val getBarcodeByIdUseCase: GetBarcodeByIdUseCase,
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
    private val barcode: MutableStateFlow<BarcodeDomainModel?> =
        MutableStateFlow(
            value = null,
        )
    private val isDeleteBarcodeDialogVisible = MutableStateFlow(
        value = false,
    )
    private val isError = MutableStateFlow(
        value = false,
    )
    private val barcodeBitmap: StateFlow<ImageBitmap?> = combine(
        flow = barcode,
        flow2 = barcodeBitmapSize,
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
                barcodeColor = CosmosColor.ON_BACKGROUND.color,
                backgroundColor = CosmosColor.BACKGROUND.color,
            )
        }
    }.defaultObjectStateIn(
        scope = viewModelScope,
        initialValue = null,
    )
    // endregion

    // region uiState and uiStateEvents
    val uiState: StateFlow<BarcodeDetailsScreenUIState> = combine(
        flow = barcode,
        flow2 = barcodeBitmap,
        flow3 = isDeleteBarcodeDialogVisible,
        flow4 = isError,
    ) {
            barcode,
            barcodeBitmap,
            isDeleteBarcodeDialogVisible,
            isError,
        ->
        if (isError) {
            BarcodeDetailsScreenUIState(
                isError = true,
            )
        } else if (barcode == null) {
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
                val result = deleteBarcodesUseCase(
                    barcodes = arrayOf(it),
                )
                when (result) {
                    is MyResult.Error -> {
                        // TODO(Abhi): Handle failure
                        result.exception?.printStackTrace()
                    }

                    is MyResult.Loading -> {}

                    is MyResult.Success -> {}
                }
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
        screenArgs.originalBarcodeId?.let { originalBarcodeId ->
            val result = getBarcodeByIdUseCase(
                id = originalBarcodeId,
            )
            isError.update {
                false
            }
            when (result) {
                is MyResult.Error -> {
                    isError.update {
                        true
                    }
                }

                is MyResult.Loading -> {}

                is MyResult.Success -> {
                    barcode.value = result.data
                }
            }
        }
    }
}
