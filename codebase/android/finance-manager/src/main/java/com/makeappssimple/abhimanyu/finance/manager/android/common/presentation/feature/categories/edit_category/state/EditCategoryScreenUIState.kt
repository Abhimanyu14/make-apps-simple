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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.state

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.bottom_sheet.EditCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class EditCategoryScreenUIState(
    val screenBottomSheetType: EditCategoryScreenBottomSheetType = EditCategoryScreenBottomSheetType.None,
    val isBottomSheetVisible: Boolean = false,
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    val isSupportingTextVisible: Boolean = false,
    val titleError: EditCategoryScreenTitleError = EditCategoryScreenTitleError.None,
    val selectedTransactionTypeIndex: Int? = null,
    val transactionTypesChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val emoji: String = "",
    val titleTextFieldState: TextFieldState = TextFieldState(),
) : ScreenUIState

internal sealed class EditCategoryScreenTitleError {
    data object CategoryExists : EditCategoryScreenTitleError()
    data object None : EditCategoryScreenTitleError()
}

internal val EditCategoryScreenTitleError.stringResourceId: Int?
    get() {
        return when (this) {
            EditCategoryScreenTitleError.CategoryExists -> R.string.finance_manager_screen_add_or_edit_category_error_category_exists
            EditCategoryScreenTitleError.None -> null
        }
    }
