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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.account

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.common.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.common.MyConfirmationBottomSheetData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.common.MyConfirmationBottomSheetEvent
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
internal fun AccountsDeleteConfirmationBottomSheet(
    handleEvent: (event: AccountsDeleteConfirmationBottomSheetEvent) -> Unit = {},
) {
    MyConfirmationBottomSheet(
        data = MyConfirmationBottomSheetData(
            messageStringResource = CosmosStringResource.Id(
                id = R.string.finance_manager_screen_accounts_bottom_sheet_delete_message,
            ),
            negativeButtonTextStringResource = CosmosStringResource.Id(
                id = R.string.finance_manager_screen_accounts_bottom_sheet_delete_negative_button_text,
            ),
            positiveButtonTextStringResource = CosmosStringResource.Id(
                id = R.string.finance_manager_screen_accounts_bottom_sheet_delete_positive_button_text,
            ),
            titleStringResource = CosmosStringResource.Id(
                id = R.string.finance_manager_screen_accounts_bottom_sheet_delete_title,
            ),
        ),
        handleEvent = { event ->
            when (event) {
                is MyConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                    handleEvent(AccountsDeleteConfirmationBottomSheetEvent.OnNegativeButtonClick)
                }

                is MyConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                    handleEvent(AccountsDeleteConfirmationBottomSheetEvent.OnPositiveButtonClick)
                }
            }
        },
    )
}
