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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType

@Stable
internal class AddAccountScreenUIStateEvents(
    val clearMinimumAccountBalanceAmountValue: () -> Unit = {},
    val clearName: () -> Unit = {},
    val insertAccount: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenSnackbarType: () -> Unit = {},
    val updateMinimumAccountBalanceAmountValue: (updatedMinimumAccountBalanceAmountValue: TextFieldValue) -> Unit = {},
    val updateName: (updatedName: TextFieldValue) -> Unit = {},
    val updateScreenSnackbarType: (AddAccountScreenSnackbarType) -> Unit = {},
    val updateSelectedAccountTypeIndex: (updatedIndex: Int) -> Unit = {},
) : ScreenUIStateEvents
