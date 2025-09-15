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

package com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.edit_category.use_case

import com.makeappssimple.abhimanyu.common.core.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.edit_category.state.EditCategoryScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.edit_category.view_model.EditCategoryScreenDataValidationState
import kotlinx.collections.immutable.ImmutableList

public class EditCategoryScreenDataValidationUseCase(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
) {
    public suspend operator fun invoke(
        enteredTitle: String,
        currentCategory: Category?,
    ): EditCategoryScreenDataValidationState {
        val editCategoryScreenDataValidationState =
            EditCategoryScreenDataValidationState()
        if (enteredTitle.isBlank()) {
            return editCategoryScreenDataValidationState
        }
        val isDefaultCategory =
            isDefaultIncomeCategory(
                category = enteredTitle,
            ) || isDefaultExpenseCategory(
                category = enteredTitle,
            ) || isDefaultInvestmentCategory(
                category = enteredTitle,
            )
        if (isDefaultCategory) {
            return editCategoryScreenDataValidationState
                .copy(
                    titleError = EditCategoryScreenTitleError.CategoryExists,
                )
        }
        val categories: ImmutableList<Category> = getAllCategoriesUseCase()
        val isCategoryTitleChanged =
            currentCategory?.title?.trim() != enteredTitle.trim()
        val isCategoryTitleAlreadyUsed = categories.find {
            it.title.equalsIgnoringCase(
                other = enteredTitle.trim(),
            )
        }.isNotNull()
        if (isCategoryTitleChanged && isCategoryTitleAlreadyUsed) {
            return editCategoryScreenDataValidationState
                .copy(
                    titleError = EditCategoryScreenTitleError.CategoryExists,
                )
        }
        return editCategoryScreenDataValidationState
            .copy(
                isCtaButtonEnabled = true,
            )
    }
}
