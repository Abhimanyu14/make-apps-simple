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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.theme.FinanceManagerAppTheme

@Immutable
internal data class MyListItemDataAndEventHandler(
    val data: MyListItemData,
    val handleEvent: (event: MyListItemEvent) -> Unit = {},
)

@Immutable
internal data class MyListItemData(
    @StringRes val textStringResourceId: Int,
)

@Immutable
internal sealed class MyListItemEvent {
    data object OnClick : MyListItemEvent()
}

@Composable
internal fun MyListItem(
    modifier: Modifier = Modifier,
    data: MyListItemData,
    handleEvent: (event: MyListItemEvent) -> Unit = {},
) {
    MyText(
        modifier = modifier
            .fillMaxWidth()
            .conditionalClickable(
                onClick = {
                    handleEvent(MyListItemEvent.OnClick)
                },
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        textStringResourceId = data.textStringResourceId,
        style = FinanceManagerAppTheme.typography.bodyLarge
            .copy(
                color = FinanceManagerAppTheme.colorScheme.onBackground,
            ),
    )
}
