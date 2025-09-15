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

package com.makeappssimple.abhimanyu.finance.manager.android.common.feature.transaction_for.transaction_for_values.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.listitem.transaction_for.TransactionForListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.transaction_for.transaction_for_values.bottom_sheet.TransactionForValuesScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class TransactionForValuesScreenUIState(
    val isBottomSheetVisible: Boolean = false,
    val isLoading: Boolean = true,
    val transactionForListItemDataList: ImmutableList<TransactionForListItemData> = persistentListOf(),
    val screenBottomSheetType: TransactionForValuesScreenBottomSheetType = TransactionForValuesScreenBottomSheetType.None,
) : ScreenUIState
