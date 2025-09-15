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

package com.makeappssimple.abhimanyu.finance.manager.android.common.feature.transactions.add_transaction.view_model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed class AddTransactionScreenUiVisibilityState(
    open val isTitleTextFieldVisible: Boolean = false,
    open val isCategoryTextFieldVisible: Boolean = false,
    open val isTransactionForRadioGroupVisible: Boolean = false,
    open val isTransactionTypesRadioGroupVisible: Boolean = false,
    open val isAccountFromTextFieldVisible: Boolean = false,
    open val isAccountToTextFieldVisible: Boolean = false,
    open val isTitleSuggestionsVisible: Boolean = false,
) {
    data class Expense(
        override val isTitleTextFieldVisible: Boolean = true,
        override val isCategoryTextFieldVisible: Boolean = true,
        override val isTransactionForRadioGroupVisible: Boolean = true,
        override val isTransactionTypesRadioGroupVisible: Boolean = true,
        override val isAccountFromTextFieldVisible: Boolean = true,
        override val isAccountToTextFieldVisible: Boolean = false,
        override val isTitleSuggestionsVisible: Boolean = true,
    ) : AddTransactionScreenUiVisibilityState()

    data class Income(
        override val isTitleTextFieldVisible: Boolean = true,
        override val isCategoryTextFieldVisible: Boolean = true,
        override val isTransactionForRadioGroupVisible: Boolean = false,
        override val isTransactionTypesRadioGroupVisible: Boolean = true,
        override val isAccountFromTextFieldVisible: Boolean = false,
        override val isAccountToTextFieldVisible: Boolean = true,
        override val isTitleSuggestionsVisible: Boolean = true,
    ) : AddTransactionScreenUiVisibilityState()

    data class Investment(
        override val isTitleTextFieldVisible: Boolean = true,
        override val isCategoryTextFieldVisible: Boolean = true,
        override val isTransactionForRadioGroupVisible: Boolean = false,
        override val isTransactionTypesRadioGroupVisible: Boolean = true,
        override val isAccountFromTextFieldVisible: Boolean = true,
        override val isAccountToTextFieldVisible: Boolean = false,
        override val isTitleSuggestionsVisible: Boolean = true,
    ) : AddTransactionScreenUiVisibilityState()

    data class Refund(
        override val isTitleTextFieldVisible: Boolean = false,
        override val isCategoryTextFieldVisible: Boolean = false,
        override val isTransactionForRadioGroupVisible: Boolean = false,
        override val isTransactionTypesRadioGroupVisible: Boolean = false,
        override val isAccountFromTextFieldVisible: Boolean = false,
        override val isAccountToTextFieldVisible: Boolean = true,
        override val isTitleSuggestionsVisible: Boolean = false,
    ) : AddTransactionScreenUiVisibilityState()

    data class Transfer(
        override val isTitleTextFieldVisible: Boolean = false,
        override val isCategoryTextFieldVisible: Boolean = false,
        override val isTransactionForRadioGroupVisible: Boolean = false,
        override val isTransactionTypesRadioGroupVisible: Boolean = true,
        override val isAccountFromTextFieldVisible: Boolean = true,
        override val isAccountToTextFieldVisible: Boolean = true,
        override val isTitleSuggestionsVisible: Boolean = false,
    ) : AddTransactionScreenUiVisibilityState()
}
