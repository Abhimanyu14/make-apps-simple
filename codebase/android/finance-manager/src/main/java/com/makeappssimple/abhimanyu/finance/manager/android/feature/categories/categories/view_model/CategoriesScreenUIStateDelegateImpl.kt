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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.view_model

import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.SetDefaultCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegateImpl
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.bottom_sheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.snackbar.CategoriesScreenSnackbarType
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class CategoriesScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val setDefaultCategoryUseCase: SetDefaultCategoryUseCase,
    private val navigationKit: NavigationKit,
) : CategoriesScreenUIStateDelegate, ScreenUIStateDelegateImpl() {
    // region initial data
    override val validTransactionTypes: PersistentList<TransactionType> =
        persistentListOf(
            TransactionType.EXPENSE,
            TransactionType.INCOME,
            TransactionType.INVESTMENT,
        )
    // endregion

    // region UI state
    override var screenBottomSheetType: CategoriesScreenBottomSheetType =
        CategoriesScreenBottomSheetType.None
    override var screenSnackbarType: CategoriesScreenSnackbarType =
        CategoriesScreenSnackbarType.None
    override var categoryIdToDelete: Int? = null
    override var clickedItemId: Int? = null
    // endregion

    // region state events
    override fun deleteCategory() {
        coroutineScope.launch {
            categoryIdToDelete?.let { id ->
                val isCategoryDeleted = deleteCategoryUseCase(
                    id = id,
                )
                if (isCategoryDeleted) {
                    categoryIdToDelete = null
                } else {
                    // TODO(Abhi): Handle this error scenario
                }
            } ?: run {
                // TODO(Abhi): Handle this error scenario
            }
        }
    }

    override fun navigateToAddCategoryScreen(
        transactionType: String,
    ) {
        navigationKit.navigateToAddCategoryScreen(
            transactionType = transactionType,
        )
    }

    override fun navigateToEditCategoryScreen(
        categoryId: Int,
    ) {
        navigationKit.navigateToEditCategoryScreen(
            categoryId = categoryId,
        )
    }

    override fun navigateUp() {
        navigationKit.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedCategoriesScreenBottomSheetType = CategoriesScreenBottomSheetType.None,
        )
    }

    override fun resetScreenSnackbarType() {
        updateScreenSnackbarType(
            updatedCategoriesScreenSnackbarType = CategoriesScreenSnackbarType.None,
        )
    }

    override fun updateDefaultCategoryIdInDataStore(
        selectedTabIndex: Int,
    ) {
        coroutineScope.launch {
            clickedItemId?.let {
                val isSetDefaultCategorySuccessful = setDefaultCategoryUseCase(
                    defaultCategoryId = it,
                    transactionType = validTransactionTypes[selectedTabIndex],
                )
                if (isSetDefaultCategorySuccessful) {
                    updateScreenSnackbarType(
                        updatedCategoriesScreenSnackbarType = CategoriesScreenSnackbarType.SetDefaultCategorySuccessful,
                    )
                } else {
                    updateScreenSnackbarType(
                        updatedCategoriesScreenSnackbarType = CategoriesScreenSnackbarType.SetDefaultCategoryFailed,
                    )
                    clickedItemId = null
                }
            } ?: run {
                // TODO(Abhi): Handle this error scenario
            }
        }
    }

    override fun updateCategoryIdToDelete(
        updatedCategoryIdToDelete: Int?,
        refresh: Boolean,
    ) {
        categoryIdToDelete = updatedCategoryIdToDelete
        if (refresh) {
            refresh()
        }
    }

    override fun updateClickedItemId(
        updatedClickedItemId: Int?,
        refresh: Boolean,
    ) {
        clickedItemId = updatedClickedItemId
        if (refresh) {
            refresh()
        }
    }

    override fun updateScreenBottomSheetType(
        updatedCategoriesScreenBottomSheetType: CategoriesScreenBottomSheetType,
        refresh: Boolean,
    ) {
        screenBottomSheetType = updatedCategoriesScreenBottomSheetType
        if (refresh) {
            refresh()
        }
    }

    override fun updateScreenSnackbarType(
        updatedCategoriesScreenSnackbarType: CategoriesScreenSnackbarType,
        refresh: Boolean,
    ) {
        screenSnackbarType = updatedCategoriesScreenSnackbarType
        if (refresh) {
            refresh()
        }
    }
    // endregion
}
