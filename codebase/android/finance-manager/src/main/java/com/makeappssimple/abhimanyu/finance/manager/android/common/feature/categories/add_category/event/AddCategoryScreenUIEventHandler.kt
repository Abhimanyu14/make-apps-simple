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

package com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.add_category.event

import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.add_category.bottom_sheet.AddCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.add_category.state.AddCategoryScreenUIStateEvents

internal class AddCategoryScreenUIEventHandler internal constructor(
    private val uiStateEvents: AddCategoryScreenUIStateEvents,
) : ScreenUIEventHandler<AddCategoryScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: AddCategoryScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddCategoryScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.clearEmojiSearchText()
            }

            is AddCategoryScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AddCategoryScreenUIEvent.OnCtaButtonClick -> {
                uiStateEvents.insertCategory()
            }

            is AddCategoryScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateEvents.clearTitle()
            }

            is AddCategoryScreenUIEvent.OnEmojiCircleClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    AddCategoryScreenBottomSheetType.SelectEmoji
                )
            }

            is AddCategoryScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is AddCategoryScreenUIEvent.OnEmojiUpdated -> {
                uiStateEvents.updateEmoji(uiEvent.updatedEmoji)
            }

            is AddCategoryScreenUIEvent.OnEmojiBottomSheetSearchTextUpdated -> {
                uiStateEvents.updateEmojiSearchText(uiEvent.updatedSearchText)
            }

            is AddCategoryScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateEvents.updateSelectedTransactionTypeIndex(uiEvent.updatedIndex)
            }

            is AddCategoryScreenUIEvent.OnTitleUpdated -> {
                uiStateEvents.updateTitle(uiEvent.updatedTitle)
            }
        }
    }
}
