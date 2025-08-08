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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.event

import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.bottom_sheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.state.CategoriesScreenUIStateEvents

internal class CategoriesScreenUIEventHandler internal constructor(
    private val uiStateEvents: CategoriesScreenUIStateEvents,
) : ScreenUIEventHandler<CategoriesScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: CategoriesScreenUIEvent,
    ) {
        when (uiEvent) {
            is CategoriesScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is CategoriesScreenUIEvent.OnSnackbarDismissed -> {
                uiStateEvents.resetScreenSnackbarType()
            }

            is CategoriesScreenUIEvent.OnFloatingActionButtonClick -> {
                uiStateEvents.navigateToAddCategoryScreen(uiEvent.transactionType)
            }

            is CategoriesScreenUIEvent.OnCategoriesGridItemClick -> {
                uiEvent.categoryId?.let {
                    if (uiEvent.isEditVisible || uiEvent.isSetAsDefaultVisible || uiEvent.isDeleteVisible) {
                        uiStateEvents.updateScreenBottomSheetType(
                            CategoriesScreenBottomSheetType.Menu(
                                isDeleteVisible = uiEvent.isDeleteVisible,
                                isEditVisible = uiEvent.isEditVisible,
                                isSetAsDefaultVisible = uiEvent.isSetAsDefaultVisible,
                                categoryId = uiEvent.categoryId,
                            )
                        )
                        uiStateEvents.updateClickedItemId(uiEvent.categoryId)
                    }
                }
            }

            is CategoriesScreenUIEvent.OnCategoriesSetAsDefaultConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.updateClickedItemId(null)
            }

            is CategoriesScreenUIEvent.OnCategoriesSetAsDefaultConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.updateDefaultCategoryIdInDataStore(uiEvent.selectedTabIndex)
                uiStateEvents.updateClickedItemId(null)
            }

            is CategoriesScreenUIEvent.OnCategoryMenuBottomSheet.DeleteButtonClick -> {
                uiStateEvents.updateCategoryIdToDelete(uiEvent.categoryId)
                uiStateEvents.updateScreenBottomSheetType(
                    CategoriesScreenBottomSheetType.DeleteConfirmation
                )
            }

            is CategoriesScreenUIEvent.OnCategoryMenuBottomSheet.EditButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.navigateToEditCategoryScreen(uiEvent.categoryId)
            }

            is CategoriesScreenUIEvent.OnCategoryMenuBottomSheet.SetAsDefaultButtonClick -> {
                uiStateEvents.updateClickedItemId(uiEvent.categoryId)
                uiStateEvents.updateScreenBottomSheetType(
                    CategoriesScreenBottomSheetType.SetAsDefaultConfirmation
                )
            }

            is CategoriesScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is CategoriesScreenUIEvent.OnCategoriesDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.updateCategoryIdToDelete(null)
            }

            is CategoriesScreenUIEvent.OnCategoriesDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.deleteCategory()
            }
        }
    }
}
