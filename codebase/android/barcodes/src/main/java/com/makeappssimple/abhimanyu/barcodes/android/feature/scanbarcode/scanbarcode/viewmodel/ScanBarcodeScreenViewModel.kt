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

package com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.scanbarcode.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.barcodes.android.core.analytics.AnalyticsKit
import com.makeappssimple.abhimanyu.barcodes.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.barcodes.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.barcodes.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.barcodes.android.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.barcodes.android.core.data.repository.BarcodeRepository
import com.makeappssimple.abhimanyu.barcodes.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.model.BarcodeSource
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.navigation.ScanBarcodeScreenArgs
import com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.scanbarcode.screen.ScanBarcodeScreenUIData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class ScanBarcodeScreenViewModel(
    analyticsKit: AnalyticsKit,
    coroutineScope: CoroutineScope,
    savedStateHandle: SavedStateHandle,
    screenUICommonState: ScreenUICommonState,
    private val barcodeRepository: BarcodeRepository,
    private val navigationKit: NavigationKit,
    val dateTimeKit: DateTimeKit,
    val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
    analyticsKit = analyticsKit,
    screen = Screen.ScanBarcode,
    screenUICommonState = screenUICommonState,
) {
    // region screen args
    private val screenArgs = ScanBarcodeScreenArgs(
        savedStateHandle = savedStateHandle,
    )
    // endregion

    override fun updateUiStateAndStateEvents() {}

    val screenUIData: StateFlow<MyResult<ScanBarcodeScreenUIData>?> =
        MutableStateFlow(
            MyResult.Success(
                data = ScanBarcodeScreenUIData(
                    isDeeplink = screenArgs.isDeeplink.orFalse(),
                    isCameraPermissionGranted = false, // TODO(Abhi): To remove
                ),
            )
        )

    fun saveBarcode(
        barcodeFormat: Int,
        barcodeValue: String,
    ) {
        viewModelScope.launch {
            val barcodeId = barcodeRepository.insertBarcodes(
                Barcode(
                    source = BarcodeSource.SCANNED,
                    format = barcodeFormat,
                    timestamp = dateTimeKit.getCurrentTimeMillis(),
                    value = barcodeValue,
                ),
            ).first().toInt()
            navigationKit.navigateUpAndNavigateToBarcodeDetailsScreen(
                barcodeId = barcodeId,
            )
        }
    }

    fun navigateUp() {
        navigationKit.navigateUp()
    }
}
