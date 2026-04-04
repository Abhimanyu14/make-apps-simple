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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.transaction_for

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.listitem.transaction_for.TransactionForListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.listitem.transaction_for.TransactionForListItemDataAndEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.listitem.transaction_for.TransactionForListItemEvent
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Immutable
internal data class SelectTransactionForBottomSheetData(
    val transactionForValues: List<TransactionFor> = emptyList(),
)

@Immutable
internal sealed class SelectTransactionForBottomSheetEvent {
    internal data class OnItemClick(
        val selectedTransactionFor: TransactionFor,
    ) : SelectTransactionForBottomSheetEvent()
}

@Composable
internal fun SelectTransactionForBottomSheet(
    modifier: Modifier = Modifier,
    data: SelectTransactionForBottomSheetData,
    handleEvent: (event: SelectTransactionForBottomSheetEvent) -> Unit = {},
) {
    SelectTransactionForBottomSheetUI(
        modifier = modifier,
        data = SelectTransactionForListItemBottomSheetUIData(
            titleTextStringResourceId = R.string.finance_manager_bottom_sheet_select_transaction_for_title,
            data = data.transactionForValues
                .map { transactionFor ->
                    TransactionForListItemDataAndEventHandler(
                        data = TransactionForListItemData(
                            title = transactionFor.title,
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
