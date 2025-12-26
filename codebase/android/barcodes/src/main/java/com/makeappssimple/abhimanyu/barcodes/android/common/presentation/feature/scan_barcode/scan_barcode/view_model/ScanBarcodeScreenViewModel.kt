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

package com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.scan_barcode.scan_barcode.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.model.BarcodeSourceDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.use_case.barcode.InsertBarcodesUseCase
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.base.ScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.scan_barcode.navigation.ScanBarcodeScreenArgs
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.scan_barcode.scan_barcode.snackbar.ScanBarcodeScreenSnackbarType
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.scan_barcode.scan_barcode.state.ScanBarcodeScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.NavigationKit
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.analytics.AnalyticsKit
import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.common.core.extensions.orFalse
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.result.MyResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class ScanBarcodeScreenViewModel(
    analyticsKit: AnalyticsKit,
    coroutineScope: CoroutineScope,
    navigationKit: NavigationKit,
    savedStateHandle: SavedStateHandle,
    private val insertBarcodesUseCase: InsertBarcodesUseCase,
    val dateTimeKit: DateTimeKit,
    val dispatcherProvider: DispatcherProvider,
    val logKit: LogKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    analyticsKit = analyticsKit,
    logKit = logKit,
    navigationKit = navigationKit,
    screen = Screen.ScanBarcode,
) {
    // region screen args
    private val screenArgs = ScanBarcodeScreenArgs(
        savedStateHandle = savedStateHandle,
    )
    // endregion

    // region uiState and uiStateEvents
    private val _uiState: MutableStateFlow<ScanBarcodeScreenUIState> =
        MutableStateFlow(
            value = ScanBarcodeScreenUIState(
                isDeeplink = screenArgs.isDeeplink.orFalse(),
            ),
        )
    val uiState: StateFlow<ScanBarcodeScreenUIState> = _uiState.asStateFlow()
    // endregion

    fun onCameraPermissionChanged(
        isCameraPermissionGranted: Boolean,
    ) {
        _uiState.update {
            it.copy(
                isCameraPermissionGranted = isCameraPermissionGranted,
            )
        }
    }

    fun onSnackbarDismissed() {
        _uiState.update {
            it.copy(
                screenSnackbarType = ScanBarcodeScreenSnackbarType.None,
            )
        }
    }

    fun setPermissionPermanentlyDeniedDialogVisible(
        isVisible: Boolean,
    ) {
        _uiState.update {
            it.copy(
                showPermissionPermanentlyDeniedDialog = isVisible,
            )
        }
    }

    fun saveBarcode(
        barcodeFormat: Int,
        barcodeValue: String,
    ) {
        viewModelScope.launch {
            val result = insertBarcodesUseCase(
                source = BarcodeSourceDomainModel.SCANNED,
                format = barcodeFormat,
                value = barcodeValue,
            )
            when (result) {
                is MyResult.Error -> {
                    _uiState.update {
                        it.copy(
                            screenSnackbarType = ScanBarcodeScreenSnackbarType.SaveBarcodeFailed,
                        )
                    }
                }

                is MyResult.Loading -> {}

                is MyResult.Success -> {
                    val barcodeId = result.data.toInt()
                    navigateUp().join()
                    navigateToBarcodeDetailsScreen(
                        barcodeId = barcodeId,
                    )
                }
            }
        }
    }
}
