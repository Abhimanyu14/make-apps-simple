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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.usecase

import com.makeappssimple.abhimanyu.common.core.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.state.EditCategoryScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.viewmodel.EditCategoryScreenDataValidationState
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class EditCategoryScreenDataValidationUseCase() {
    public operator fun invoke(
        categories: ImmutableList<Category>,
        enteredTitle: String,
        currentCategory: Category?,
    ): EditCategoryScreenDataValidationState {
        val state = EditCategoryScreenDataValidationState()
        if (enteredTitle.isBlank()) {
            return state
        }
        if (isDefaultIncomeCategory(
                category = enteredTitle,
            ) || isDefaultExpenseCategory(
                category = enteredTitle,
            ) || isDefaultInvestmentCategory(
                category = enteredTitle,
            )
        ) {
            return state
                .copy(
                    titleError = EditCategoryScreenTitleError.CategoryExists,
                )
        }
        val isCategoryTitleAlreadyUsed = categories.find {
            it.title.equalsIgnoringCase(
                other = enteredTitle.trim(),
            )
        }.isNotNull()
        if (isCategoryTitleAlreadyUsed && enteredTitle != currentCategory?.title?.trim()) {
            return state
                .copy(
                    titleError = EditCategoryScreenTitleError.CategoryExists,
                )
        }
        return state
            .copy(
                isCtaButtonEnabled = true,
            )
    }
}
