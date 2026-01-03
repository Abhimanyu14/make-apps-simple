/*
 * Copyright 2025-2026 Abhimanyu
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

package com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.create_barcode.create_barcode.view_model

import android.os.Build
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.model.BarcodeDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.model.BarcodeFormatDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.model.BarcodeSourceDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.use_case.barcode.GetBarcodeByIdUseCase
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.use_case.barcode.InsertBarcodesUseCase
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.use_case.barcode.UpdateBarcodesUseCase
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.base.ScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.create_barcode.create_barcode.snackbar.CreateBarcodeScreenSnackbarType
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.create_barcode.create_barcode.state.CreateBarcodeScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.create_barcode.create_barcode.state.CreateBarcodeScreenUIStateEvents
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.create_barcode.navigation.CreateBarcodeScreenArgs
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.NavigationKit
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.analytics.AnalyticsKit
import com.makeappssimple.abhimanyu.common.core.build_config.BuildConfigKit
import com.makeappssimple.abhimanyu.common.core.clipboard.ClipboardKit
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.result.MyResult
import com.makeappssimple.abhimanyu.common.core.util.defaultObjectStateIn
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
    private val buildConfigKit: BuildConfigKit,
    private val clipboardKit: ClipboardKit,
    private val getBarcodeByIdUseCase: GetBarcodeByIdUseCase,
    private val insertBarcodesUseCase: InsertBarcodesUseCase,
    private val updateBarcodesUseCase: UpdateBarcodesUseCase,
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
    private val originalBarcode: MutableStateFlow<BarcodeDomainModel?> =
        MutableStateFlow(
            value = null,
        )
    private val barcodeName = MutableStateFlow(
        value = "",
    )
    private val barcodeValue = MutableStateFlow(
        value = "",
    )
    private val isError: MutableStateFlow<Boolean> =
        MutableStateFlow(
            value = false,
        )
    private val screenSnackbarType: MutableStateFlow<CreateBarcodeScreenSnackbarType> =
        MutableStateFlow(
            value = CreateBarcodeScreenSnackbarType.None,
        )
    // endregion

    // region uiState and uiStateEvents
    val uiState: StateFlow<CreateBarcodeScreenUIState> = combine(
        flow = originalBarcode,
        flow2 = barcodeName,
        flow3 = barcodeValue,
        flow4 = screenSnackbarType,
        flow5 = isError,
    ) {
            originalBarcode,
            barcodeName,
            barcodeValue,
            screenSnackbarType,
            isError,
        ->
        CreateBarcodeScreenUIState(
            isBarcodeValueEditable = originalBarcode == null,
            isError = isError,
            isSaveButtonEnabled = barcodeName.isNotNullOrBlank() && barcodeValue.isNotNullOrBlank(),
            barcodeName = barcodeName,
            barcodeValue = barcodeValue,
            screenSnackbarType = screenSnackbarType,
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

    fun onSnackbarDismissed() {
        screenSnackbarType.update {
            CreateBarcodeScreenSnackbarType.None
        }
    }

    @OptIn(ExperimentalTime::class)
    fun saveBarcode(
        onSuccess: () -> Unit = {},
    ): Job {
        return viewModelScope.launch {
            val originalBarcodeValue = originalBarcode.value
            val result = if (originalBarcodeValue != null) {
                updateBarcodesUseCase(
                    originalBarcodeValue.copy(
                        name = barcodeName.value,
                    ),
                )
            } else {
                insertBarcodesUseCase(
                    source = BarcodeSourceDomainModel.CREATED,
                    format = BarcodeFormatDomainModel.QrCode.value,
                    name = barcodeName.value,
                    value = barcodeValue.value,
                )
            }
            when (result) {
                is MyResult.Error -> {
                    screenSnackbarType.update {
                        CreateBarcodeScreenSnackbarType.SaveBarcodeFailed
                    }
                }

                is MyResult.Loading -> {}
                is MyResult.Success -> {
                    onSuccess()
                }
            }
        }
    }

    fun shouldShowCopiedToClipboardToastMessage(): Boolean {
        return !buildConfigKit.isAndroidApiEqualToOrAbove(
            buildVersionNumber = Build.VERSION_CODES.TIRAMISU,
        )
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
            val result: MyResult<BarcodeDomainModel?> = getBarcodeByIdUseCase(
                id = barcodeId,
            )
            when (result) {
                is MyResult.Error -> {
                    isError.update {
                        true
                    }
                }

                is MyResult.Loading -> {}

                is MyResult.Success -> {
                    val fetchedOriginalBarcode = result.data
                    originalBarcode.update {
                        fetchedOriginalBarcode
                    }
                    barcodeName.update {
                        fetchedOriginalBarcode?.name.orEmpty()
                    }
                    barcodeValue.update {
                        fetchedOriginalBarcode?.value.orEmpty()
                    }
                }
            }
        }
    }
}
