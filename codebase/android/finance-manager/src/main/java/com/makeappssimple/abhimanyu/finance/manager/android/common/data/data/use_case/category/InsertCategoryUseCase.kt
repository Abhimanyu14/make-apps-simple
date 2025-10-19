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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.category

import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType

public class InsertCategoryUseCase(
    private val insertCategoriesUseCase: InsertCategoriesUseCase,
) {
    public suspend operator fun invoke(
        emoji: String,
        title: String,
        transactionType: TransactionType,
    ): Long {
        val category = Category(
            emoji = emoji,
            title = title,
            transactionType = transactionType,
        )
        return insertCategoriesUseCase(category)
            .first()
    }
}
