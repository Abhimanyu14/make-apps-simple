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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.bottomsheet.transactionfor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.transactionfor.TransactionForListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.transactionfor.TransactionForListItemDataAndEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.transactionfor.TransactionForListItemEvent
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Immutable
public data class SelectTransactionForBottomSheetData(
    val transactionForValues: List<TransactionFor> = emptyList(),
)

@Immutable
public sealed class SelectTransactionForBottomSheetEvent {
    public data class OnItemClick(
        val selectedTransactionFor: TransactionFor,
    ) : SelectTransactionForBottomSheetEvent()
}

@Composable
public fun SelectTransactionForBottomSheet(
    modifier: Modifier = Modifier,
    data: SelectTransactionForBottomSheetData,
    handleEvent: (event: SelectTransactionForBottomSheetEvent) -> Unit = {},
) {
    SelectTransactionForBottomSheetUI(
        modifier = modifier,
        data = SelectTransactionForListItemBottomSheetUIData(
            titleTextStringResourceId = R.string.bottom_sheet_select_transaction_for_title,
            data = data.transactionForValues
                .map { transactionFor ->
                    TransactionForListItemDataAndEventHandler(
                        data = TransactionForListItemData(
                            title = transactionFor.titleToDisplay,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is TransactionForListItemEvent.OnClick -> {
                                    handleEvent(
                                        SelectTransactionForBottomSheetEvent.OnItemClick(
                                            selectedTransactionFor = transactionFor,
                                        )
                                    )
                                }

                                is TransactionForListItemEvent.OnMoreOptionsIconButtonClick -> {}
                            }
                        },
                    )
                }
                .toList(),
        ),
    )
}
