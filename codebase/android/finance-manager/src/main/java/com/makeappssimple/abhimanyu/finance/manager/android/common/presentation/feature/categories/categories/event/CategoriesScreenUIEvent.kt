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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.categories.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.base.ScreenUIEvent

@Immutable
internal sealed class CategoriesScreenUIEvent : ScreenUIEvent {
    data object OnNavigationBackButtonClick : CategoriesScreenUIEvent()
    data object OnSnackbarDismissed : CategoriesScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : CategoriesScreenUIEvent()

    data class OnFloatingActionButtonClick(
        val transactionType: String,
    ) : CategoriesScreenUIEvent()

    data class OnCategoriesGridItemClick(
        val isDeleteVisible: Boolean,
        val isEditVisible: Boolean,
        val isSetAsDefaultVisible: Boolean,
        val categoryId: Int?,
    ) : CategoriesScreenUIEvent()

    sealed class OnCategoriesSetAsDefaultConfirmationBottomSheet {
        data object NegativeButtonClick : CategoriesScreenUIEvent()
        data class PositiveButtonClick(
            val selectedTabIndex: Int,
        ) : CategoriesScreenUIEvent()
    }

    sealed class OnCategoryMenuBottomSheet {
        data class DeleteButtonClick(
            val categoryId: Int,
        ) : CategoriesScreenUIEvent()

        data class EditButtonClick(
            val categoryId: Int,
        ) : CategoriesScreenUIEvent()

        data class SetAsDefaultButtonClick(
            val categoryId: Int,
        ) : CategoriesScreenUIEvent()
    }

    sealed class OnCategoriesDeleteConfirmationBottomSheet {
        data object NegativeButtonClick : CategoriesScreenUIEvent()
        data object PositiveButtonClick : CategoriesScreenUIEvent()
    }
}
