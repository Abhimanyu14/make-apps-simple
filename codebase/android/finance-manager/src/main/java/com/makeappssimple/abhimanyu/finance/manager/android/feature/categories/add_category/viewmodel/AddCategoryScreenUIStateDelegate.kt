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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList

internal interface AddCategoryScreenUIStateDelegate : ScreenUIStateDelegate {
    // region initial data
    val validTransactionTypes: ImmutableList<TransactionType>
    // endregion

    // region UI state
    val title: TextFieldValue
    val searchText: String
    val emoji: String
    val selectedTransactionTypeIndex: Int
    val screenBottomSheetType: AddCategoryScreenBottomSheetType
    // endregion

    // region state events
    fun clearSearchText(
        refresh: Boolean = true,
    )

    fun clearTitle(
        refresh: Boolean = true,
    )

    fun insertCategory()

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun updateEmoji(
        updatedEmoji: String,
        refresh: Boolean = true,
    )

    fun updateScreenBottomSheetType(
        updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType,
        refresh: Boolean = true,
    )

    fun updateSearchText(
        updatedSearchText: String,
        refresh: Boolean = true,
    )

    fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
        refresh: Boolean = true,
    )

    fun updateTitle(
        updatedTitle: TextFieldValue,
        refresh: Boolean = true,
    )
    // endregion
}
