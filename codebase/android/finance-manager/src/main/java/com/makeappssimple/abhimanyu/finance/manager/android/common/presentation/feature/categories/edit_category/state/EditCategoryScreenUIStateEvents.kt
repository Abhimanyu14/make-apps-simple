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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.bottom_sheet.EditCategoryScreenBottomSheetType
import kotlinx.coroutines.Job

@Stable
internal class EditCategoryScreenUIStateEvents(
    val clearTitle: () -> Job,
    val navigateUp: () -> Job,
    val resetScreenBottomSheetType: () -> Job,
    val updateCategory: () -> Job,
    val updateEmoji: (updatedEmoji: String) -> Job,
    val updateScreenBottomSheetType: (EditCategoryScreenBottomSheetType) -> Job,
    val updateSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Job,
    val updateTitle: (updatedTitle: String) -> Job,
) : ScreenUIStateEvents
