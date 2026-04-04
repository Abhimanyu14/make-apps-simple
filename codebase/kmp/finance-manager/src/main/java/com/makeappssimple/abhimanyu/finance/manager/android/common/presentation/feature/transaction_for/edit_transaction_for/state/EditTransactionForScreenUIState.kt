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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.edit_transaction_for.state

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Stable
internal data class EditTransactionForScreenUIState(
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    val titleError: EditTransactionForScreenTitleError = EditTransactionForScreenTitleError.None,
    val titleTextFieldState: TextFieldState = TextFieldState(),
) : ScreenUIState

internal sealed class EditTransactionForScreenTitleError {
    data object None : EditTransactionForScreenTitleError()
    data object TransactionForExists :
        EditTransactionForScreenTitleError()
}

internal val EditTransactionForScreenTitleError.stringResourceId: Int?
    get() {
        return when (this) {
            EditTransactionForScreenTitleError.None -> null
            EditTransactionForScreenTitleError.TransactionForExists -> R.string.finance_manager_screen_add_or_edit_transaction_for_error_exists
        }
    }
