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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.analysis.AnalysisListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.bottomsheet.AnalysisScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

@Stable
internal data class AnalysisScreenUIState(
    val screenBottomSheetType: AnalysisScreenBottomSheetType = AnalysisScreenBottomSheetType.None,
    val isBottomSheetVisible: Boolean = false,
    val isLoading: Boolean = true,
    val selectedFilter: Filter = Filter(),
    val analysisListItemData: ImmutableList<AnalysisListItemData> = persistentListOf(),
    val transactionTypesChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val selectedTransactionTypeIndex: Int = 0,
    val defaultStartLocalDate: LocalDate = LocalDate.MIN,
    val defaultEndLocalDate: LocalDate = LocalDate.MIN,
    val startOfCurrentMonthLocalDate: LocalDate = LocalDate.MIN,
    val startOfCurrentYearLocalDate: LocalDate = LocalDate.MIN,
) : ScreenUIState
