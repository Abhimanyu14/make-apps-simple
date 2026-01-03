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
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.common.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.common.MyConfirmationBottomSheetData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.common.MyConfirmationBottomSheetEvent
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
internal fun TransactionForValuesDeleteConfirmationBottomSheet(
    handleEvent: (event: TransactionForValuesDeleteConfirmationBottomSheetEvent) -> Unit = {},
) {
    MyConfirmationBottomSheet(
        data = MyConfirmationBottomSheetData(
            message = stringResource(
                id = R.string.finance_manager_bottom_sheet_transaction_for_values_menu_message,
            ),
            negativeButtonText = stringResource(
                id = R.string.finance_manager_bottom_sheet_transaction_for_values_menu_negative_button_text,
            ),
            positiveButtonText = stringResource(
                id = R.string.finance_manager_bottom_sheet_transaction_for_values_menu_positive_button_text,
            ),
            title = stringResource(
                id = R.string.finance_manager_bottom_sheet_transaction_for_values_menu_delete_title,
            ),
        ),
        handleEvent = { event ->
            when (event) {
                is MyConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                    handleEvent(
                        TransactionForValuesDeleteConfirmationBottomSheetEvent.OnNegativeButtonClick
                    )
                }

                is MyConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                    handleEvent(
                        TransactionForValuesDeleteConfirmationBottomSheetEvent.OnPositiveButtonClick
                    )
                }
            }
        },
    )
}
