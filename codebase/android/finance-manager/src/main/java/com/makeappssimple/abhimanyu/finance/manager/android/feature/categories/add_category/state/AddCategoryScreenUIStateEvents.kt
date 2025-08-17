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
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.bottom_sheet.AddCategoryScreenBottomSheetType
import kotlinx.coroutines.Job

@Stable
internal class AddCategoryScreenUIStateEvents(
    val clearEmojiSearchText: () -> Job,
    val clearTitle: () -> Job,
    val insertCategory: () -> Job,
    val navigateUp: () -> Job,
    val resetScreenBottomSheetType: () -> Job,
    val updateEmoji: (updatedEmoji: String) -> Job,
    val updateEmojiSearchText: (updatedEmojiSearchText: String) -> Job,
    val updateScreenBottomSheetType: (updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType) -> Job,
    val updateSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Job,
    val updateTitle: (updatedTitle: TextFieldValue) -> Job,
) : ScreenUIStateEvents
