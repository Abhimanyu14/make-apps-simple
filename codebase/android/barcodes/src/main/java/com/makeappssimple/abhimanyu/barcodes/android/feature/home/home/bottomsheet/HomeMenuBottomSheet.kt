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

package com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.list.MyListItemData
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.list.MyListItemDataEvent
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.list.MyListItemDataEventDataAndEventHandler
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.list.MySimpleList
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.spacer.NavigationBarsAndImeSpacer
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
        MySimpleList(
            listItemsDataAndEventHandler = listOf(
                MyListItemDataEventDataAndEventHandler(
                    data = MyListItemData(
                        stringResourceId = R.string.barcodes_screen_home_bottom_sheet_scan_barcode,
                        painter = painterResource(
                            id = R.drawable.barcodes_icon_scanner,
                        ),
                    ),
                    handleEvent = { event ->
                        when (event) {
                            MyListItemDataEvent.OnClick -> {
                                handleEvent(HomeMenuBottomSheetEvent.OnScanBarcodeButtonClick)
                            }

                            MyListItemDataEvent.OnLongClick -> {}

                            MyListItemDataEvent.OnToggleSelection -> {}
                        }
                    },
                ),
                MyListItemDataEventDataAndEventHandler(
                    data =
                        MyListItemData(
                            stringResourceId = R.string.barcodes_screen_home_bottom_sheet_create_barcode,
                            painter = painterResource(
                                id = R.drawable.barcodes_icon_barcode_black_24dp,
                            ),
                        ),
                    handleEvent = { event ->
                        when (event) {
                            MyListItemDataEvent.OnClick -> {
                                handleEvent(HomeMenuBottomSheetEvent.OnCreateBarcodeButtonClick)
                            }

                            MyListItemDataEvent.OnLongClick -> {}

                            MyListItemDataEvent.OnToggleSelection -> {}
                        }
                    },
                ),
            ),
        )
        NavigationBarsAndImeSpacer()
    }
}
