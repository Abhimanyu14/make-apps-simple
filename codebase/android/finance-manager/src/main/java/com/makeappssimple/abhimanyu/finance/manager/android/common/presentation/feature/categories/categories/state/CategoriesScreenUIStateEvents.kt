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
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.categories.bottom_sheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.categories.snackbar.CategoriesScreenSnackbarType
import kotlinx.coroutines.Job

@Stable
internal class CategoriesScreenUIStateEvents(
    val deleteCategory: () -> Job,
    val navigateToAddCategoryScreen: (transactionType: String) -> Job,
    val navigateToEditCategoryScreen: (categoryId: Int) -> Job,
    val navigateUp: () -> Job,
    val resetScreenBottomSheetType: () -> Job,
    val resetScreenSnackbarType: () -> Job,
    val updateCategoryIdToDelete: (updatedCategoryIdToDelete: Int?) -> Job,
    val updateClickedItemId: (updatedClickedItemId: Int?) -> Job,
    val updateDefaultCategoryIdInDataStore: (selectedTabIndex: Int) -> Job,
    val updateScreenBottomSheetType: (updatedCategoriesBottomSheetType: CategoriesScreenBottomSheetType) -> Job,
    val updateScreenSnackbarType: (CategoriesScreenSnackbarType) -> Job,
) : ScreenUIStateEvents
