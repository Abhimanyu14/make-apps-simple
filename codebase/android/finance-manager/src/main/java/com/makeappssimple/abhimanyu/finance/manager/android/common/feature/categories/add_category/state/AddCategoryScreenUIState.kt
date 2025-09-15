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

package com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.add_category.state

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.add_category.bottom_sheet.AddCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class AddCategoryScreenUIState(
    val screenBottomSheetType: AddCategoryScreenBottomSheetType = AddCategoryScreenBottomSheetType.None,
    val isBottomSheetVisible: Boolean = false,
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    val isSupportingTextVisible: Boolean = false,
    val titleError: AddCategoryScreenTitleError = AddCategoryScreenTitleError.None,
    val selectedTransactionTypeIndex: Int? = null,
    val transactionTypesChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val emoji: String = "",
    val emojiSearchText: String = "",
    val titleTextFieldState: TextFieldState = TextFieldState(),
) : ScreenUIState

public sealed class AddCategoryScreenTitleError {
    public data object CategoryExists : AddCategoryScreenTitleError()
    public data object None : AddCategoryScreenTitleError()
}

internal val AddCategoryScreenTitleError.stringResourceId: Int?
    get() {
        return when (this) {
            AddCategoryScreenTitleError.CategoryExists -> R.string.finance_manager_screen_add_or_edit_category_error_category_exists
            AddCategoryScreenTitleError.None -> null
        }
    }
