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

package com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.home.home.view_model

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.use_case.barcode.DeleteBarcodesUseCase
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.use_case.barcode.GetAllBarcodesFlowUseCase
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.use_case.barcode.InsertBarcodesUseCase
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.base.ScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.home.home.state.HomeScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.home.home.state.HomeScreenUIStateEvents
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.mapper.BarcodeDomainToUiMapper
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.mapper.BarcodeUiToDomainMapper
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.model.BarcodeUiModel
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.NavigationKit
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.analytics.AnalyticsKit
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.feature.home.home.bottom_sheet.HomeCosmosBottomSheetType
import com.makeappssimple.abhimanyu.common.core.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.result.MyResult
import com.makeappssimple.abhimanyu.common.core.util.defaultObjectStateIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class HomeScreenViewModel(
    analyticsKit: AnalyticsKit,
    coroutineScope: CoroutineScope,
    getAllBarcodesFlowUseCase: GetAllBarcodesFlowUseCase,
    logKit: LogKit,
    navigationKit: NavigationKit,
    private val barcodeDomainToUiMapper: BarcodeDomainToUiMapper,
    private val barcodeUiToDomainMapper: BarcodeUiToDomainMapper,
    private val dateTimeKit: DateTimeKit,
    private val deleteBarcodesUseCase: DeleteBarcodesUseCase,
    private val insertBarcodesUseCase: InsertBarcodesUseCase,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    analyticsKit = analyticsKit,
    logKit = logKit,
    navigationKit = navigationKit,
    screen = Screen.Home,
) {
    private var allBarcodes: Flow<List<BarcodeUiModel>> =
        getAllBarcodesFlowUseCase().map {
            it.map { barcodeDomainModel ->
                barcodeDomainToUiMapper.toUiModel(
                    barcodeDomainModel = barcodeDomainModel,
                )
            }
        }

    // region state
    private val isDeleteBarcodeDialogVisible = MutableStateFlow(
        value = false,
    )
    private val homeScreenBottomSheetType: MutableStateFlow<HomeCosmosBottomSheetType> =
        MutableStateFlow(
            value = HomeCosmosBottomSheetType.None,
        )
    // endregion

    // region uiState and uiStateEvents
    val uiState: StateFlow<HomeScreenUIState> = combine(
        flow = allBarcodes,
        flow2 = isDeleteBarcodeDialogVisible,
        flow3 = homeScreenBottomSheetType,
    ) {
            allBarcodes,
            isDeleteBarcodeDialogVisible,
            homeScreenBottomSheetType,
        ->
        HomeScreenUIState(
            isDeleteBarcodeDialogVisible = isDeleteBarcodeDialogVisible,
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
        updateScreenBottomSheetType = ::updateScreenBottomSheetType,
        updateIsDeleteBarcodeDialogVisible = ::updateIsDeleteBarcodeDialogVisible,
    )
    // endregion

    fun saveBarcode(
        barcode: BarcodeUiModel,
    ): Job {
        return viewModelScope.launch {
            val result = insertBarcodesUseCase(
                source = barcodeUiToDomainMapper.toDomainModel(
                    barcodeSourceUiModel = barcode.source,
                ),
                format = barcode.format.value,
                id = barcode.id,
                timestamp = barcode.timestamp,
                name = barcode.name,
                value = barcode.value,
            )
            if (result is MyResult.Error) {
                // TODO(Abhi): Handle failure
                result.exception?.printStackTrace()
            }
        }
    }

    fun deleteBarcodes(
        barcodes: List<BarcodeUiModel>,
    ): Job {
        return viewModelScope.launch {
            val result = deleteBarcodesUseCase(
                barcodes = barcodes.map {
                    barcodeUiToDomainMapper.toDomainModel(
                        barcodeUiModel = it,
                    )
                }.toTypedArray(),
            )
            if (result is MyResult.Error) {
                // TODO(Abhi): Handle failure
                result.exception?.printStackTrace()
            }
        }
    }

    // region state events
    private fun updateIsDeleteBarcodeDialogVisible(
        updatedIsDeleteBarcodeDialogVisible: Boolean,
    ) {
        isDeleteBarcodeDialogVisible.update {
            updatedIsDeleteBarcodeDialogVisible
        }
    }

    private fun updateScreenBottomSheetType(
        updatedHomeScreenBottomSheetType: HomeCosmosBottomSheetType,
    ) {
        homeScreenBottomSheetType.update {
            updatedHomeScreenBottomSheetType
        }
    }
    // endregion
}
