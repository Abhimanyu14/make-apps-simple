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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.bottom_sheet.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.theme.FinanceManagerAppTheme

@Immutable
internal data class MyBottomSheetListItemData(
    val imageVector: ImageVector? = null,
    val text: String,
)

@Immutable
internal sealed class MyBottomSheetListItemEvent {
    data object OnClick : MyBottomSheetListItemEvent()
}

@Composable
internal fun MyBottomSheetListItem(
    modifier: Modifier = Modifier,
    data: MyBottomSheetListItemData,
    handleEvent: (event: MyBottomSheetListItemEvent) -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .conditionalClickable(
                onClickLabel = data.text,
                role = Role.Button,
                onClick = {
                    handleEvent(MyBottomSheetListItemEvent.OnClick)
                },
            )
            .padding(
                all = 16.dp,
            ),
    ) {
        data.imageVector?.let {
            Icon(
                imageVector = data.imageVector,
                contentDescription = null,
                tint = FinanceManagerAppTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(
                        end = 12.dp,
                    ),
            )
        }
        MyText(
            modifier = Modifier,
            text = data.text,
            style = FinanceManagerAppTheme.typography.headlineMedium
                .copy(
                    color = FinanceManagerAppTheme.colorScheme.onBackground,
                ),
        )
    }
}
