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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.analysis

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.feature.analysis.Filter
import java.time.LocalDate

@Immutable
public data class AnalysisFilterBottomSheetData(
    val selectedFilter: Filter,
    @StringRes val headingTextStringResourceId: Int,
    val defaultEndLocalDate: LocalDate,
    val defaultStartLocalDate: LocalDate,
    val startOfCurrentMonthLocalDate: LocalDate,
    val startOfCurrentYearLocalDate: LocalDate,
)
