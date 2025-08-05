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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType

@Stable
internal class AddCategoryScreenUIStateEvents(
    val clearSearchText: () -> Unit = {},
    val clearTitle: () -> Unit = {},
    val insertCategory: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val updateEmoji: (updatedEmoji: String) -> Unit = {},
    val updateScreenBottomSheetType: (updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType) -> Unit = {},
    val updateSearchText: (updatedSearchText: String) -> Unit = {},
    val updateSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit = {},
    val updateTitle: (updatedTitle: TextFieldValue) -> Unit = {},
) : ScreenUIStateEvents
