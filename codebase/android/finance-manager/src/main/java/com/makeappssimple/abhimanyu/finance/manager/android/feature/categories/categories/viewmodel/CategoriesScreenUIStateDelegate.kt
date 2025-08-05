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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.viewmodel

import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.bottomsheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.snackbar.CategoriesScreenSnackbarType
import kotlinx.collections.immutable.PersistentList

internal interface CategoriesScreenUIStateDelegate : ScreenUIStateDelegate {
    // region initial data
    val validTransactionTypes: PersistentList<TransactionType>
    // endregion

    // region UI state
    val screenBottomSheetType: CategoriesScreenBottomSheetType
    val screenSnackbarType: CategoriesScreenSnackbarType
    val categoryIdToDelete: Int?
    val clickedItemId: Int?
    // endregion

    // region state events
    fun deleteCategory()

    fun navigateToAddCategoryScreen(
        transactionType: String,
    )

    fun navigateToEditCategoryScreen(
        categoryId: Int,
    )

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun resetScreenSnackbarType()

    fun updateDefaultCategoryIdInDataStore(
        selectedTabIndex: Int,
    )

    fun updateCategoryIdToDelete(
        updatedCategoryIdToDelete: Int?,
        refresh: Boolean = true,
    )

    fun updateClickedItemId(
        updatedClickedItemId: Int?,
        refresh: Boolean = true,
    )

    fun updateScreenBottomSheetType(
        updatedCategoriesScreenBottomSheetType: CategoriesScreenBottomSheetType,
        refresh: Boolean = true,
    )

    fun updateScreenSnackbarType(
        updatedCategoriesScreenSnackbarType: CategoriesScreenSnackbarType,
        refresh: Boolean = true,
    )
    // endregion
}
