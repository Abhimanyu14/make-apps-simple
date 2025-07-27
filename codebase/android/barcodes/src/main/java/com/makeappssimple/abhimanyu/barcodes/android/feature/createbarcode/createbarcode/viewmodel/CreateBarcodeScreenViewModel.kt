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

package com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.barcodes.android.core.analytics.AnalyticsKit
import com.makeappssimple.abhimanyu.barcodes.android.core.common.clipboard.ClipboardKit
import com.makeappssimple.abhimanyu.barcodes.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.barcodes.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.barcodes.android.core.data.repository.BarcodeRepository
import com.makeappssimple.abhimanyu.barcodes.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.model.BarcodeFormat
import com.makeappssimple.abhimanyu.barcodes.android.core.model.BarcodeSource
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.state.CreateBarcodeScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.state.CreateBarcodeScreenUIStateEvents
import com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.navigation.CreateBarcodeScreenArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import kotlin.time.ExperimentalTime

@KoinViewModel
internal class CreateBarcodeScreenViewModel(
    analyticsKit: AnalyticsKit,
    coroutineScope: CoroutineScope,
    logKit: LogKit,
    navigationKit: NavigationKit,
    savedStateHandle: SavedStateHandle,
    private val barcodeRepository: BarcodeRepository,
    private val dateTimeKit: DateTimeKit,
    private val clipboardKit: ClipboardKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    analyticsKit = analyticsKit,
    logKit = logKit,
    navigationKit = navigationKit,
    screen = Screen.CreateBarcode,
) {
    // region screen args
    private val screenArgs = CreateBarcodeScreenArgs(
        savedStateHandle = savedStateHandle,
    )
    // endregion

    // region state
    private val originalBarcode: MutableStateFlow<Barcode?> = MutableStateFlow(
        value = null,
    )
    private val barcodeName = MutableStateFlow(
        value = "",
    )
    private val barcodeValue = MutableStateFlow(
        value = "",
    )
    // endregion

    // region uiState and uiStateEvents
    val uiState: StateFlow<CreateBarcodeScreenUIState> = combine(
        originalBarcode,
        barcodeName,
        barcodeValue,
    ) {
            originalBarcode,
            barcodeName,
            barcodeValue,
        ->
        CreateBarcodeScreenUIState(
            barcodeName = barcodeName,
            barcodeValue = barcodeValue,
            isBarcodeValueEditable = originalBarcode == null,
        )
    }.defaultObjectStateIn(
        scope = viewModelScope,
        initialValue = CreateBarcodeScreenUIState(),
    )
    val uiStateEvents: CreateBarcodeScreenUIStateEvents =
        CreateBarcodeScreenUIStateEvents(
            updateBarcodeName = ::updateBarcodeName,
            updateBarcodeValue = ::updateBarcodeValue,
        )
    // endregion

    override fun fetchData(): Job {
        return viewModelScope.launch {
            fetchBarcode()
        }
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

    @OptIn(ExperimentalTime::class)
    fun saveBarcode() {
        viewModelScope.launch {
            val originalBarcodeValue = originalBarcode.value
            if (originalBarcodeValue != null) {
                barcodeRepository.updateBarcodes(
                    originalBarcodeValue.copy(
                        name = barcodeName.value,
                    ),
                )
            } else {
                barcodeRepository.insertBarcodes(
                    Barcode(
                        source = BarcodeSource.CREATED,
                        format = BarcodeFormat.QrCode.value,
                        timestamp = dateTimeKit.getCurrentTimeMillis(),
                        name = barcodeName.value,
                        value = barcodeValue.value,
                    )
                )
            }
        }
    }

    // region state events
    private fun updateBarcodeName(
        updatedBarcodeName: String,
    ) {
        barcodeName.update {
            updatedBarcodeName
        }
    }

    private fun updateBarcodeValue(
        updatedBarcodeValue: String,
    ) {
        barcodeValue.update {
            updatedBarcodeValue
        }
    }
    // endregion

    private suspend fun fetchBarcode() {
        screenArgs.barcodeId?.let { barcodeId ->
            originalBarcode.update {
                barcodeRepository.getBarcode(
                    id = barcodeId,
                )
            }
            barcodeName.update {
                originalBarcode.value?.name.orEmpty()
            }
            barcodeValue.update {
                originalBarcode.value?.value.orEmpty()
            }
        }
    }
}
