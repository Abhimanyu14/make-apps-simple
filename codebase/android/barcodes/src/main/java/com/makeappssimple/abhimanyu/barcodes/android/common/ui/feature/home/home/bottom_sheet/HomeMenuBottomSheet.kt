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

package com.makeappssimple.abhimanyu.barcodes.android.common.ui.feature.home.home.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.icons.BarcodesIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosListItemData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosListItemDataEvent
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosListItemDataEventDataAndEventHandler
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosSimpleList
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.spacer.CosmosNavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.library.barcodes.android.R

private object HomeMenuBottomSheetConstants {
    val minimumBottomSheetHeight = 24.dp
}

@Immutable
internal sealed class HomeMenuBottomSheetEvent {
    data object OnCreateBarcodeButtonClick : HomeMenuBottomSheetEvent()
    data object OnScanBarcodeButtonClick : HomeMenuBottomSheetEvent()
}

@Composable
internal fun HomeMenuBottomSheet(
    handleEvent: (event: HomeMenuBottomSheetEvent) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .defaultMinSize(
                minHeight = HomeMenuBottomSheetConstants.minimumBottomSheetHeight,
            ),
    ) {
        CosmosSimpleList(
            listItemsDataAndEventHandler = listOf(
                CosmosListItemDataEventDataAndEventHandler(
                    data = CosmosListItemData(
                        stringResource = CosmosStringResource.Id(
                            id = R.string.barcodes_screen_home_bottom_sheet_scan_barcode,
                        ),
                        iconResource = BarcodesIcons.Scanner,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            CosmosListItemDataEvent.OnClick -> {
                                handleEvent(HomeMenuBottomSheetEvent.OnScanBarcodeButtonClick)
                            }

                            CosmosListItemDataEvent.OnLongClick -> {}

                            CosmosListItemDataEvent.OnToggleSelection -> {}
                        }
                    },
                ),
                CosmosListItemDataEventDataAndEventHandler(
                    data =
                        CosmosListItemData(
                            stringResource = CosmosStringResource.Id(
                                id = R.string.barcodes_screen_home_bottom_sheet_create_barcode,
                            ),
                            iconResource = BarcodesIcons.Barcode,
                        ),
                    handleEvent = { event ->
                        when (event) {
                            CosmosListItemDataEvent.OnClick -> {
                                handleEvent(HomeMenuBottomSheetEvent.OnCreateBarcodeButtonClick)
                            }

                            CosmosListItemDataEvent.OnLongClick -> {}

                            CosmosListItemDataEvent.OnToggleSelection -> {}
                        }
                    },
                ),
            ),
        )
        CosmosNavigationBarsAndImeSpacer()
    }
}
