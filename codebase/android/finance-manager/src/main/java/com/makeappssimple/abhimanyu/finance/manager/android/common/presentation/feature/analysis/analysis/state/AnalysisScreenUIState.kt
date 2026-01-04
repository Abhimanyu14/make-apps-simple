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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.analysis.analysis.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.chip.ChipUIData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.date_time.MyLocalDate
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.analysis.analysis.bottom_sheet.AnalysisScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.listitem.analysis.AnalysisListItemData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class AnalysisScreenUIState(
    val screenBottomSheetType: AnalysisScreenBottomSheetType = AnalysisScreenBottomSheetType.None,
    val isBottomSheetVisible: Boolean = false,
    val isLoading: Boolean = true,
    val selectedFilter: Filter = Filter(),
    val analysisListItemData: ImmutableList<AnalysisListItemData> = persistentListOf(),
    val transactionTypesChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val selectedTransactionTypeIndex: Int = 0,
    val defaultStartLocalDate: MyLocalDate = MyLocalDate.MIN,
    val defaultEndLocalDate: MyLocalDate = MyLocalDate.MIN,
    val startOfCurrentMonthLocalDate: MyLocalDate = MyLocalDate.MIN,
    val startOfCurrentYearLocalDate: MyLocalDate = MyLocalDate.MIN,
) : ScreenUIState
