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

package com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.category

import com.makeappssimple.abhimanyu.common.core.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.add_category.state.AddCategoryScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.add_category.view_model.AddCategoryScreenDataValidationState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.util.isDefaultInvestmentCategory
import kotlinx.collections.immutable.ImmutableList

internal class AddCategoryScreenDataValidationUseCase(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
) {
    suspend operator fun invoke(
        enteredTitle: String,
    ): AddCategoryScreenDataValidationState {
        val categories: ImmutableList<Category> = getAllCategoriesUseCase()
        val state = AddCategoryScreenDataValidationState()
        if (enteredTitle.trim().isBlank()) {
            return state
        }
        if (isDefaultIncomeCategory(
                category = enteredTitle.trim(),
            ) || isDefaultExpenseCategory(
                category = enteredTitle.trim(),
            ) || isDefaultInvestmentCategory(
                category = enteredTitle.trim(),
            )
        ) {
            return state
                .copy(
                    titleError = AddCategoryScreenTitleError.CategoryExists,
                )
        }
        val isCategoryTitleAlreadyUsed = categories.find {
            it.title.equalsIgnoringCase(
                other = enteredTitle.trim(),
            )
        }.isNotNull()
        if (isCategoryTitleAlreadyUsed) {
            return state
                .copy(
                    titleError = AddCategoryScreenTitleError.CategoryExists,
                )
        }
        return state
            .copy(
                isCtaButtonEnabled = true,
            )
    }
}
