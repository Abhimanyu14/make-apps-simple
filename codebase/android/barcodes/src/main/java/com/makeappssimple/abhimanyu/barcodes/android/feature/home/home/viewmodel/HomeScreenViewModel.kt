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

package com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.barcodes.android.core.analytics.AnalyticsKit
import com.makeappssimple.abhimanyu.barcodes.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.barcodes.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.barcodes.android.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.barcodes.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.barcodes.android.core.data.repository.BarcodeRepository
import com.makeappssimple.abhimanyu.barcodes.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.screen.HomeScreenUIData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class HomeScreenViewModel(
    analyticsKit: AnalyticsKit,
    coroutineScope: CoroutineScope,
    screenUICommonState: ScreenUICommonState,
    private val barcodeRepository: BarcodeRepository,
    private val dateTimeKit: DateTimeKit,
    private val navigationKit: NavigationKit,
    val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
    analyticsKit = analyticsKit,
    screen = Screen.Home,
    screenUICommonState = screenUICommonState,
) {
    private var allBarcodes: Flow<List<Barcode>> =
        barcodeRepository.getAllBarcodesFlow()
    val screenUIData: StateFlow<MyResult<HomeScreenUIData>?> = allBarcodes.map {
        MyResult.Success(
            data = HomeScreenUIData(
                allBarcodes = it,
                barcodeFormattedTimestamps = it.map { barcode ->
                    dateTimeKit.getFormattedDateAndTime(
                        timestamp = barcode.timestamp,
                    )
                },
            ),
        )
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    override fun updateUiStateAndStateEvents() {}

    fun navigateToCreateBarcodeScreen() {
        navigationKit.navigateToCreateBarcodeScreen()
    }

    fun navigateToScanBarcodeScreen() {
        navigationKit.navigateToScanBarcodeScreen()
    }

    fun navigateToSettingsScreen() {
        navigationKit.navigateToSettingsScreen()
    }

    fun navigateToBarcodeDetailsScreen(
        barcodeId: Int,
    ) {
        navigationKit.navigateToBarcodeDetailsScreen(
            barcodeId = barcodeId,
        )
    }

    fun saveBarcode(
        barcode: Barcode,
    ): Job {
        return viewModelScope.launch {
            barcodeRepository.insertBarcodes(
                barcode,
            )
        }
    }

    fun deleteBarcodes(
        barcodes: List<Barcode>,
    ): Job {
        return viewModelScope.launch {
            barcodeRepository.deleteBarcodes(
                barcodes = barcodes.toTypedArray(),
            )
        }
    }
}
