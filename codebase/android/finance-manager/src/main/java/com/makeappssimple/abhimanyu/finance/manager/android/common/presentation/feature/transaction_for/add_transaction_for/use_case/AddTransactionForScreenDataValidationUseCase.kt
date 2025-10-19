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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.add_transaction_for.use_case

import com.makeappssimple.abhimanyu.common.core.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.transaction_for.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.add_transaction_for.state.AddTransactionForScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.add_transaction_for.view_model.AddTransactionForScreenDataValidationState
import kotlinx.collections.immutable.ImmutableList

internal class AddTransactionForScreenDataValidationUseCase(
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
) {
    suspend operator fun invoke(
        enteredTitle: String,
    ): AddTransactionForScreenDataValidationState {
        val allTransactionForValues: ImmutableList<TransactionFor> =
            getAllTransactionForValuesUseCase()
        val state = AddTransactionForScreenDataValidationState()
        if (enteredTitle.isBlank()) {
            return state
        }
        val isTransactionForTitleAlreadyUsed = allTransactionForValues.find {
            it.title.equalsIgnoringCase(
                other = enteredTitle.trim(),
            )
        }.isNotNull()
        if (isTransactionForTitleAlreadyUsed) {
            return state
                .copy(
                    titleError = AddTransactionForScreenTitleError.TransactionForExists,
                )
        }
        return state
            .copy(
                isCtaButtonEnabled = true,
            )
    }
}
