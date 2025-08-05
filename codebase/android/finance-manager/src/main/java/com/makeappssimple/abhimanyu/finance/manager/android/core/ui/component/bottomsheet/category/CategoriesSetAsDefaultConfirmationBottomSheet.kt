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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.bottomsheet.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheetData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheetEvent
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Immutable
public sealed class CategoriesSetAsDefaultConfirmationBottomSheetEvent {
    public data object OnNegativeButtonClick :
        CategoriesSetAsDefaultConfirmationBottomSheetEvent()

    public data object OnPositiveButtonClick :
        CategoriesSetAsDefaultConfirmationBottomSheetEvent()
}

@Immutable
public data class CategoriesSetAsDefaultConfirmationBottomSheetData(
    val transactionType: TransactionType,
)

@Composable
public fun CategoriesSetAsDefaultConfirmationBottomSheet(
    data: CategoriesSetAsDefaultConfirmationBottomSheetData,
    handleEvent: (event: CategoriesSetAsDefaultConfirmationBottomSheetEvent) -> Unit = {},
) {
    MyConfirmationBottomSheet(
        data = MyConfirmationBottomSheetData(
            message = stringResource(
                id = R.string.finance_manager_screen_categories_bottom_sheet_set_as_default_message,
                data.transactionType.title.lowercase(),
            ),
            negativeButtonText = stringResource(
                id = R.string.finance_manager_screen_categories_bottom_sheet_set_as_default_negative_button_text,
            ),
            positiveButtonText = stringResource(
                id = R.string.finance_manager_screen_categories_bottom_sheet_set_as_default_positive_button_text,
            ),
            title = stringResource(
                id = R.string.finance_manager_screen_categories_bottom_sheet_set_as_default_title,
            ),
        ),
        handleEvent = { event ->
            when (event) {
                is MyConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                    handleEvent(
                        CategoriesSetAsDefaultConfirmationBottomSheetEvent.OnNegativeButtonClick
                    )
                }

                is MyConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                    handleEvent(
                        CategoriesSetAsDefaultConfirmationBottomSheetEvent.OnPositiveButtonClick
                    )
                }
            }
        },
    )
}
