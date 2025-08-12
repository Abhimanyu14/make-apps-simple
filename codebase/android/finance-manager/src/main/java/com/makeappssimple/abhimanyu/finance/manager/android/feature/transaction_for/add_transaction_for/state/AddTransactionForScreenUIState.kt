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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.add_transaction_for.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Stable
internal data class AddTransactionForScreenUIState(
    val titleError: AddTransactionForScreenTitleError = AddTransactionForScreenTitleError.None,
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    val title: TextFieldValue = TextFieldValue(),
) : ScreenUIState

public sealed class AddTransactionForScreenTitleError {
    public data object TransactionForExists :
        AddTransactionForScreenTitleError()

    public data object None : AddTransactionForScreenTitleError()
}

internal val AddTransactionForScreenTitleError.stringResourceId: Int?
    get() {
        return when (this) {
            AddTransactionForScreenTitleError.TransactionForExists -> R.string.finance_manager_screen_add_or_edit_transaction_for_error_exists
            AddTransactionForScreenTitleError.None -> null
        }
    }
