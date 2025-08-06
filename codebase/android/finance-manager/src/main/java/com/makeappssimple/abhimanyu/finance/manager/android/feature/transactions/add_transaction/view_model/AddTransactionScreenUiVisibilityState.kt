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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.view_model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed class AddTransactionScreenUiVisibilityState(
    val isTitleTextFieldVisible: Boolean = false,
    val isCategoryTextFieldVisible: Boolean = false,
    val isTransactionForRadioGroupVisible: Boolean = false,
    val isTransactionTypesRadioGroupVisible: Boolean = false,
    val isAccountFromTextFieldVisible: Boolean = false,
    val isAccountToTextFieldVisible: Boolean = false,
    val isTitleSuggestionsVisible: Boolean = false,
) {
    data object Expense : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = true,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = true,
        isAccountToTextFieldVisible = false,
        isTitleSuggestionsVisible = true,
    )

    data object Income : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = false,
        isAccountToTextFieldVisible = true,
        isTitleSuggestionsVisible = true,
    )

    data object Investment : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = true,
        isCategoryTextFieldVisible = true,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = true,
        isAccountToTextFieldVisible = false,
        isTitleSuggestionsVisible = true,
    )

    data object Refund : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = false,
        isCategoryTextFieldVisible = false,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = false,
        isAccountFromTextFieldVisible = false,
        isAccountToTextFieldVisible = true,
        isTitleSuggestionsVisible = false,
    )

    data object Transfer : AddTransactionScreenUiVisibilityState(
        isTitleTextFieldVisible = false,
        isCategoryTextFieldVisible = false,
        isTransactionForRadioGroupVisible = false,
        isTransactionTypesRadioGroupVisible = true,
        isAccountFromTextFieldVisible = true,
        isAccountToTextFieldVisible = true,
        isTitleSuggestionsVisible = false,
    )
}
