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

package com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.barcodes.android.core.analytics.FirebaseAnalyticsEventLogger
import com.makeappssimple.abhimanyu.barcodes.android.core.barcodegenerator.BarcodeGenerator
import com.makeappssimple.abhimanyu.barcodes.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.barcodes.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.barcodes.android.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.barcodes.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.barcodes.android.core.data.repository.BarcodeRepository
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.screen.BarcodeDetailsScreenUIData
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.navigation.BarcodeDetailsScreenArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BarcodeDetailsScreenViewModel(
    coroutineScope: CoroutineScope,
    firebaseAnalyticsEventLogger: FirebaseAnalyticsEventLogger,
    savedStateHandle: SavedStateHandle,
    screenUICommonState: ScreenUICommonState,
    private val barcodeGenerator: BarcodeGenerator,
    private val barcodeRepository: BarcodeRepository,
    private val dateTimeKit: DateTimeKit,
    private val navigationKit: NavigationKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
    firebaseAnalyticsEventLogger = firebaseAnalyticsEventLogger,
    screen = Screen.BarcodeDetails,
    screenUICommonState = screenUICommonState,
) {
    // region screen args
    private val screenArgs = BarcodeDetailsScreenArgs(
        savedStateHandle = savedStateHandle,
    )
    // endregion

    private val barcodeBitmapSize = MutableStateFlow(
        value = 0,
    )
    private val barcode = MutableStateFlow<Barcode?>(
        value = null,
    )
    private val barcodeBitmap: StateFlow<Bitmap?> = combine(
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
    )

    val screenUIData: StateFlow<MyResult<BarcodeDetailsScreenUIData>?> =
        combine(
            barcode,
            barcodeBitmap,
        ) { barcode,
            barcodeBitmap ->
            if (barcode == null) {
                MyResult.Loading
            } else {
                MyResult.Success(
                    data = BarcodeDetailsScreenUIData(
                        barcode = barcode,
                        formattedTimestamp = dateTimeKit.getFormattedDateAndTime(
                            timestamp = barcode.timestamp,
                        ),
                        bitmap = barcodeBitmap,
                    ),
                )
            }
        }.defaultObjectStateIn(
            scope = viewModelScope,
        )

    override fun updateUiStateAndStateEvents() {}

    override fun fetchData(): Job {
        return viewModelScope.launch {
            fetchBarcode()
        }
    }

    fun updateBarcodeBitmapSize(
        size: Int,
    ) {
        barcodeBitmapSize.value = size
    }

    fun navigateUp() {
        navigationKit.navigateUp()
    }

    fun navigateToCreateBarcodeScreen() {
        navigationKit.navigateToCreateBarcodeScreen(
            barcodeId = barcode.value?.id,
        )
    }

    fun deleteBarcode() {
        viewModelScope.launch {
            barcode.value?.let {
                barcodeRepository.deleteBarcodes(
                    barcodes = arrayOf(it),
                )
            }
        }
        navigationKit.navigateToHomeScreen()
    }

    private suspend fun fetchBarcode() {
        screenArgs.originalBarcodeId?.let {
            barcode.value = barcodeRepository.getBarcode(
                id = it,
            )
        }
    }
}
