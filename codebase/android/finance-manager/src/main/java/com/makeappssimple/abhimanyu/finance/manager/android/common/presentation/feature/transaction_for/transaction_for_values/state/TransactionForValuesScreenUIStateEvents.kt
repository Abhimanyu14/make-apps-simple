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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.transaction_for_values.state

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.transaction_for_values.bottom_sheet.TransactionForValuesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIStateEvents
import kotlinx.coroutines.Job

@Immutable
internal class TransactionForValuesScreenUIStateEvents(
    val deleteTransactionFor: () -> Job,
    val navigateToAddTransactionForScreen: () -> Job,
    val navigateToEditTransactionForScreen: (transactionForId: Int) -> Job,
    val navigateUp: () -> Job,
    val resetScreenBottomSheetType: () -> Job,
    val updateScreenBottomSheetType: (TransactionForValuesScreenBottomSheetType) -> Job,
    val updateTransactionForIdToDelete: (Int?) -> Job,
) : ScreenUIStateEvents
