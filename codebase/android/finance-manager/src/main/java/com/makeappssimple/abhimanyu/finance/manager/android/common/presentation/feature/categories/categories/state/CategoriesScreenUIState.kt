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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.categories.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.tab_row.MyTabData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.grid_item.CategoriesGridItemData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.categories.bottom_sheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.categories.snackbar.CategoriesScreenSnackbarType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class CategoriesScreenUIState(
    val isBottomSheetVisible: Boolean = false,
    val isLoading: Boolean = true,
    val screenBottomSheetType: CategoriesScreenBottomSheetType = CategoriesScreenBottomSheetType.None,
    val screenSnackbarType: CategoriesScreenSnackbarType = CategoriesScreenSnackbarType.None,
    val tabData: ImmutableList<MyTabData> = persistentListOf(),
    val transactionTypeTabs: ImmutableList<TransactionType> = persistentListOf(),
    val categoriesGridItemDataMap: Map<TransactionType, ImmutableList<CategoriesGridItemData>> = emptyMap(),
) : ScreenUIState
