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
import com.makeappssimple.abhimanyu.barcodes.android.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.barcodes.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.barcodes.android.core.data.repository.BarcodeRepository
import com.makeappssimple.abhimanyu.barcodes.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.bottomsheet.HomeScreenBottomSheetType
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.state.HomeScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.state.HomeScreenUIStateEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class HomeScreenViewModel(
    analyticsKit: AnalyticsKit,
    coroutineScope: CoroutineScope,
    logKit: LogKit,
    navigationKit: NavigationKit,
    screenUICommonState: ScreenUICommonState,
    private val barcodeRepository: BarcodeRepository,
    private val dateTimeKit: DateTimeKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
    analyticsKit = analyticsKit,
    logKit = logKit,
    navigationKit = navigationKit,
    screen = Screen.Home,
    screenUICommonState = screenUICommonState,
) {
    private var allBarcodes: Flow<List<Barcode>> =
        barcodeRepository.getAllBarcodesFlow()

    private val homeScreenBottomSheetType: MutableStateFlow<HomeScreenBottomSheetType> =
        MutableStateFlow(
            value = HomeScreenBottomSheetType.None,
        )

    // region uiState and uiStateEvents
    val uiState: StateFlow<HomeScreenUIState> = combine(
        allBarcodes,
        homeScreenBottomSheetType,
    ) {
            allBarcodes,
            homeScreenBottomSheetType,
        ->
        HomeScreenUIState(
            allBarcodes = allBarcodes,
            barcodeFormattedTimestamps = allBarcodes.map { barcode ->
                dateTimeKit.getFormattedDateAndTime(
                    timestamp = barcode.timestamp,
                )
            },
            screenBottomSheetType = homeScreenBottomSheetType,
        )
    }.defaultObjectStateIn(
        scope = viewModelScope,
        initialValue = HomeScreenUIState(),
    )
    val uiStateEvents: HomeScreenUIStateEvents = HomeScreenUIStateEvents(
        setScreenBottomSheetType = ::setScreenBottomSheetType,
    )
    // endregion

    override fun updateUiStateAndStateEvents() {}

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

    fun setScreenBottomSheetType(
        updatedHomeScreenBottomSheetType: HomeScreenBottomSheetType,
    ) {
        homeScreenBottomSheetType.update {
            updatedHomeScreenBottomSheetType
        }
    }
}
